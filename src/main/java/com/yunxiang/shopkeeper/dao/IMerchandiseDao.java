package com.yunxiang.shopkeeper.dao;


import com.yunxiang.shopkeeper.model.Merchandise;

import java.util.List;

/**
 * @desc: Created by jiely on 2015/9/1.
 */
public interface IMerchandiseDao {
    /**
     * 不同位置
     * throws Exception
     */
    public void clear() throws Exception;
    public void add(Merchandise merchandise) throws Exception;
    public void update(Merchandise merchandise) throws Exception;
    public Merchandise find(int id) throws Exception;
    public void addList(List<Merchandise> categories) throws Exception;
    public List<Merchandise> findAll() throws Exception;
    public List<Merchandise> findByCategoryId(int id) throws Exception;
    public List<Merchandise> findRecommendCategorys() throws Exception;

}
