package com.wangjunneil.schedule.entity.jdhome;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

/**
 * @author yangyongbing
 * @since 2016/11/16.
 * 订单Entity
 */
@Document(collection = "sync.jdhome.order")
public class OrderInfoDTO {
    private Long orderId;//订单号
    private String srcOrderId;//来源订单号(外系统来源订单号)
    private Integer srcInnerType;//内部订单来源类型(0:原订单，10:退款单，20:补货单，30:直赔商品 40:退货 50:上门换新)
    private Long srcInnerOrderId;//内部订单来源订单号
    private Integer orderType;//订单类型（10000：超市类订单；13000：美食订单；20000：服务订单）
    private Integer orderStatus;//20010:锁定，20020:用户取消，20040:超时未支付系统取消，31000:等待付款，31020:已付款，41000:待接单,41010:已接单,33040:配送中，33060:已妥投,90000:订单完成;
    private Date orderStatusTime;//订单状态最新更改时间
    private Date orderStartTime;//下单时间
    private Date orderPurchaseTime;//订单成交时间(在线支付类型订单的付款完成时间)
    private Integer orderAgingType;//订单时效类型(0:无时效;2:自定义时间;1:次日达;27:七小时达;24:六小时达;21:五小时达;18:四小时达;15:三小时达;12:两小时达;9:一小时达;6:半小时达;)
    private Date orderPreStartDeliveryTime;//预计送达开始时间
    private Date orderPreEndDeliveryTime;//预计结束送达开始时间
    private Date orderCancelTime;//订单取消时间
    private String orderCancelRemark;//订单取消备注
    private String orgCode;//商家编号
    private String buyerFullName;//收货人名称
    private String buyerFullAddress;//收货人地址
    private String buyerTelephone;//收货人电话
    private String buyerMobile;//收货人手机号
    private String produceStationNo;//京东门店编号
    private String produceStationName;//京东门店名称
    private String produceStationNoIsv;//商家门店编号
    private String deliveryStationNo;//配送门店编号
    private String deliveryStationName;//配送门店名称
    private String deliveryCarrierNo;//承运商编号(9966:京东众包;2938:商家自送)
    private String deliveryCarrierName;//承运商名称
    private String deliveryBillNo;//承运单号
    private Double deliveryPackageWeight;//	包裹重量
    private Date deliveryConfirmTime;//妥投时间
    private Integer orderPayType;//订单支付类型(4:在线支付;)
    private Long orderTotalMoney;//商品总金额
    private Long orderDiscountMoney;//订单优惠金额
    private Long orderFreightMoney;//订单运费金额
    private Long packagingMoney;//包装金额
    private Long orderBuyerPayableMoney;//用户应付金额=商品总金额 -订单优惠总金额 +订单运费总金额 -余额支付金额 +包装金额
    private Integer orderInvoiceOpenMark;//订单开发票标识
    private Boolean adjustIsExists;//是否存在调整单(0:否;1:是)
    private Long adjustId;//调整单编号
    private Date ts;//时间戳
    private Date latestTime;
    private OrderExtend orderExtend;//订单扩展类
    private List<OrderProductDTO> productList;//商品信息
    private List<OrderDiscountDTO> discountList;//订单折扣

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getSrcOrderId() {
        return srcOrderId;
    }

    public void setSrcOrderId(String srcOrderId) {
        this.srcOrderId = srcOrderId;
    }

    public Integer getSrcInnerType() {
        return srcInnerType;
    }

    public void setSrcInnerType(Integer srcInnerType) {
        this.srcInnerType = srcInnerType;
    }

    public Long getSrcInnerOrderId() {
        return srcInnerOrderId;
    }

