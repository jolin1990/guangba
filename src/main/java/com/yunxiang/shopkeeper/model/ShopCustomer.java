package com.yunxiang.shopkeeper.model;

/**
 * @desc: Created by jiely on 2015/12/4.
 */
public class ShopCustomer {
    private Long id;

    /**
     * customerId:用户id
     */
    private Long customerId;
    /**
     * customerName:用户姓名
     */
    private String customerName;

    /**
     * createTime:上一次进店时间
     */
    private String createTime;
    /**
     * updateTime:本次进店时间
     */
    private String updateTime;

    /**
     * times:到店次数
     */
    private Integer times;
    /**
     * consumption:顾客消费的总金额
     */
    private Double consumption;
    private int headImgId;//头像
    private boolean isTakeOut;//是否送货

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public Double getConsumption() {
        return consumption;
    }

    public void setConsumption(Double consumption) {
        this.consumption = consumption;
    }

    public int getHeadImgId() {
        return headImgId;
    }

    public void setHeadImgId(int headImgId) {
        this.headImgId = headImgId;
    }

    public boolean isTakeOut() {
        return isTakeOut;
    }

    public void setIsTakeOut(boolean isTakeOut) {
        this.isTakeOut = isTakeOut;
    }
}
