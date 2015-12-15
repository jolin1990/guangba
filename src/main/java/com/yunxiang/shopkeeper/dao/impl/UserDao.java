package com.yunxiang.shopkeeper.dao.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yunxiang.shopkeeper.dao.IUserDao;
import com.yunxiang.shopkeeper.model.User;


/**
 * desc: Created by jiely on 2015/9/1.
 "userId Integer," +
 "userName varchar(50)," +
 "password varchar(30)," +
 "screenName varchar(80)," +
 "emailAddress varchar(50)," +
 "companyId Integer," +
 "createDate date," +
 "modifiedDate date," +
 "loginIP Integer,"+
 "loginDate date," +
 "lastLoginIP Integer," +
 "lastLoginDate date," +
 "lastFailedLoginDate date," +
 "failedLoginAttempts Integer," +
 "lockout Integer,"+
 "lockoutDate date," +
 "status Integer";
 */
public class UserDao implements IUserDao {

    private static final String SQL_INSERT = "Insert into user(userId,userName,password,screenName,emailAddress," +
            "companyId,createDate,modifiedDate,loginIP,loginDate,lastLoginIP,lastLoginDate,lastFailedLoginDate," +
            "failedLoginAttempts,lockout,lockoutDate,status)" + " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String SQL_SELECT_ALL = "select userId,userName,password,screenName,emailAddress," +
            "companyId,createDate,modifiedDate,loginIP,loginDate,lastLoginIP,lastLoginDate,lastFailedLoginDate," +
            "failedLoginAttempts,lockout,lockoutDate,status" + " from user";

    private static final String SQL_CLEAR = "delete from user";

    private SQLiteDatabase db;

    public UserDao(SQLiteDatabase db) {
        super();
        this.db = db;
    }

    @Override
    public void clear() throws Exception {
        db.execSQL(SQL_CLEAR);
    }

    @Override
    public void add(User user) throws Exception {
        //String sql="Insert into user(userId,userName,password) values(?,?,?)";
        db.execSQL(SQL_INSERT, new Object[] {
                user.getUserId(),
                user.getUserName(),
                user.getPassword(),
                user.getScreenName(),
                user.getEmailAddress(),
                user.getCompanyId(),
                user.getCreateDate(),
                user.getModifiedDate(),
                user.getLoginIP(),
                user.getLoginDate(),
                user.getLastLoginIP(),
                user.getLastLoginDate(),
                user.getLastFailedLoginDate(),
                user.getFailedLoginAttempts(),
                user.getLockout(),
                user.getLockoutDate(),
                user.getStatus()} );
    }

    @Override
    public void update(User user) throws Exception {
/*
serId,userName,password,screenName,emailAddress," +
            "companyId,createDate,modifiedDate,loginIP,loginDate,lastLoginIP,lastLoginDate,lastFailedLoginDate," +
            "failedLoginAttempts,lockout,lockoutDate,status
 */
        ContentValues cv = new ContentValues();
        cv.put("userId", user.getUserId());
        cv.put("userName", user.getUserName());
        cv.put("password", user.getPassword());
        cv.put("screenName", user.getScreenName());
        cv.put("emailAddress", user.getEmailAddress());
        cv.put("companyId", user.getCompanyId());
        cv.put("createDate", user.getCreateDate());
        cv.put("modifiedDate", user.getModifiedDate());
        cv.put("loginIP", user.getLoginIP());
        cv.put("loginDate", user.getLoginDate());
        cv.put("lastLoginIP", user.getLastLoginIP());
        cv.put("lastLoginDate", user.getLastLoginDate());
        cv.put("lastFailedLoginDate", user.getLastFailedLoginDate());
        cv.put("failedLoginAttempts", user.getFailedLoginAttempts());
        cv.put("lockout", user.getLockout());
        cv.put("lockoutDate", user.getLockoutDate());
        cv.put("status", user.getStatus());

        String[] args = {String.valueOf(user.getUserId())};
        db.update("user", cv, "userId=?", args);
    }

    @Override
    public User find() throws Exception {
        User user = null;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(SQL_SELECT_ALL, new String[]{});
            user = null;
            if (cursor.moveToFirst()) {
                user = new User();
                user.setUserId(cursor.getLong(cursor.getColumnIndex("userId")));
                user.setUserName(cursor.getString(cursor.getColumnIndex("userName")));
                user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
                user.setScreenName(cursor.getString(cursor.getColumnIndex("screenName")));
                user.setEmailAddress(cursor.getString(cursor.getColumnIndex("emailAddress")));
                user.setCompanyId(cursor.getLong(cursor.getColumnIndex("companyId")));
                user.setCreateDate(cursor.getString(cursor.getColumnIndex("createDate")));
                user.setModifiedDate(cursor.getString(cursor.getColumnIndex("modifiedDate")));
                user.setLoginIP(cursor.getString(cursor.getColumnIndex("loginIP")));
                user.setLoginDate(cursor.getString(cursor.getColumnIndex("loginDate")));
                user.setLastLoginIP(cursor.getString(cursor.getColumnIndex("lastLoginIP")));
                user.setLastLoginDate(cursor.getString(cursor.getColumnIndex("lastLoginDate")));
                user.setLastFailedLoginDate(cursor.getString(cursor.getColumnIndex("lastFailedLoginDate")));
                user.setFailedLoginAttempts(cursor.getInt(cursor.getColumnIndex("failedLoginAttempts")));
                user.setLockout(cursor.getInt(cursor.getColumnIndex("lockout")));
                user.setLockoutDate(cursor.getString(cursor.getColumnIndex("lockoutDate")));
                user.setStatus(cursor.getInt(cursor.getColumnIndex("status")));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(cursor != null){
                cursor.close();
            }
        }
        return user;
    }
}
