package com.yunxiang.shopkeeper.service.impl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.yunxiang.shopkeeper.TApplication;
import com.yunxiang.shopkeeper.dao.ICategoryDao;
import com.yunxiang.shopkeeper.dao.dbhelper.DBHelper;
import com.yunxiang.shopkeeper.dao.dbhelper.DatabaseManager;
import com.yunxiang.shopkeeper.dao.impl.CategoryDao;
import com.yunxiang.shopkeeper.model.Category;
import com.yunxiang.shopkeeper.service.interFace.ICategoryService;

import java.util.List;


public class CategoryService implements ICategoryService {
	private static Context context;
	private DBHelper helper;
	
	private SQLiteDatabase db;//DatabaseManager.getInstance().openDatabase();
	ICategoryDao categoryDao;
	
	private static CategoryService instance;
	public static CategoryService getInstanse(){
		context = TApplication.context;
		if(instance == null){
			instance = new CategoryService();
		}
		return instance;
	}
	
	//写入 ，不然会是出错，是空指针
	private CategoryService(){
		helper = new DBHelper(context);
		//LogUtil.i("categoryService", "helper"+helper);
		DatabaseManager.initialize(context, helper);
		db = DatabaseManager.getInstance().openDatabase();
		categoryDao = new CategoryDao(db);
	}

	@Override
	public void addCategory(Category category) {
		db = DatabaseManager.getInstance().openDatabase();
		db.beginTransaction();

		try {
			categoryDao.add(category);

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
	public void addCategoryList(List<Category> categoryList) {
		db = DatabaseManager.getInstance().openDatabase();
		db.beginTransaction();

		try {
			categoryDao.addList(categoryList);
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
	public List<Category> getAll() {
		db = DatabaseManager.getInstance().openDatabase();
		List<Category> categories = null;
		try {
			categories = categoryDao.findAll();
		} catch (Exception e) {
			e.printStackTrace();

		}finally{
			DatabaseManager.getInstance().closeDatabase();
		}
		return categories;
	}

	@Override
	public Category getCategory(int id) {
		db = DatabaseManager.getInstance().openDatabase();
		Category category = null;
		try {
			category = categoryDao.find(id);
		} catch (Exception e) {
			e.printStackTrace();

		}finally{
			DatabaseManager.getInstance().closeDatabase();
		}
		return category;
	}

	@Override
	public void deleteAll() {
		db = DatabaseManager.getInstance().openDatabase();
		db.beginTransaction();
		try {
			categoryDao.clear();

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
	public void delete(Category category) {
		db = DatabaseManager.getInstance().openDatabase();
		db.beginTransaction();
		try {
			categoryDao.delete(category);
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
	public void modify(Category category) {
		db = DatabaseManager.getInstance().openDatabase();
		db.beginTransaction();

		try {
			categoryDao.update(category);

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
