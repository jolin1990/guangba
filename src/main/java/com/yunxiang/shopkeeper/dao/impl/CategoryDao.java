package com.yunxiang.shopkeeper.dao.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.yunxiang.shopkeeper.dao.ICategoryDao;
import com.yunxiang.shopkeeper.model.Category;
import com.yunxiang.shopkeeper.utils.DebugUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * @desc: Created by jiely on 2015/9/1.
 */
public class CategoryDao implements ICategoryDao{
    private static final String SQL_INSERT = "Insert into category(categoryId,name)" + " values(?,?)";
    private static final String SQL_SELECT_ALL = "select categoryId,name" + " from category";
    private static final String SQL_SELECT_ONE = "select categoryId,name" + " from category where categoryId=?";
    private static final String SQL_CLEAR = "delete from category";
    private SQLiteDatabase db;

    public CategoryDao(SQLiteDatabase db) {
        super();
        this.db = db;
    }


    @Override
    public void clear() throws Exception {
        db.execSQL(SQL_CLEAR);
    }

    @Override
    public void delete(Category category) throws Exception {
        long id = category.getCategoryId();
        String[] args = {String.valueOf(id)};
        db.delete("category", "categoryId=?", args);

    }

    @Override
    public void add(Category category) throws Exception {
        db.execSQL(SQL_INSERT, new Object[] {
                category.getCategoryId(), category.getName()});
        DebugUtils.i("categorydao_add", "categoryId=" + category.getCategoryId());
    }

    @Override
    public void update(Category category) throws Exception {
        ContentValues cv = new ContentValues();
        cv.put("categoryId", category.getCategoryId());
        cv.put("name", category.getName());

        String[] args = {String.valueOf(category.getCategoryId())};
        db.update("category", cv, "categoryid=?", args);
    }

    @Override
    public Category find(int id) throws Exception {
        String sql = SQL_SELECT_ONE;
        Category category = null;
        Cursor cursor = null;

        try {
            cursor=db.rawQuery(sql, new String[]{String.valueOf(id)});
            if(cursor.moveToNext()) {
                category = new Category();
                category.setCategoryId(id);
                category.setName(cursor.getString(cursor.getColumnIndex("name")));
            }} catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(cursor != null){
                cursor.close();
            }
        }
        return category;
    }

    @Override
    public void addList(List<Category> categories) throws Exception {
        for (Category category : categories) {
            db.execSQL(SQL_INSERT, new Object[] {
                    category.getCategoryId(), category.getName()});
        }
    }

    @Override
    public List<Category> findAll() throws Exception {
        List<Category> categories = new ArrayList<Category>();
        Cursor cursor = null;
        try {
            cursor=db.rawQuery(SQL_SELECT_ALL, null);
            while(cursor.moveToNext()){
                Category category = new Category();
                category.setCategoryId(cursor.getInt(cursor.getColumnIndex("categoryId")));
                category.setName(cursor.getString(cursor.getColumnIndex("name")));
                categories.add(category);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }finally{
            if(cursor != null){
                cursor.close();
            }
        }
        return categories;
    }
}
