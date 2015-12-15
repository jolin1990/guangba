package com.yunxiang.shopkeeper.biz;

import android.os.Handler;
import android.os.Message;
import android.util.LongSparseArray;

import com.yunxiang.shopkeeper.TApplication;
import com.yunxiang.shopkeeper.model.Merchandise;
import com.yunxiang.shopkeeper.service.impl.MerchandiseService;
import com.yunxiang.shopkeeper.utils.Const;
import com.yunxiang.shopkeeper.utils.HttpUtils;
import com.yunxiang.shopkeeper.utils.JsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


/**
 * @desc: Created by jiely on 2015/9/9.
 */
public class MerchandiseBiz {
    private static MerchandiseBiz instance;

    public static MerchandiseBiz getInstance(){
        if(instance == null){
            instance = new MerchandiseBiz();
        }
        return instance;
    }

    //获取商品的列表
    public void selectMerchandises(final long categoryId ,final Handler handler){
        new Thread(){
            @Override
            public void run() {
                String url=Const.URL_SHOP_ITEM_LIST;
                StringBuilder builder=new StringBuilder();
                builder.append(url).append("shopId=").append(TApplication.shop.getShopId()).append("&categoryId=").append(categoryId);
                url=builder.toString();
                String result = HttpUtils.doGetAsyn(url);
                boolean status = JsonUtils.getStatus(result);
                Message message = handler.obtainMessage();
                if(status){
                    List<Merchandise> merchandises = JsonUtils.getMerchandises(result);
                    if(TApplication.merchandiseArray == null){
                        TApplication.merchandiseArray = new LongSparseArray<List<Merchandise>>();
                    }
                    TApplication.merchandiseArray.append(categoryId,merchandises);
                    message.what=Const.MSG_OBTAIN_MERCHANDISE_SUCCESS;
                }
                handler.sendMessage(message);

            }
        }.start();
    }

    //添加商品图片
    public void insertMerchandiseImage(final Handler handler,final String path){
        new Thread(){
            @Override
            public void run() {
                String url=Const.URL_ADD_OR_UPDATA_SHOPITEM;
                try {
                    String result = HttpUtils.doJsonPost(url, path);
                    boolean status = JsonUtils.getStatus(result);
                    String desc = JsonUtils.getDescription(result);
                    Message message = handler.obtainMessage();
                    message.obj = desc;
                    if (status){
                        message.what = Const.MSG_ADD_MERCHANDISE_SUCCESS;
                        Merchandise merchandise1 = JsonUtils.getMerchandise(result);
                        MerchandiseService.getInstanse().addMerchandise(merchandise1);
                    }else {
                        message.what = Const.MSG_FAILURE;
                    }
                    handler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //添加商品
    public void insertMerchandise(final Merchandise merchandise,final Handler handler){
         new Thread(){
             @Override
             public void run() {
                 String url=Const.URL_ADD_OR_UPDATA_SHOPITEM;
                 try {
                     String jsonString = getMerchandiseJson(merchandise);
                     String result = HttpUtils.doJsonPost(url, jsonString);
                     boolean status = JsonUtils.getStatus(result);
                     String desc = JsonUtils.getDescription(result);
                     Message message = handler.obtainMessage();
                     message.obj = desc;
                     if (status){
                         message.what = Const.MSG_ADD_MERCHANDISE_SUCCESS;
                         Merchandise merchandise1 = JsonUtils.getMerchandise(result);
                         MerchandiseService.getInstanse().addMerchandise(merchandise1);
                     }else {
                         message.what = Const.MSG_FAILURE;
                     }
                     handler.sendMessage(message);
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
             }
         }.start();
    }

    private String getMerchandiseJson(Merchandise merchandise){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("type", "itemSmallImage");
            jsonObject.put("categoryId",String.valueOf(merchandise.getCategoryId()));
            jsonObject.put("name", merchandise.getName());
            jsonObject.put("price", String.valueOf(merchandise.getPrice()));
            jsonObject.put("description", merchandise.getDescription());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    /**
     * desc:  更新商品推荐信息
     * auther:jiely
     * create at 2015/11/19 16:14
     */
    public void updateRecommend(final Merchandise merchandise,final Handler handler){
        new Thread(){
            @Override
            public void run() {
                String url=Const.URL_UPDATE_SHOPITME_ISSEAL;
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("itemId", String.valueOf(merchandise.getId()));
                    String type = merchandise.isRecommend()?"1":"0";
                    jsonObject.put("feature", type);
                    String result = HttpUtils.doJsonPut(url, jsonObject.toString());
                    boolean status = JsonUtils.getStatus(result);
                    Message message =handler.obtainMessage();
                    String desc = JsonUtils.getDescription(result);
                    message.obj = desc;
                    if(status){
                        MerchandiseService.getInstanse().modify(merchandise);
                        message.what=Const.MSG_RECOMMEND_SUCCESS;

                    }else {
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
     * desc: 更新商品下架信息
     * auther:jiely
     * create at 2015/11/19 16:15
     */
    public void updateSoldOut(final Merchandise merchandise,final Handler handler){
        new Thread(){
            @Override
            public void run() {
                String url=Const.URL_UPDATE_SHOPITME_ISSEAL;
                String result;
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("itemId", String.valueOf(merchandise.getId()));
                    int type = merchandise.isSoldOut()?0:1;
                    jsonObject.put("stockQuantity", String.valueOf(type));
                    result = HttpUtils.doJsonPut(url, jsonObject.toString());
                    boolean status = JsonUtils.getStatus(result);
                    Message message =handler.obtainMessage();
                    String desc = JsonUtils.getDescription(result);
                    message.obj = desc;
                    if(status){
                        message.what=Const.MSG_SOLDOUT_SUCCESS;
                    }else {
                        message.what=Const.MSG_FAILURE;
                    }
                    handler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }
}
