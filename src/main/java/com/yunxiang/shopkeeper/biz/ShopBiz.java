package com.yunxiang.shopkeeper.biz;

import android.os.Handler;
import android.os.Message;

import com.yunxiang.shopkeeper.TApplication;
import com.yunxiang.shopkeeper.model.Deliver;
import com.yunxiang.shopkeeper.model.Shop;
import com.yunxiang.shopkeeper.service.impl.ShopService;
import com.yunxiang.shopkeeper.utils.Const;
import com.yunxiang.shopkeeper.utils.HttpUtils;
import com.yunxiang.shopkeeper.utils.JsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * desc: Created by jiely on 2015/9/9.
 */
public class ShopBiz {
    private static ShopBiz instance;

    public static ShopBiz getInstance() {
        if (instance == null) {
            instance = new ShopBiz();
        }
        return instance;
    }

    public void insertShop(final Shop shop, final Handler handler){
        new Thread(){
            @Override
            public void run() {
                String url = Const.URL_REGISTER_SHOP;
                try {
                    JSONObject json = getShopJsonObj(shop);
                    String jsonString = json.toString();
                    String jsonContent = HttpUtils.doJsonPost(url, jsonString);
                    boolean status = JsonUtils.getStatus(jsonContent);
                    Message msg = handler.obtainMessage();
                    String desc = JsonUtils.getDescription(jsonContent);
                    msg.obj = desc;
                    if(status){
                        msg.what = Const.MSG_INSERT_SHOP_SUCCESS;
                        long shopId = JsonUtils.getShopId(jsonContent);
                        shop.setShopId(shopId);
                        ShopBiz.refreshShop(shop);
                    }else {
                        msg.what = Const.MSG_FAILURE;
                    }
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //修改商铺的信息
    public void updateShop(final Shop shop, final Handler handler) {
        new Thread() {
            @Override
            public void run() {
                String url = Const.URL_UPDATA_SHOP_INFO;
                try {
                    JSONObject json = getShopJsonObj(shop);
                    String jsonString = json.toString();
                    String result = HttpUtils.doJsonPut(url, jsonString);
                    boolean status = JsonUtils.getStatus(result);
                    String desc = JsonUtils.getDescription(result);
                    Message message = Message.obtain();
                    message.obj = desc;
                    if (status) {
                        message.what = Const.MSG_UPDATE_SHOP_SUCCESS;
                        refreshShop(shop);
                    } else {
                        message.what = Const.MSG_FAILURE;
                    }
                    handler.sendMessage(message);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void insertShopImage(final String path, final Handler handler){
        new Thread(){
            @Override
            public void run() {
                String url = Const.URL_REGISTER_SHOP;
                try {
                    String jsonContent = HttpUtils.doPostFile(url, path);
                    boolean status = JsonUtils.getStatus(jsonContent);
                    Message msg = handler.obtainMessage();
                    String desc = JsonUtils.getDescription(jsonContent);
                    msg.obj = desc;
                    if(status){
                        msg.what = Const.MSG_SUCCESS;
                        long imgId = JsonUtils.getImageId(jsonContent);
                        TApplication.shop.setSmallImageURL(String.valueOf(imgId));
                    }else {
                        msg.what = Const.MSG_FAILURE;
                    }
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //修改商铺的信息
    public void updateShopImage(final String path, final Handler handler) {
        new Thread() {
            @Override
            public void run() {
                String url = Const.URL_UPDATA_SHOP_INFO;
                try {
                    String result = HttpUtils.doJsonPut(url, path);
                    boolean status = JsonUtils.getStatus(result);
                    String desc = JsonUtils.getDescription(result);
                    Message message = Message.obtain();
                    message.obj = desc;
                    if (status) {
                        message.what = Const.MSG_SUCCESS;
                        long imgId = JsonUtils.getImageId(result);
                        TApplication.shop.setSmallImageURL(String.valueOf(imgId));
                        refreshShop(TApplication.shop);
                    } else {
                        message.what = Const.MSG_FAILURE;
                    }
                    handler.sendMessage(message);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //提交配送信息
    public void insertDeliver(final Deliver deliver, final Handler handler) {
        new Thread() {
            @Override
            public void run() {
                JSONObject jsonObj = getDeliverJsonObj(deliver);
                String url = Const.URL_UPDATA_SHOP_INFO;
                try {
                    String result = HttpUtils.doJsonPost(url, jsonObj.toString());
                    boolean status = JsonUtils.getStatus(result);
                    String desc = JsonUtils.getDescription(result);
                    Message message = Message.obtain();
                    message.obj = desc;
                    if (status) {
                        message.what = Const.MSG_INSERT_DELIVER_SUCCESS;
                        TApplication.shop.setDeliver(deliver);
                    } else {
                        message.what = Const.MSG_FAILURE;
                    }
                    handler.sendMessage(message);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //提交配送信息
    public void updateDeliver(final Deliver deliver, final Handler handler) {
        new Thread() {
            @Override
            public void run() {
                JSONObject jsonObj = getDeliverJsonObj(deliver);
                String url = Const.URL_UPDATA_SHOP_INFO;
                try {
                    String result = HttpUtils.doJsonPut(url, jsonObj.toString());
                    boolean status = JsonUtils.getStatus(result);
                    String desc = JsonUtils.getDescription(result);
                    Message message = Message.obtain();
                    message.obj = desc;
                    if (status) {
                        message.what = Const.MSG_UPDATE_DELIVER_SUCCESS;
                        TApplication.shop.setDeliver(deliver);
                    } else {
                        message.what = Const.MSG_FAILURE;
                    }
                    handler.sendMessage(message);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * desc: 把Deliver转换为Json类
     * auther:jiely
     * create at 2015/12/1 13:23
     */
    private static JSONObject getDeliverJsonObj(Deliver deliver){
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("deliverFee", deliver.getDeliverFee());
            jsonObj.put("pockFee", deliver.getPockFee());
            jsonObj.put("sendingFee", deliver.getSendingFee());
            Integer type = deliver.isInvoice()?1:0;
            jsonObj.put("isInvoice", type.toString());
            type = deliver.isCOD()?1:0;
            jsonObj.put("isCOD", type.toString());
            type = deliver.isCallingMan()?1:0;
            jsonObj.put("isCallingMan", type.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObj;
    }

    /**
     * desc: 把Shop转换为Json类
     * auther:jiely
     * create at 2015/12/1 13:23
     */
    private static JSONObject getShopJsonObj(Shop shop){
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("shopName",shop.getShopName());
            jsonObj.put("shopPhone",shop.getShopPhone());
            jsonObj.put("street", shop.getAddress());
            jsonObj.put("typeId",String.valueOf(shop.getTypeId()));
            jsonObj.put("description", shop.getDescribe());
            jsonObj.put("masterName", shop.getMasterName());
            jsonObj.put("masterPhone", shop.getMasterPhone());
            jsonObj.put("openingHours", shop.getOpeningHours());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObj;
    }



    //往数据库保存shop
    public static void refreshShop(Shop shop) {
        TApplication.shop = shop;
        ShopService.getInstanse().refresh(shop);
    }

}
