package com.yunxiang.shopkeeper.model;

/**
 * Created by yunxiang on 2015/11/6.
 */
public class UserMessage {

    /**
     * id_:留言id
     */
    private Long id;

    /**
     * content:留言内容
     */
    private String content;

    /**
     * customerName:顾客用户名称
     */
    private String customerName;
    /**
     * createDate:创建日期
     */
    private String createDate;

    private String imgId;//消费者的头像

    private String messageReply; //商户回复

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getImgId() {
        return imgId;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }

    public String getMessageReply() {
        return messageReply;
    }

    public void setMessageReply(String messageReply) {
        this.messageReply = messageReply;
    }
}
