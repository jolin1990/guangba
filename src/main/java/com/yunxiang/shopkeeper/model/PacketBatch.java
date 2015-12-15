package com.yunxiang.shopkeeper.model;

/**
 * desc: 红包或代金券批次
 * auther:jiely
 * create at 2015/11/20 17:46
 */
public class PacketBatch {
    private long patchId;
    private String name;            //红包名称
    private int type;               //红包类型  0：普通红包 1 拼手气 2 折扣券 3 抵用券
    private String amountMoney;     //总金额
    private String createDate;      //创建日期
    private String discount;        //折扣
    private String totalCount;      //发送红包的总数
    private String receiveCount;    //领取的红包数量
    private String moneyLimit;      //限定金额
    private String startDate;       //开始时间
    private String stopDate;        //结束时间
    private String desc;            //描述信息
    private boolean isTimeOut;      //是否过去
    private int status;     //status:优惠卷状态-1代表刚发放 0代表已被领取 1被使用或过期
    private String customerIdList;//发送红包给指定用户 {1,2,3}

    public PacketBatch() {
    }

    public PacketBatch(int type, String name,String desc) {
        this.desc = desc;
        this.type = type;
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCustomerIdList() {
        return customerIdList;
    }

    public void setCustomerIdList(String customerIdList) {
        this.customerIdList = customerIdList;
    }

    public boolean isTimeOut() {
        return isTimeOut;
    }

    public void setIsTimeOut(boolean isTimeOut) {
        this.isTimeOut = isTimeOut;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStopDate() {
        return stopDate;
    }

    public void setStopDate(String stopDate) {
        this.stopDate = stopDate;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public long getPatchId() {
        return patchId;
    }

    public void setPatchId(long patchId) {
        this.patchId = patchId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getMoneyLimit() {
        return moneyLimit;
    }

    public void setMoneyLimit(String moneyLimit) {
        this.moneyLimit = moneyLimit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public String getAmountMoney() {
        return amountMoney;
    }

    public void setAmountMoney(String amountMoney) {
        this.amountMoney = amountMoney;
    }

    public String getReceiveCount() {
        return receiveCount;
    }

    public void setReceiveCount(String receiveCount) {
        this.receiveCount = receiveCount;
    }
}
