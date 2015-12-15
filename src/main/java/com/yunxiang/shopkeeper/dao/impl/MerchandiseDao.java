package com.yunxiang.shopkeeper.dao.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yunxiang.shopkeeper.dao.IMerchandiseDao;
import com.yunxiang.shopkeeper.model.Merchandise;
import com.yunxiang.shopkeeper.utils.DebugUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * @desc: Created by jiely on 2015/9/1.
 */
public class MerchandiseDao implements IMerchandiseDao {

    /* "merchandiseId Integer PRIMARY KEY," +
                    "categoryId Integer," +
                    "name varchar(50)," +
                    "smallImageURL varchar(80)," +
                    "description varchar(30)," +
                    "isRecommend Integer,"+
                    "isSoldOut Integer,"+
                    "price FLOAT)";;*/
    private static final String SQL_INSERT = "Insert into merchandise(merchandiseId,categoryId,name,smallImageURL,description,isRecommend,isSoldOut,price)"
            +" values(?,?,?,?,?,?,?,?)";
    private static final String SQL_SELECT_ALL = "select merchandiseId,categoryId,name,smallImageURL,description,isRecommend,isSoldOut,price"
            + " from merchandise";
    private static final String SQL_SELECT_ONE = "select merchandiseId,categoryId,name,smallImageURL,description,isRecommend,isSoldOut,price"
            + " from merchandise where merchandiseId=?";
    private static final String SQL_SELECT_LIST_BY_CATEGORYID = "select merchandiseId,categoryId,name,smallImageURL,description,isRecommend,isSoldOut,price"
            + " from merchandise where categoryId=?";
    private static final String SQL_SELECT_LIST_RECMMAND_CATEGORYID = "select merchandiseId,categoryId,name,smallImageURL,description,isRecommend,isSoldOut,price"
            + " from merchandise where isRecommend=1 and isSoldOut=1";
    private static final String SQL_CLEAR = "delete from merchandise";
    private SQLiteDatabase db;

    public MerchandiseDao(SQLiteDatabase db) {
        super();
        this.db = db;
    }


    @Override
    public void clear() throws Exception {
        db.execSQL(SQL_CLEAR);
    }

    @Override
    public void add(Merchandise merchandise) throws Exception {
        db.execSQL(SQL_INSERT, new Object[]{
                merchandise.getId(), merchandise.getName(), merchandise.getCategoryId(), merchandise.getDescription()
                , merchandise.getSmallImageURL(), merchandise.isRecommend(), merchandise.isSoldOut(), merchandise.getPrice()});
        DebugUtils.i("merchandisedao_add", "merchandiseId=" + merchandise.getId());
    }

    @Override
    public void update(Merchandise merchandise) throws Exception {
        ContentValues cv = new ContentValues();
        cv.put("merchandiseId", merchandise.getId());
        cv.put("name", merchandise.getName());
        cv.put("categoryId", merchandise.getCategoryId());
        cv.put("smallImageURL", merchandise.getSmallImageURL());
        int num = merchandise.isRecommend()?1:0;
        cv.put("isRecommend", num);
        num = merchandise.isSoldOut()?0:1;
        cv.put("isSoldOut", num);
        cv.put("description", merchandise.getDescription());
        cv.put("price", merchandise.getPrice());

        String[] args = {String.valueOf(merchandise.getId())};
        db.update("merchandise", cv, "merchandiseId=?", args);
    }

    @Override
    public Merchandise find(int id) throws Exception {
        String sql = SQL_SELECT_ONE;
        Merchandise merchandise = null;
        Cursor cursor = null;

        try {
            cursor=db.rawQuery(sql, new String[]{String.valueOf(id)});
            if(cursor.moveToNext()) {
                merchandise = getMerchandise(cursor);
            }} catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(cursor != null){
                cursor.close();
            }
        }
        return merchandise;
    }

    @Override
    public void addList(List<Merchandise> categories) throws Exception {
        //merchandiseId,categoryId,name,smallImageURL,description,isRecommend,isSoldOut,price
        try {
            for (Merchandise merchandise : categories) {
                int recommend = merchandise.isRecommend()?1:0;
                int soldout = merchandise.isSoldOut()?0:1;
                db.execSQL(SQL_INSERT, new Object[] {
                        merchandise.getId(),
                        merchandise.getCategoryId(),
                        merchandise.getName(),
                        merchandise.getSmallImageURL(),
                        merchandise.getDescription(),
                        recommend,
                        soldout,
                        merchandise.getPrice()});
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Merchandise> findAll() throws Exception {
        List<Merchandise> categories = new ArrayList<Merchandise>();
        Cursor cursor = null;
        try {
            cursor=db.rawQuery(SQL_SELECT_ALL, null);
            while(cursor.moveToNext()){
                Merchandise merchandise = getMerchandise(cursor);
                categories.add(merchandise);
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

    @Override
    public List<Merchandise> findByCategoryId(int id) throws Exception {
        List<Merchandise> categories = new ArrayList<Merchandise>();
        Cursor cursor = null;
        try {
            cursor=db.rawQuery(SQL_SELECT_LIST_BY_CATEGORYID, new String[]{String.valueOf(id)});
            while(cursor.moveToNext()){
                Merchandise merchandise = getMerchandise(cursor);
                categories.add(merchandise);
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

    @Override
    public List<Merchandise> findRecommendCategorys() throws Exception {
        List<Merchandise> categories = new ArrayList<Merchandise>();
        Cursor cursor = null;
        try {
            cursor=db.rawQuery(SQL_SELECT_LIST_RECMMAND_CATEGORYID,null);
            while(cursor.moveToNext()){
                Merchandise merchandise = getMerchandise(cursor);
                categories.add(merchandise);
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

    private Merchandise getMerchandise(Cursor cursor){
        Merchandise merchandise = new Merchandise();
        merchandise.setId(cursor.getInt(cursor.getColumnIndex("merchandiseId")));
        merchandise.setName(cursor.getString(cursor.getColumnIndex("name")));
        merchandise.setCategoryId(cursor.getInt(cursor.getColumnIndex("categoryId")));
        merchandise.setDescription(cursor.getString(cursor.getColumnIndex("description")));
        int is = cursor.getInt(cursor.getColumnIndex("isRecommend"));
        boolean isRecommend = is==0 ? false:true;
        is = cursor.getInt(cursor.getColumnIndex("isSoldOut"));
        boolean isSoldOut = is==0 ? true:false;
        merchandise.setIsRecommend(isRecommend);
        merchandise.setIsSoldOut(isSoldOut);
        merchandise.setPrice(cursor.getFloat(cursor.getColumnIndex("price")));
        return merchandise;
    }
}
