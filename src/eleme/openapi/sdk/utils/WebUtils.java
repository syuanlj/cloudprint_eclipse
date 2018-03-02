package eleme.openapi.sdk.utils;

import com.fasterxml.jackson.databind.JavaType;
import eleme.openapi.sdk.api.exception.*;
import eleme.openapi.sdk.api.protocol.ErrorPayload;
import eleme.openapi.sdk.api.protocol.ResponsePayload;
import eleme.openapi.sdk.config.Constants;
import eleme.openapi.sdk.config.Config;
import eleme.openapi.sdk.oauth.response.Token;

import javax.net.ssl.*;
import java.io.*;
import java.lang.reflect.Type;
import java.net.*;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.zip.GZIPInputStream;

public abstract class WebUtils {
    private static final String DEFAULT_CHARSET = Constants.CHARSET_UTF8;
    private static final String METHOD_POST = "POST";

    private static class DefaultTrustManager implements X509TrustManager {
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
    }

    public static String doPost(Config context, String url,
                                Map<String, String> params,
                                String charset,
                                String rid,
                                int connectTimeout,
                                int readTimeout,
                                Map<String, String> headerMap) throws IOException {
        String ctype = "application/x-www-form-urlencoded;charset=" + charset;
        String query = buildQuery(params, charset);
        setLogInfo(context, "request: " + query);

        byte[] content = {};
        if (query != null) {
            content = query.getBytes(charset);
        }
        return _doPost(context, url, ctype, content, rid, connectTimeout, readTimeout, headerMap);
    }

    public static String doPost(Config context, String url, String ctype, byte[] content, String rid, int connectTimeout, int readTimeout)
            throws SocketTimeoutException, IOException {
        return _doPost(context, url, ctype, content, rid, connectTimeout, readTimeout, null);
    }

    private static String _doPost(Config context, String url, String ctype, byte[] content, String rid, int connectTimeout, int readTimeout,
                                  Map<String, String> headerMap) throws SocketTimeoutException, IOException {
        HttpURLConnection conn = null;
        OutputStream out = null;
        String rsp = null;
        try {
            conn = getConnection(new URL(url), METHOD_POST, ctype, headerMap, rid);
            conn.setConnectTimeout(connectTimeout);
            conn.setReadTimeout(readTimeout);
            out = conn.getOutputStream();
            out.write(content);
            rsp = getResponseAsString(conn);
        } finally {
            if (out != null) {
                out.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        setLogInfo(context, "response: " + rsp);
        return rsp;
    }

    private static HttpURLConnection getConnection(URL url, String method, String ctype, Map<String, String> headerMap, String rid) throws IOException {
        HttpURLConnection conn;
        if ("https".equals(url.getProtocol())) {
            SSLContext ctx;
            try {
                ctx = SSLContext.getInstance("TLS");
                ctx.init(new KeyManager[0], new TrustManager[]{new DefaultTrustManager()}, new SecureRandom());
            } catch (Exception e) {
                throw new IOException(e);
            }
            HttpsURLConnection connHttps = (HttpsURLConnection) url.openConnection();
            connHttps.setSSLSocketFactory(ctx.getSocketFactory());
            connHttps.setHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;// 默认都认证通过
                }
            });
            conn = connHttps;
        } else {
            conn = (HttpURLConnection) url.openConnection();
        }

        conn.setRequestMethod(method);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestProperty("Accept", "text/xml,text/javascript,text/html");
        conn.setRequestProperty("Content-Type", ctype);
        conn.setRequestProperty("Accept-Encoding", "gzip");
        conn.setRequestProperty("User-Agent", "eleme-openapi-java-sdk");
        String elemeRequestId = getReqID(rid);
        conn.setRequestProperty("x-eleme-requestid", elemeRequestId);
        if (headerMap != null) {
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        return conn;
    }

    public static String buildQuery(Map<String, String> params, String charset) throws IOException {
        if (params == null || params.isEmpty()) {
            return null;
        }

        StringBuilder query = new StringBuilder();
        Set<Map.Entry<String, String>> entries = params.entrySet();
        boolean hasParam = false;

        for (Map.Entry<String, String> entry : entries) {
            String name = entry.getKey();
            String value = entry.getValue();
            // 忽略参数名或参数值为空的参数
            if (StringUtils.areNotEmpty(name, value)) {
                if (hasParam) {
                    query.append("&");
                } else {
                    hasParam = true;
                }
                query.append(name).append("=").append(URLEncoder.encode(value, charset));
            }
        }
        return query.toString();
    }

    protected static String getResponseAsString(HttpURLConnection conn) throws IOException {
        String charset = getResponseCharset(conn.getContentType());
        InputStream es = conn.getErrorStream();
        if (es == null) {
            return getStreamAsString(conn.getInputStream(), charset, conn);
        } else {
            String msg = getStreamAsString(es, charset, conn);
            if (StringUtils.isEmpty(msg)) {
                throw new IOException(conn.getResponseCode() + ":" + conn.getResponseMessage());
            } else {
                return msg;
            }
        }
    }

