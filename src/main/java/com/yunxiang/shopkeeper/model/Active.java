package com.yunxiang.shopkeeper.model;

/**
 * desc: 活动类
 */
public class Active {
    private long activeId;
    private String activeName;
    private String description;
    private String imgUrl;
    private String StartDate;
    private String StopDate;
    private boolean isOverdate;//是否过期

    public boolean isOverdate() {
        return isOverdate;
    }

    public void setIsOverdate(boolean isOverdate) {
        this.isOverdate = isOverdate;
    }

    public long getActiveId() {
        return activeId;
    }

    public void setActiveId(long activeId) {
        this.activeId = activeId;
    }

    public String getActiveName() {
        return activeName;
    }

    public void setActiveName(String activeName) {
        this.activeName = activeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getStopDate() {
        return StopDate;
    }

    public void setStopDate(String stopDate) {
        StopDate = stopDate;
    }
}
