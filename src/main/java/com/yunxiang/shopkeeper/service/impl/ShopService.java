package com.yunxiang.shopkeeper.service.impl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.yunxiang.shopkeeper.TApplication;
import com.yunxiang.shopkeeper.dao.IShopDao;
import com.yunxiang.shopkeeper.dao.dbhelper.DBHelper;
import com.yunxiang.shopkeeper.dao.dbhelper.DatabaseManager;
import com.yunxiang.shopkeeper.dao.impl.ShopDao;
import com.yunxiang.shopkeeper.model.Shop;
import com.yunxiang.shopkeeper.service.interFace.IShopService;


public class ShopService implements IShopService {
	private DBHelper helper;
	private SQLiteDatabase db;//DatabaseManager.getInstance().openDatabase();
	IShopDao shopdao;
	
	private static ShopService instance;
	public static ShopService getInstanse(){
		if(instance == null){
			instance = new ShopService();
		}
		return instance;
	}
	
	//写入 ，不然会是出错，是空指针
	private ShopService(){
		Context context = TApplication.context;
		helper = new DBHelper(context);
		//LogUtil.i("shopService", "helper"+helper);
		DatabaseManager.initialize(context, helper);
		db = DatabaseManager.getInstance().openDatabase();
		shopdao = new ShopDao(db);
	}
	
	
	@Override
	public void clear()  {
		db = DatabaseManager.getInstance().openDatabase();		
		db.beginTransaction();		
		try {			
			shopdao.clear();
			
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
	public void refresh(Shop shop)  {
		db = DatabaseManager.getInstance().openDatabase();		
		db.beginTransaction();		
		try {			
			shopdao.clear();
			shopdao.add(shop);
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
	public Shop getShop()  {
		db = DatabaseManager.getInstance().openDatabase();			
		Shop shop = null;
		try {			
			shop = shopdao.find();
		} catch (Exception e) {			 
			e.printStackTrace();
			 
		}finally{
            DatabaseManager.getInstance().closeDatabase();          
		}
		return shop;
	}


	@Override
	public void modify(Shop shop) {
		db = DatabaseManager.getInstance().openDatabase();		
		db.beginTransaction();	
		 
		try {			
			shopdao.update(shop);
			
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
