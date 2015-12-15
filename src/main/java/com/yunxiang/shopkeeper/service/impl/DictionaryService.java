package com.yunxiang.shopkeeper.service.impl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.yunxiang.shopkeeper.TApplication;
import com.yunxiang.shopkeeper.dao.IDictionaryDao;
import com.yunxiang.shopkeeper.dao.dbhelper.DBHelper;
import com.yunxiang.shopkeeper.dao.dbhelper.DatabaseManager;
import com.yunxiang.shopkeeper.dao.impl.DictionaryDao;
import com.yunxiang.shopkeeper.service.interFace.IDictionaryService;


/**
 * @desc: Created by jiely on 2015/9/1.
 */
public class DictionaryService implements IDictionaryService {

    private DBHelper helper;

    private SQLiteDatabase db;//DatabaseManager.getInstance().openDatabase();
    IDictionaryDao stringdao;
    private static DictionaryService instance;
    private static Context context;

    public static DictionaryService getInstanse(){
        context = TApplication.context;
        if(instance == null){
            instance = new DictionaryService(context);
        }
        return instance;
    }

    //写入 ，不然会是出错，是空指针
    private DictionaryService(Context context){
        helper = new DBHelper(context);
        //LogUtil.i("StringService", "helper"+helper);
        DatabaseManager.initialize(context, helper);
        db = DatabaseManager.getInstance().openDatabase();
        stringdao = new DictionaryDao(db);
    }

    @Override
    public void modify(String key, String value) {
        db = DatabaseManager.getInstance().openDatabase();
        db.beginTransaction();

        try {
            stringdao.update(key, value);
            db.setTransactionSuccessful();// 设置事务标志为成功，当结束事务时就会提交事务
        } catch (Exception e) {
            e.printStackTrace();

        }finally{
            // 结束事务
            db.endTransaction();
            DatabaseManager.getInstance().closeDatabase();
        }
    }

    @Override
    public String getValue(String key) {
        db = DatabaseManager.getInstance().openDatabase();
        String value = null;
        try {
            value = stringdao.findValue(key);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            DatabaseManager.getInstance().closeDatabase();
        }
        return value;
    }

    @Override
    public void addValue(String key, String value) {
        db = DatabaseManager.getInstance().openDatabase();
        db.beginTransaction();

        try {
            if(stringdao.findValue(key) == null){
                stringdao.addString(key, value);
            }else{
                stringdao.update(key, value);
            }

            db.setTransactionSuccessful();// 设置事务标志为成功，当结束事务时就会提交事务
        } catch (Exception e) {
            e.printStackTrace();

        }finally{
            // 结束事务
            db.endTransaction();
            DatabaseManager.getInstance().closeDatabase();
        }
    }
}
