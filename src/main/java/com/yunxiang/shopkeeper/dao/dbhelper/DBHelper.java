package com.yunxiang.shopkeeper.dao.dbhelper;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.yunxiang.shopkeeper.utils.Const;


public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, Const.SQLITE_DBNAME, null, Const.SQLITE_VERTION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            //创建user表
            String sql = "create table if not exists user(" +
                    "userId Integer," +
                    "userName varchar(50)," +
                    "password varchar(30)," +
                    "screenName varchar(80)," +
                    "emailAddress varchar(50)," +
                    "companyId Integer," +
                    "createDate varchar(50)," +
                    "modifiedDate varchar(50)," +
                    "loginIP Integer,"+
                    "loginDate varchar(50)," +
                    "lastLoginIP Integer," +
                    "lastLoginDate varchar(50)," +
                    "lastFailedLoginDate varchar(50)," +
                    "failedLoginAttempts Integer," +
                    "lockout Integer,"+
                    "lockoutDate varchar(50)," +
                    "status Integer)";
            db.execSQL(sql);

            //创建shop表
            sql = "create table if not exists shop(" +
                    "shopId Integer," +
                    "shopName varchar(50)," +
                    "phone varchar(30)," +
                    "address varchar(50)," +
                    "typeId Integer," +
                    "imgUrl varchar(200)," +
                    "bannerIds varchar(200)," +
                    "describe varchar(300))";
            db.execSQL(sql);

            //创建category表
            sql = "create table if not exists category(" +
                    "categoryId Integer PRIMARY KEY," +
                    "name varchar(50))";
            db.execSQL(sql);

            //创建 merchandiseId,categoryId,name,smallImageURL,description,isRecommend,isSoldOut,price
            sql = "create table if not exists merchandise(" +
                    "merchandiseId Integer PRIMARY KEY," +
                    "categoryId Integer," +
                    "name varchar(50)," +
                    "smallImageURL varchar(80)," +
                    "description varchar(330)," +
                    "isRecommend Integer,"+
                    "isSoldOut Integer,"+
                    "price FLOAT)";
            db.execSQL(sql);

            //创建active表
            sql = "create table if not exists active(" +
                    "activeId Integer PRIMARY KEY," +
                    "activeName varchar(50)," +
                    "description varchar(80)," +
                    "imgUrl varchar(30)," +
                    "StartDate varchar(50)," +
                    "StopDate varchar(300)," +
                    "isOverdate Integer)";
            db.execSQL(sql);

            //创建字典表
            sql = "create table if not exists dictionary(" +
                    "strkey varchar(12) PRIMARY KEY," +
                    "strvalue varchar(50))";
            db.execSQL(sql);
            Log.i("HuakuDbHelper", "dictionary table create ok");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if exists user");
        onCreate(db);
        db.execSQL("drop table if exists shop");
        onCreate(db);
        db.execSQL("drop table if exists category");
        onCreate(db);
        db.execSQL("drop table if exists merchandise");
        onCreate(db);
        db.execSQL("drop table if exists active");
        db.execSQL("drop table if exists dictionary");
        onCreate(db);
    }


    /**
     * Sqlite里面有一个新鲜玩意“INSERTORREPLACE”，跟Mysql类似，这个结构能够保证在存在的情况下替换，不存在的情况下更新,
     * 这种方法要避免插入操作，首先要确保是依照主键执行的更新，如果where条件不是主键可能就有点麻烦
     * insertorreplaceinto t1(key,Column1,Column2)  SELECT t2.key,t2.Column1,t2.Column2  FROM t2,t1  WHERE  t2.key=t1.key;
     *
     *  不是主键的更新
     *  update t1 set column1=(select columnx from t2 where t2.key=t1.key), column2=
     *  (select columny from t2 where t2.key=t1.key),
     where t1.key = (select key from t2 where t2.key=t1.key);

     下面举一个主从表的例子，一个部门表，一个成员表，成员表中的部门名称和代码是冗余的信息，以部门表中的部门名称和代码更新成员表中的冗余信息：
     update userlogins set deptname=(select deptname from DepartMents where DepartMents.[DeptID] = userlogins.[DeptID]),
     deptcode=(select deptcode from DepartMents where DepartMents.[DeptID]=userlogins.[DeptID])
     where userlogins.[DeptID] = (SELECT [DeptID] FROM DepartMents where DepartMents.[DeptID]=userlogins.[DeptID]);
     */


}
