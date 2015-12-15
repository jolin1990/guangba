package com.yunxiang.shopkeeper.model;

/**
 * desc: 红包或代金券
 * auther:jiely
 * create at 2015/11/20 17:46
 */
public class Packet {
    private String customerName;    //客户名称
    private String money;           //金额
    private String updateTime;      //领取时间
    private long headImgId;          //头像

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }


    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public long getHeadImgId() {
        return headImgId;
    }

    public void setHeadImgId(int headImgId) {
        this.headImgId = headImgId;
    }
}
