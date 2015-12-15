package com.yunxiang.shopkeeper.model;

import java.util.List;

/**
 * desc:
 * auther:jiely
 * create at 2015/11/26 18:54
 */
public class Order {
    private long orderId;
    private String customerName;            //订单客户名称
    private  double discount;               //折扣
    private String sendingDate;             //起送时间
    private String creatDate;               //下单时间
    private String receivingDate;           //接单时间
    private String deliverDate;             //送货时间
    private String finishDate;              //完成时间或取消时间
    private String imgUrl;                  //用户头像
    private String serialNumber;            //订单流水号
    private double price;                  //订单总金额
    private Address address;

    private List<Merchandise> merchandises;  //订单商品列表

    /**
	 * status:TODO(订单状态).
     * 新订单  0新订单未支付 1新订单已支付
     * 已接单  2已接单待送货中 3已接单商家送货中
     * 已完成  4已完成用户确认完成 5已完成商家确认完成
	 * 已取消  6取消用户取消 7取消商家拒单
	 */
    private int status;                     //订单类型 0,1：新订单 2,3:已接单 4,5：已完成 6,7:取消

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Merchandise> getMerchandises() {
        return merchandises;
    }

    public void setMerchandises(List<Merchandise> merchandises) {
        this.merchandises = merchandises;
    }

    public String getSendingDate() {
        return sendingDate;
    }

    public void setSendingDate(String sendingDate) {
        this.sendingDate = sendingDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }


    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCreatDate() {
        return creatDate;
    }

    public void setCreatDate(String creatDate) {
        this.creatDate = creatDate;
    }

    public String getReceivingDate() {
        return receivingDate;
    }

    public void setReceivingDate(String receivingDate) {
        this.receivingDate = receivingDate;
    }

    public String getDeliverDate() {
        return deliverDate;
    }

    public void setDeliverDate(String deliverDate) {
        this.deliverDate = deliverDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
