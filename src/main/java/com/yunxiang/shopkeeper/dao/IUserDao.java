package com.yunxiang.shopkeeper.dao;


import com.yunxiang.shopkeeper.model.User;

/**
 * @desc: Created by jiely on 2015/9/1.
 */
public interface IUserDao {
    /**
     * 不同位置
     * throws Exception
     */
    public void clear() throws Exception;
    public void add(User user) throws Exception;
    public void update(User user) throws Exception;
    public User find() throws Exception;

}
