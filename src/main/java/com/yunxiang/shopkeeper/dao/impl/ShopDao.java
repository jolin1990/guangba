package com.yunxiang.shopkeeper.dao.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yunxiang.shopkeeper.dao.IShopDao;
import com.yunxiang.shopkeeper.model.Shop;
import com.yunxiang.shopkeeper.utils.StringUtils;


/**
 * @desc: Created by jiely on 2015/9/1.
 */
public class ShopDao implements IShopDao {

    private static final String SQL_INSERT = "Insert into shop(shopId,shopName,phone,address,typeId,imgUrl,bannerIds,describe) values(?,?,?,?,?,?,?,?)";
    private static final String SQL_SELECT_ONE =       "select shopId,shopName,phone,address,typeId,imgUrl,bannerIds,describe from shop";


    private SQLiteDatabase db;

    public ShopDao(SQLiteDatabase db) {
        super();
        this.db = db;
    }

    @Override
    public void clear() throws Exception {
        String sql = "delete from shop";
        db.execSQL(sql);
    }

    @Override
    public void add(Shop shop) throws Exception {
        String bannerIds = StringUtils.longsToString(shop.getBannerIds());
        db.execSQL(SQL_INSERT, new Object[] {shop.getShopId(),shop.getShopName(),shop.getShopPhone(),shop.getAddress()
                ,shop.getTypeId(),shop.getSmallImageURL(),bannerIds,shop.getDescribe() } );
    }

    @Override
    public void update(Shop shop) throws Exception {

        ContentValues cv = new ContentValues();
        cv.put("shopId", shop.getShopId());
        cv.put("shopName", shop.getShopName());
        cv.put("phone", shop.getShopPhone());
        cv.put("address", shop.getAddress());
        cv.put("typeId", shop.getTypeId());
        cv.put("imgUrl", shop.getSmallImageURL());
        long[] ids = shop.getBannerIds();
        String str = StringUtils.longsToString(ids);
        cv.put("bannerIds",str);
        cv.put("describe",shop.getDescribe());
        String[] args = {String.valueOf(shop.getShopId())};
        db.update("shop", cv, "shopId=?", args);
    }

    @Override
    public Shop find() throws Exception {
        Shop shop = null;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(SQL_SELECT_ONE, new String[]{});
            shop = null;

            if (cursor.moveToFirst()) {
                shop = new Shop();
                shop.setShopId(cursor.getInt(cursor.getColumnIndex("shopId")));
                shop.setShopName(cursor.getString(cursor.getColumnIndex("shopName")));
                shop.setShopPhone(cursor.getString(cursor.getColumnIndex("phone")));
                shop.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                shop.setTypeId(cursor.getInt(cursor.getColumnIndex("typeId")));
                shop.setSmallImageURL(cursor.getString(cursor.getColumnIndex("imgUrl")));
                String str = cursor.getString(cursor.getColumnIndex("bannerIds"));
                long[] ids = StringUtils.splitCommaLong(str);
                shop.setBannerIds(ids);
                shop.setDescribe(cursor.getString(cursor.getColumnIndex("describe")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(cursor != null){
                cursor.close();
            }
        }
        return shop;
    }
}
