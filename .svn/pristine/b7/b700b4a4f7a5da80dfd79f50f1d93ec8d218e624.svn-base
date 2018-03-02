package eleme.openapi.sdk.convert;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JsonDateDeserializer extends JsonDeserializer<Date> {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Date deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        String dateTimeStr = ((JsonNode) parser.getCodec().readTree(parser)).asText();
        dateTimeStr = dateTimeStr.replace("T", " ");
        try {
            return dateFormat.parse(dateTimeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


}
