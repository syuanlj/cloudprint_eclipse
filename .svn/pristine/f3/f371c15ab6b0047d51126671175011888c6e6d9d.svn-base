package eleme.openapi.sdk.api.entity.order;

import eleme.openapi.sdk.api.enumeration.order.*;
import eleme.openapi.sdk.api.entity.order.*;
import java.util.*;
import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonFormat;

public class ORefundOrder{

    /**
     * 订单id
     */
    private String orderId;
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    
    /**
     * 退单状态
     */
    private OOrderRefundStatus refundStatus;
    public OOrderRefundStatus getRefundStatus() {
        return refundStatus;
    }
    public void setRefundStatus(OOrderRefundStatus refundStatus) {
        this.refundStatus = refundStatus;
    }
    
    /**
     * 退单类型
     */
    private ORefundType refundType;
    public ORefundType getRefundType() {
        return refundType;
    }
    public void setRefundType(ORefundType refundType) {
        this.refundType = refundType;
    }
    
    /**
     * 退款总额
     */
    private double totalPrice;
    public double getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    /**
     * 订单下商品列表
     */
    private List<Item> goodsList;
    public List<Item> getGoodsList() {
        return goodsList;
    }
    public void setGoodsList(List<Item> goodsList) {
        this.goodsList = goodsList;
    }
    
    /**
     * 用户申请退单原因
     */
    private String applyRefundReason;
    public String getApplyRefundReason() {
        return applyRefundReason;
    }
    public void setApplyRefundReason(String applyRefundReason) {
        this.applyRefundReason = applyRefundReason;
    }
    
    /**
     * 退单图片hash列表
     */
    private List<String> refundImages;
    public List<String> getRefundImages() {
        return refundImages;
    }
    public void setRefundImages(List<String> refundImages) {
        this.refundImages = refundImages;
    }
    
}