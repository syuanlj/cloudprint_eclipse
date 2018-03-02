<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@page import="java.util.UUID"%>
<%@page import="java.security.interfaces.RSAPublicKey"%>
<%@page import="org.apache.commons.lang.ArrayUtils"%>
<%@page import="org.apache.commons.codec.binary.Base64"%>
<%@page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="com.nfet.Setting"%>
<%@page import="com.nfet.util.SettingUtils"%>
<%@page import="com.nfet.util.SpringUtils"%>
<%@page import="com.nfet.Setting.AccountLockType"%>
<%@page import="com.nfet.service.RSAService"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
String base = request.getContextPath();
ApplicationContext applicationContext = SpringUtils.getApplicationContext();
Setting setting = SettingUtils.get();
if (applicationContext != null) {
%>
<shiro:authenticated>
<%
String redirectUrl = base + "/admin/common/main.ehtml";
response.sendRedirect(redirectUrl);
%>
</shiro:authenticated>
<%
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<%
	RSAService rsaService = SpringUtils.getBean("rsaServiceImpl", RSAService.class);
	RSAPublicKey publicKey = rsaService.generateKey(request);
	String modulus = Base64.encodeBase64String(publicKey.getModulus().toByteArray());
	String exponent = Base64.encodeBase64String(publicKey.getPublicExponent().toByteArray());
	
	String message = null;
	String loginFailure = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
	if (loginFailure != null) {
		if (loginFailure.equals("org.apache.shiro.authc.UnknownAccountException")) {
			message = "admin.login.unknownAccount";
		} else if (loginFailure.equals("org.apache.shiro.authc.DisabledAccountException")) {
			message = "admin.login.disabledAccount";
		} else if (loginFailure.equals("org.apache.shiro.authc.LockedAccountException")) {
			message = "admin.login.lockedAccount";
		} else if (loginFailure.equals("org.apache.shiro.authc.IncorrectCredentialsException")) {
			if (ArrayUtils.contains(setting.getAccountLockTypes(), AccountLockType.admin)) {
				message = "admin.login.accountLockCount";
			} else {
				message = "admin.login.incorrectCredentials";
			}
		} else if (loginFailure.equals("org.apache.shiro.authc.AuthenticationException")) {
			message = "admin.login.authentication";
		}
	}
%>
<title><%=SpringUtils.getMessage("admin.login.title")%></title>
<meta http-equiv="expires" content="0" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache" />


<link href="<%=base%>/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<link href="<%=base%>/resources/admin/css/loginstyle.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=base%>/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="<%=base%>/resources/admin/js/jsbn.js"></script>
<script type="text/javascript" src="<%=base%>/resources/admin/js/prng4.js"></script>
<script type="text/javascript" src="<%=base%>/resources/admin/js/rng.js"></script>
<script type="text/javascript" src="<%=base%>/resources/admin/js/rsa.js"></script>
<script type="text/javascript" src="<%=base%>/resources/admin/js/base64.js"></script>
<script type="text/javascript" src="<%=base%>/resources/admin/js/common.js"></script>
<script type="text/javascript">
	$().ready( function() {
		
		var $loginDiv = $("#loginDiv");
		var $blankDiv = $("#blankDiv");
		var $loginForm = $("#loginForm");
		var $enPassword = $("#enPassword");
		var $username = $("#username");
		var $password = $("#password");
		
		if(window.screen.height > 800) {
			//$loginDiv.css("height", 760);
			//$blankDiv.css("height", 240);
		}
		
		// 表单验证、记住用户名
		$loginForm.submit( function() {
			if ($username.val() == "") {
				$.message("warn", "<%=SpringUtils.getMessage("admin.login.usernameRequired")%>");
				return false;
			}
			if ($password.val() == "") {
				$.message("warn", "<%=SpringUtils.getMessage("admin.login.passwordRequired")%>");
				return false;
			}
			
			var rsaKey = new RSAKey();
			rsaKey.setPublic(b64tohex("<%=modulus%>"), b64tohex("<%=exponent%>"));
			var enPassword = hex2b64(rsaKey.encrypt($password.val()));
			$enPassword.val(enPassword);
		});
		
		<%if (message != null) {%>
			$.message("error", "<%=SpringUtils.getMessage(message, setting.getAccountLockCount())%>");
		<%}%>
	});
</script>

</head>
<body>
  <div id="loginDiv" class="login-card">
    <h1>登陆</h1><br>
  <form id="loginForm" action="login.jsp" method="post">
  	<input type="hidden" id="enPassword" name="enPassword" />
    <input type="text" id="username" name="username" placeholder="用户名">
    <input type="password" id="password" placeholder="密码">
    <input type="submit" name="login" class="login login-submit" value="登陆">
  </form>
    
  <div class="login-help">
    <a href="#">Register</a> • <a href="#">Forgot Password</a>
  </div>
</div>
<!--
	<div  id="loginDiv" class="login">
		<form id="loginForm" action="login.jsp" method="post">
			<input type="hidden" id="enPassword" name="enPassword" />
			<div style="width: 80%;margin:0 auto 0 auto">
					<img src="<%=base%>/resources/admin/images/login_logo.png"/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<img src="<%=base%>/resources/admin/images/title.png" width="640"/>
			</div>
			<div id="blankDiv" style="height:180px">&nbsp;</div>
			<table>
				<tr>
					<td width="50"></td>
					<th>
						<%=SpringUtils.getMessage("admin.login.username")%>:
					</th>
					<td>
						<input type="text" id="username" name="username" class="text" maxlength="20" />
					</td>
					<th>
						<%=SpringUtils.getMessage("admin.login.password")%>:
					</th>
					<td>
						<input type="password" id="password" class="text" maxlength="20" autocomplete="off" />
					</td>
					<td>
						&nbsp;
					</td>
					<th>
						&nbsp;
					</th>
					<td>
						<input type="submit" class="loginButton" value="<%=SpringUtils.getMessage("admin.login.login")%>" />
					</td>
				</tr>
			</table>
		</form>
	</div>
	-->
	<div style="text-align: center">
		&copy;&nbsp;南京富士通电子信息科技股份有限公司 2016
	</div>
</body>
</html>