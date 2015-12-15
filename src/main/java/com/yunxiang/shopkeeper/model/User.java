package com.yunxiang.shopkeeper.model;

/**
 * desc: Created by jiely on 2015/10/13.
 *
 "companyId": 0,
 "createDate": "",
 "emailAddress": "",
 "failedLoginAttempts": 0,
 "lastFailedLoginDate": "",
 "lastLoginDate": "",
 "lastLoginIP": "",
 "lockout": 0,
 "lockoutDate": "",
 "loginDate": "",
 "loginIP": "",
 "modifiedDate": "",
 "screenName": "",

 */
public class User {
    private Long userId;//商家用户ID
    private String userName;//商家用户名
    private Long companyId;//用户公司
    private String createDate;//创建时间
    private String modifiedDate;//修改时间
    private String password;//用户密码
    private String screenName;//用户别名
    private String emailAddress;//用户邮箱地址
    private String loginIP;//登陆IP
    private String loginDate;//登陆日期
    private String lastLoginIP;//最后一次登陆的ip
    private String lastLoginDate;//最后一次登陆时间
    private String lastFailedLoginDate;//最后一次登陆失败时间
    private Integer failedLoginAttempts;//登陆失败原因
    private Integer lockout;//是否锁定,密码输入3次后锁定
    private String lockoutDate;//锁定日期
    private Integer status;//用户状态是否可用

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getLoginIP() {
        return loginIP;
    }

    public void setLoginIP(String loginIP) {
        this.loginIP = loginIP;
    }

    public String getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(String loginDate) {
        this.loginDate = loginDate;
    }

    public String getLastLoginIP() {
        return lastLoginIP;
    }

    public void setLastLoginIP(String lastLoginIP) {
        this.lastLoginIP = lastLoginIP;
    }

    public String getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(String lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getLastFailedLoginDate() {
        return lastFailedLoginDate;
    }

    public void setLastFailedLoginDate(String lastFailedLoginDate) {
        this.lastFailedLoginDate = lastFailedLoginDate;
    }

    public Integer getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    public void setFailedLoginAttempts(Integer failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
    }

    public Integer getLockout() {
        return lockout;
    }

    public void setLockout(Integer lockout) {
        this.lockout = lockout;
    }

    public String getLockoutDate() {
        return lockoutDate;
    }

    public void setLockoutDate(String lockoutDate) {
        this.lockoutDate = lockoutDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
