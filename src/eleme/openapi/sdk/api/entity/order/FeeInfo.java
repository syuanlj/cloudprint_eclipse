package eleme.openapi.sdk.api.entity.order;

import eleme.openapi.sdk.api.enumeration.order.*;
import eleme.openapi.sdk.api.entity.order.*;
import java.util.*;
import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonFormat;

public class FeeInfo{

    /**
     * 能否加小费
     */
    private Boolean canAddFee;
    public Boolean getCanAddFee() {
        return canAddFee;
    }
    public void setCanAddFee(Boolean canAddFee) {
        this.canAddFee = canAddFee;
    }
    
    /**
     * 最大小费金额
     */
    private int maxAmount;
    public int getMaxAmount() {
        return maxAmount;
    }
    public void setMaxAmount(int maxAmount) {
        this.maxAmount = maxAmount;
    }
    
    /**
     * 最小小费金额
     */
    private int minAmount;
    public int getMinAmount() {
        return minAmount;
    }
    public void setMinAmount(int minAmount) {
        this.minAmount = minAmount;
    }
    
    /**
     * 当前小费金额
     */
    private int fee;
    public int getFee() {
        return fee;
    }
    public void setFee(int fee) {
        this.fee = fee;
    }
    
}