    public void setSrcInnerOrderId(Long srcInnerOrderId) {
        this.srcInnerOrderId = srcInnerOrderId;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getOrderStatusTime() {
        return orderStatusTime;
    }

    public void setOrderStatusTime(Date orderStatusTime) {
        this.orderStatusTime = orderStatusTime;
    }

    public Date getOrderStartTime() {
        return orderStartTime;
    }

    public void setOrderStartTime(Date orderStartTime) {
        this.orderStartTime = orderStartTime;
    }

    public Date getOrderPurchaseTime() {
        return orderPurchaseTime;
    }

    public void setOrderPurchaseTime(Date orderPurchaseTime) {
        this.orderPurchaseTime = orderPurchaseTime;
    }

    public Integer getOrderAgingType() {
        return orderAgingType;
    }

    public void setOrderAgingType(Integer orderAgingType) {
        this.orderAgingType = orderAgingType;
    }

    public Date getOrderPreStartDeliveryTime() {
        return orderPreStartDeliveryTime;
    }

    public void setOrderPreStartDeliveryTime(Date orderPreStartDeliveryTime) {
        this.orderPreStartDeliveryTime = orderPreStartDeliveryTime;
    }

    public Date getOrderPreEndDeliveryTime() {
        return orderPreEndDeliveryTime;
    }

    public void setOrderPreEndDeliveryTime(Date orderPreEndDeliveryTime) {
        this.orderPreEndDeliveryTime = orderPreEndDeliveryTime;
    }

    public Date getOrderCancelTime() {
        return orderCancelTime;
    }

    public void setOrderCancelTime(Date orderCancelTime) {
        this.orderCancelTime = orderCancelTime;
    }

    public String getOrderCancelRemark() {
        return orderCancelRemark;
    }

    public void setOrderCancelRemark(String orderCancelRemark) {
        this.orderCancelRemark = orderCancelRemark;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getBuyerFullName() {
        return buyerFullName;
    }

    public void setBuyerFullName(String buyerFullName) {
        this.buyerFullName = buyerFullName;
    }

    public String getBuyerFullAddress() {
        return buyerFullAddress;
    }

    public void setBuyerFullAddress(String buyerFullAddress) {
        this.buyerFullAddress = buyerFullAddress;
    }

    public String getBuyerTelephone() {
        return buyerTelephone;
    }

    public void setBuyerTelephone(String buyerTelephone) {
        this.buyerTelephone = buyerTelephone;
    }

    public String getBuyerMobile() {
        return buyerMobile;
    }

    public void setBuyerMobile(String buyerMobile) {
        this.buyerMobile = buyerMobile;
    }

    public String getProduceStationNo() {
        return produceStationNo;
    }

    public void setProduceStationNo(String produceStationNo) {
        this.produceStationNo = produceStationNo;
    }

    public String getProduceStationName() {
        return produceStationName;
    }

    public void setProduceStationName(String produceStationName) {
        this.produceStationName = produceStationName;
    }

    public String getProduceStationNoIsv() {
        return produceStationNoIsv;
    }

    public void setProduceStationNoIsv(String produceStationNoIsv) {
        this.produceStationNoIsv = produceStationNoIsv;
    }

    public String getDeliveryStationNo() {
        return deliveryStationNo;
    }

    public void setDeliveryStationNo(String deliveryStationNo) {
        this.deliveryStationNo = deliveryStationNo;
    }

    public String getDeliveryStationName() {
        return deliveryStationName;
    }

    public void setDeliveryStationName(String deliveryStationName) {
        this.deliveryStationName = deliveryStationName;
    }

    public String getDeliveryCarrierNo() {
        return deliveryCarrierNo;
    }

    public void setDeliveryCarrierNo(String deliveryCarrierNo) {
        this.deliveryCarrierNo = deliveryCarrierNo;
    }

    public String getDeliveryCarrierName() {
        return deliveryCarrierName;
    }

    public void setDeliveryCarrierName(String deliveryCarrierName) {
        this.deliveryCarrierName = deliveryCarrierName;
    }

    public String getDeliveryBillNo() {
        return deliveryBillNo;
    }

    public void setDeliveryBillNo(String deliveryBillNo) {
        this.deliveryBillNo = deliveryBillNo;
    }

    public Double getDeliveryPackageWeight() {
        return deliveryPackageWeight;
    }

    public void setDeliveryPackageWeight(Double deliveryPackageWeight) {
        this.deliveryPackageWeight = deliveryPackageWeight;
    }

    public Date getDeliveryConfirmTime() {
        return deliveryConfirmTime;
    }

    public void setDeliveryConfirmTime(Date deliveryConfirmTime) {
        this.deliveryConfirmTime = deliveryConfirmTime;
    }

    public Integer getOrderPayType() {
        return orderPayType;
    }

    public void setOrderPayType(Integer orderPayType) {
        this.orderPayType = orderPayType;
    }

    public Long getOrderTotalMoney() {
        return orderTotalMoney;
    }

    public void setOrderTotalMoney(Long orderTotalMoney) {
        this.orderTotalMoney = orderTotalMoney;
    }

    public Long getOrderDiscountMoney() {
        return orderDiscountMoney;
    }

    public void setOrderDiscountMoney(Long orderDiscountMoney) {
        this.orderDiscountMoney = orderDiscountMoney;
    }

    public Long getOrderFreightMoney() {
        return orderFreightMoney;
    }

    public void setOrderFreightMoney(Long orderFreightMoney) {
        this.orderFreightMoney = orderFreightMoney;
    }

    public Long getPackagingMoney() {
        return packagingMoney;
    }

    public void setPackagingMoney(Long packagingMoney) {
        this.packagingMoney = packagingMoney;
    }

    public Long getOrderBuyerPayableMoney() {
        return orderBuyerPayableMoney;
    }

    public void setOrderBuyerPayableMoney(Long orderBuyerPayableMoney) {
        this.orderBuyerPayableMoney = orderBuyerPayableMoney;
    }

    public Integer getOrderInvoiceOpenMark() {
        return orderInvoiceOpenMark;
    }

    public void setOrderInvoiceOpenMark(Integer orderInvoiceOpenMark) {
        this.orderInvoiceOpenMark = orderInvoiceOpenMark;
    }

    public Boolean getAdjustIsExists() {
        return adjustIsExists;
    }

    public void setAdjustIsExists(Boolean adjustIsExists) {
        this.adjustIsExists = adjustIsExists;
    }

    public Long getAdjustId() {
        return adjustId;
    }

    public void setAdjustId(Long adjustId) {
        this.adjustId = adjustId;
    }

    public Date getTs() {
        return ts;
    }

    public void setTs(Date ts) {
        this.ts = ts;
    }

    public Date getLatestTime() {
        return latestTime;
    }

    public void setLatestTime(Date latestTime) {
        this.latestTime = latestTime;
    }

    public OrderExtend getOrderExtend() {
        return orderExtend;
    }

    public void setOrderExtend(OrderExtend orderExtend) {
        this.orderExtend = orderExtend;
    }

    public List<OrderProductDTO> getProductList() {
        return productList;
    }

    public void setProductList(List<OrderProductDTO> productList) {
        this.productList = productList;
    }

    public List<OrderDiscountDTO> getDiscountList() {
        return discountList;
    }

    public void setDiscountList(List<OrderDiscountDTO> discountList) {
        this.discountList = discountList;
    }
}
