package com.yunxiang.shopkeeper.dao;

/**
 * @desc: Created by jiely on 2015/9/1.
 */
public interface IDictionaryDao {
    public void addString(String name, String value) throws Exception;
    public void update(String name, String value) throws Exception;
    public String findValue(String name) throws Exception;
}
