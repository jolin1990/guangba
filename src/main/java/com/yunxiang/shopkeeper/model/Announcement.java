package com.yunxiang.shopkeeper.model;

/**
 * @desc: Created by jiely on 2015/12/3.
 */
public class Announcement {

    /**
     * id_:公告id
     */
    private Long id;

    /**
     * content:公告内容
     */
    private String content;

    /**
     * createDate:创建日期
     */
    private String createDate;

    /**
     * imageId:公告图片Id
     */
    private String imageId;

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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }
}
