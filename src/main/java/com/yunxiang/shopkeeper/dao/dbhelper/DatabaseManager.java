package com.yunxiang.shopkeeper.dao.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * ************************************************************************************************************
 * @author：jiely
 * Date：2015-2-13
 * Description: 数据库管理类，保证只开启一个连接
 * Company：中交讯通
 * @version 0.1
 **************************************************************************************************************
 */
public class DatabaseManager {
	
	private AtomicInteger mOpenCounter = new AtomicInteger();
	private static DatabaseManager instance;	
    private static SQLiteOpenHelper mDatabaseHelper;
    private SQLiteDatabase mDatabase;

    
    public static synchronized void initialize(Context context, SQLiteOpenHelper helper) {
        if (instance == null) {
            instance = new DatabaseManager();
            mDatabaseHelper = helper;
        }
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException(DatabaseManager.class.getSimpleName() +
                    " is not initialized, call initialize(..) method first.");
        }

        return instance;
    }

    public synchronized SQLiteDatabase getDatabase() {
        return  mDatabaseHelper.getWritableDatabase();
    }
    
    /**
     * 
    * @author：jiely
    * @Date：2015-2-13
    * @Title: openDatabase 
    * @Description: 
    * @param @return    设定文件 
    * @return SQLiteDatabase    返回类型 
    * @throws
     */
    public synchronized SQLiteDatabase openDatabase() {
        if(mOpenCounter.incrementAndGet() == 1) {
            // Opening new database
            mDatabase = mDatabaseHelper.getWritableDatabase();
        }
        return mDatabase;
    }

    public synchronized void closeDatabase() {
    	int size = mOpenCounter.decrementAndGet();
        if(size == 0) {
            // Closing database
            mDatabase.close();

        }
    }
}
