package com.yunxiang.shopkeeper.dao;


import com.yunxiang.shopkeeper.model.Category;

import java.util.List;

/**
 * @desc: Created by jiely on 2015/9/1.
 */
public interface ICategoryDao {
    /**
     * 不同位置
     * throws Exception
     */
    public void clear() throws Exception;
    public void add(Category category) throws Exception;
    public void update(Category category) throws Exception;
    public Category find(int id) throws Exception;
    public void addList(List<Category> categories) throws Exception;
    public List<Category> findAll() throws Exception;
    public void delete(Category category) throws Exception;

}
