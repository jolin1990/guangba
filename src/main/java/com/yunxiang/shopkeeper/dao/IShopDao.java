package com.yunxiang.shopkeeper.dao;


import com.yunxiang.shopkeeper.model.Shop;

/**
 * @desc: Created by jiely on 2015/9/1.
 */
public interface IShopDao {
    /**
     * 不同位置
     * throws Exception
     */
    public void clear() throws Exception;
    public void add(Shop shop) throws Exception;
    public void update(Shop shop) throws Exception;
    public Shop find() throws Exception;

}
