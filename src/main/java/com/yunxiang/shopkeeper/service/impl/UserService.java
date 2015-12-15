package com.yunxiang.shopkeeper.service.impl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.yunxiang.shopkeeper.TApplication;
import com.yunxiang.shopkeeper.dao.IUserDao;
import com.yunxiang.shopkeeper.dao.dbhelper.DBHelper;
import com.yunxiang.shopkeeper.dao.dbhelper.DatabaseManager;
import com.yunxiang.shopkeeper.dao.impl.UserDao;
import com.yunxiang.shopkeeper.model.User;
import com.yunxiang.shopkeeper.service.interFace.IUserService;


public class UserService implements IUserService {
	private DBHelper helper;
	private static Context context;
	private SQLiteDatabase db;//DatabaseManager.getInstance().openDatabase();
	IUserDao userdao;
	
	private static UserService instance;
	public static UserService getInstanse(){
		if(instance == null){
			instance = new UserService();
		}
		return instance;
	}
	
	//写入 ，不然会是出错，是空指针
	private UserService(){
		context = TApplication.context;
		helper = new DBHelper(context);
		//LogUtil.i("UserService", "helper"+helper);
		DatabaseManager.initialize(context, helper);
		db = DatabaseManager.getInstance().openDatabase();
		userdao = new UserDao(db);
	}
	
	
	@Override
	public void clear()  {
		db = DatabaseManager.getInstance().openDatabase();		
		db.beginTransaction();		
		try {			
			userdao.clear();
			
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
	public void refresh(User user)  {
		db = DatabaseManager.getInstance().openDatabase();		
		db.beginTransaction();		
		try {			
			userdao.clear();
			userdao.add(user);
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
	public User getUser()  {
		db = DatabaseManager.getInstance().openDatabase();			
		User user = null;
		try {			
			user = userdao.find();
		} catch (Exception e) {			 
			e.printStackTrace();
			 
		}finally{
            DatabaseManager.getInstance().closeDatabase();          
		}
		return user;
	}


	@Override
	public void modify(User user) {
		db = DatabaseManager.getInstance().openDatabase();		
		db.beginTransaction();	
		 
		try {			
			userdao.update(user);
			
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
