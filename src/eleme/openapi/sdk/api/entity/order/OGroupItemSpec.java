package eleme.openapi.sdk.api.entity.order;

import eleme.openapi.sdk.api.enumeration.order.*;
import eleme.openapi.sdk.api.entity.order.*;
import java.util.*;
import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonFormat;

public class OGroupItemSpec{

    /**
     * 规格名称
     */
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * 规格值
     */
    private String value;
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    
}