    private static String getStreamAsString(InputStream stream, String charset, HttpURLConnection conn) throws IOException {
        try {
            Reader reader;
            if ("gzip".equals(conn.getContentEncoding())) {
                reader = new InputStreamReader(new GZIPInputStream(stream), charset);
            } else {
                reader = new InputStreamReader(stream, charset);
            }

            StringBuilder response = new StringBuilder();
            final char[] buff = new char[1024];
            int read = 0;
            while ((read = reader.read(buff)) > 0) {
                response.append(buff, 0, read);
            }

            return response.toString();
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }

    private static String getReqID(String rid) {
        String reqId = rid + "|" + System.currentTimeMillis();
        return reqId;
    }

    public static String generateUUID() {
        try {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            if (uuid.length() > 32) {
                uuid = uuid.substring(0, 32);
            }
            return uuid.toUpperCase();
        } catch (Exception e) {
            return "00112233445566778899AABBCCDDEEFF";
        }
    }

    private static String getResponseCharset(String ctype) {
        String charset = DEFAULT_CHARSET;

        if (!StringUtils.isEmpty(ctype)) {
            String[] params = ctype.split(";");
            for (String param : params) {
                param = param.trim();
                if (param.startsWith("charset")) {
                    String[] pair = param.split("=", 2);
                    if (pair.length == 2) {
                        if (!StringUtils.isEmpty(pair[1])) {
                            charset = pair[1].trim();
                        }
                    }
                    break;
                }
            }
        }

        return charset;
    }

    public static <T> T call(Config context, String action,
                             Map<String, Object> parameters,
                             Token token,
                             Type type
    ) throws ServiceException {
        final long timestamp = System.currentTimeMillis();
        final String appKey = context.getApp_key();
        String secret = context.getApp_secret();
        String accessToken = token.getAccessToken();
        String requestId = generateUUID();

        System.out.println("requestId: " + requestId);
        Map<String, Object> requestPayload = new HashMap<String, Object>();
        requestPayload.put("nop", "1.0.0");
        requestPayload.put("id", requestId);
        requestPayload.put("action", action);
        requestPayload.put("token", accessToken);

        Map<String, Object> metasHashMap = new HashMap<String, Object>();
        metasHashMap.put("app_key", appKey);
        metasHashMap.put("timestamp", timestamp);

        requestPayload.put("metas", metasHashMap);
        requestPayload.put("params", parameters);
        String signature = SignatureUtil.generateSignature(appKey, secret, timestamp, action, accessToken, parameters);
        requestPayload.put("signature", signature);

        String requestJson = JacksonUtils.obj2json(requestPayload);
        ResponsePayload responsePayload;
        try {
            responsePayload = doRequest(context, requestJson, requestId);
        } catch (SocketTimeoutException ex) {
            throw new SourceTimeoutException();
        } catch (IOException ex) {
            throw new ServiceException(ex.getClass().getName(), ex);
        }
        setLogInfo(context, "request: " + requestJson);
        if (responsePayload != null && null != responsePayload.getError()) {
            ServiceException serviceException = toException(responsePayload.getError());
            if (serviceException != null) {
                setLogError(context, "error: " + serviceException.getMessage());
                throw serviceException;
            }
            throw new ServerErrorException();
        }
        if (type == void.class)
            return null;
        String s2 = JacksonUtils.obj2json(responsePayload.getResult());
        JavaType javaType = JacksonUtils.getInstance().getTypeFactory().constructType(type);
        return JacksonUtils.json2pojo(s2,javaType);
    }

    private static ResponsePayload doRequest(Config context, String requestJson, String rid) throws SocketTimeoutException, IOException {
        String response = doPost(context, context.getApiUrl(), "application/json; charset=utf-8", requestJson.getBytes(Constants.CHARSET_UTF8), rid, 15000, 15000);
        setLogInfo(context, "response: " + response);
        return JacksonUtils.json2pojo(response, ResponsePayload.class);
    }

    private static ServiceException toException(ErrorPayload error) throws ServiceException {
        String code = error.getCode();
        if(StringUtils.isEmpty(code)) return null;
        String message = error.getMessage();
        if ("ACCESS_DENIED".equals(code))
            return new AccessDeniedException(message);
        if ("EXCEED_LIMIT".equals(code))
            return new ExceedLimitException(message);
        if ("INVALID_SIGNATURE".equals(code))
            return new InvalidSignatureException(message);
        if ("INVALID_TIMESTAMP".equals(code))
            return new InvalidTimestampException(message);
        if ("METHOD_NOT_ALLOWED".equals(code))
            return new MethodNotAllowedException(message);
        if ("PERMISSION_DENIED".equals(code))
            return new PermissionDeniedException(message);
        if ("UNAUTHORIZED".equals(code))
            return new UnauthorizedException(message);
        if ("VALIDATION_FAILED".equals(code))
            return new ValidationFailedException(message);
        if ("BUSINESS_ERROR".equals(code) || code.startsWith("BIZ_")) {
            return new BusinessException(code, error.getMessage());
        }
        return null;
    }

    private static void setLogInfo(Config context, String msg) {
        if (null != context.getElemeSdkLogger()) {
            context.getElemeSdkLogger().info(msg);
        }
    }

    private static void setLogError(Config context, String msg) {
        if (null != context.getElemeSdkLogger()) {
            context.getElemeSdkLogger().error(msg);
        }
    }
}
