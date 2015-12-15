package com.yunxiang.shopkeeper.dao.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yunxiang.shopkeeper.dao.IDictionaryDao;


/**
 * @desc: Created by jiely on 2015/9/1.
 */
public class DictionaryDao implements IDictionaryDao {

    private static final String SQL_INSERT = "Insert into dictionary(strkey,strvalue) values(?,?)";
    private static final String SQL_SELECT_ONE = "select strvalue from dictionary where strkey=?";


    private SQLiteDatabase db;
    public DictionaryDao(SQLiteDatabase db) {
        super();
        this.db = db;
    }

    @Override
    public void addString(String name, String value) throws Exception {
        db.execSQL(SQL_INSERT, new Object[] {name,value } );
    }

    @Override
    public void update(String name, String value) throws Exception {
        ContentValues cv = new ContentValues();
        cv.put("strvalue", value);

        String[] args = {String.valueOf(name)};
        db.update("dictionary", cv, "strkey=?",args);
    }

    @Override
    public String findValue(String name) throws Exception {
        //String sql="select strvalue from dictionary where strkey=?";
        String value = null;
        Cursor cursor = null;
        try {
            cursor=db.rawQuery(SQL_SELECT_ONE, new String[]{ name });

            if(cursor.moveToFirst()){
                value = cursor.getString(cursor.getColumnIndex("strvalue"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(cursor != null){
                cursor.close();
            }
        }

        return value;
    }
}
