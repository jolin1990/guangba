package com.yunxiang.shopkeeper.service.impl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.yunxiang.shopkeeper.TApplication;
import com.yunxiang.shopkeeper.dao.IMerchandiseDao;
import com.yunxiang.shopkeeper.dao.dbhelper.DBHelper;
import com.yunxiang.shopkeeper.dao.dbhelper.DatabaseManager;
import com.yunxiang.shopkeeper.dao.impl.MerchandiseDao;
import com.yunxiang.shopkeeper.model.Merchandise;
import com.yunxiang.shopkeeper.service.interFace.IMerchandiseService;

import java.util.List;


public class MerchandiseService implements IMerchandiseService {
	private DBHelper helper;
	private static Context context;
	private SQLiteDatabase db;//DatabaseManager.getInstance().openDatabase();
	private IMerchandiseDao MerchandiseDao;
	
	private static MerchandiseService instance;
	public static MerchandiseService getInstanse(){
		if(instance == null){
			instance = new MerchandiseService();
		}
		return instance;
	}
	
	//写入 ，不然会是出错，是空指针
	private MerchandiseService(){
		context = TApplication.context;
		helper = new DBHelper(context);
		//LogUtil.i("MerchandiseService", "helper"+helper);
		DatabaseManager.initialize(context, helper);
		db = DatabaseManager.getInstance().openDatabase();
		MerchandiseDao = new MerchandiseDao(db);
	}

	@Override
	public void addMerchandise(Merchandise Merchandise) {
		db = DatabaseManager.getInstance().openDatabase();
		db.beginTransaction();

		try {
			MerchandiseDao.add(Merchandise);

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
	public void addMerchandiseList(List<Merchandise> MerchandiseList) {
		db = DatabaseManager.getInstance().openDatabase();
		db.beginTransaction();

		try {
			MerchandiseDao.addList(MerchandiseList);
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
	public List<Merchandise> getAll() {
		db = DatabaseManager.getInstance().openDatabase();
		List<Merchandise> merchandises = null;
		try {
			merchandises = MerchandiseDao.findAll();
		} catch (Exception e) {
			e.printStackTrace();

		}finally{
			DatabaseManager.getInstance().closeDatabase();
		}
		return merchandises;
	}

	@Override
	public List<Merchandise> getMerchandisesForCategory(int categoryId) {
		db = DatabaseManager.getInstance().openDatabase();
		List<Merchandise> merchandises = null;
		try {
			merchandises = MerchandiseDao.findByCategoryId(categoryId);
		} catch (Exception e) {
			e.printStackTrace();

		}finally{
			DatabaseManager.getInstance().closeDatabase();
		}
		return merchandises;
	}

	@Override
	public Merchandise getMerchandise(int id) {
		db = DatabaseManager.getInstance().openDatabase();
		Merchandise Merchandise = null;
		try {
			Merchandise = MerchandiseDao.find(id);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DatabaseManager.getInstance().closeDatabase();
		}
		return Merchandise;
	}

	@Override
	public void deleteAll() {
		db = DatabaseManager.getInstance().openDatabase();
		db.beginTransaction();
		try {
			MerchandiseDao.clear();

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
	public void modify(Merchandise Merchandise) {
		db = DatabaseManager.getInstance().openDatabase();
		db.beginTransaction();

		try {
			MerchandiseDao.update(Merchandise);

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
	public List<Merchandise> getRecommendCategorys() {
		db = DatabaseManager.getInstance().openDatabase();
		List<Merchandise> merchandises = null;
		try {
			merchandises = MerchandiseDao.findRecommendCategorys();
		} catch (Exception e) {
			e.printStackTrace();

		}finally{
			DatabaseManager.getInstance().closeDatabase();
		}
		return merchandises;
	}
}
