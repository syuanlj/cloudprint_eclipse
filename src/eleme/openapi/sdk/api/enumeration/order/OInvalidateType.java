package eleme.openapi.sdk.api.enumeration.order;

public enum OInvalidateType {
    /**
     * 其他原因
     */
    others("others"), 
    
    /**
     * 用户信息错误
     */
    fakeOrder("fakeOrder"), 
    
    /**
     * 联系不上用户
     */
    contactUserFailed("contactUserFailed"), 
    
    /**
     * 商品已经售完
     */
    foodSoldOut("foodSoldOut"), 
    
    /**
     * 商家已经打烊
     */
    restaurantClosed("restaurantClosed"), 
    
    /**
     * 超出配送范围
     */
    distanceTooFar("distanceTooFar"), 
    
    /**
     * 商家现在太忙
     */
    restaurantTooBusy("restaurantTooBusy"), 
    
    /**
     * 用户要求取消
     */
    forceRejectOrder("forceRejectOrder"), 
    
    /**
     * 暂时无法配送
     */
    deliveryFault("deliveryFault"), 
    
    /**
     * 不满足起送要求
     */
    notSatisfiedDeliveryRequirement("notSatisfiedDeliveryRequirement");
    

    private String orderDesc;
    OInvalidateType(String orderDesc) {
        this.orderDesc = orderDesc;
    }
}