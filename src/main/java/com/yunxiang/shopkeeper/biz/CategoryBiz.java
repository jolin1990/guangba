package com.yunxiang.shopkeeper.biz;

import android.os.Handler;
import android.os.Message;

import com.yunxiang.shopkeeper.TApplication;
import com.yunxiang.shopkeeper.model.Category;
import com.yunxiang.shopkeeper.utils.Const;
import com.yunxiang.shopkeeper.utils.HttpUtils;
import com.yunxiang.shopkeeper.utils.JsonUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * @desc: Created by jiely on 2015/9/9.
 */
public class CategoryBiz {
    private static CategoryBiz instance;

    public static CategoryBiz getInstance(){
        if(instance == null){
            instance = new CategoryBiz();
        }
        return instance;
    }

    //获取商品服务的商品类别的列表
    public void selectAll(final Handler handler){
        new Thread(){
            @Override
            public void run() {
                String url=Const.URL_GET_SHOPITEM_CLASS_LIST;
                String result = HttpUtils.doGetAsyn(url);

                boolean status = JsonUtils.getStatus(result);
                Message message = handler.obtainMessage();
                if(status){
                    TApplication.categories = JsonUtils.getCategories(result);
                    message.what = Const.MSG_SUCCESS;
                }
                handler.sendMessage(message);
            }
        }.start();
    }

    //获取商品服务的商品类别的列表
    public void deleteCategory(final Category category,final Handler handler){
        new Thread(){
            @Override
            public void run() {
               long  id = category.getCategoryId();
                String url=Const.URL_DELETE_CATEGORY+id;
                try {
                    String result = HttpUtils.doDelete(url);
                    boolean status = JsonUtils.getStatus(result);
                    Message message = handler.obtainMessage();
                    String desc = JsonUtils.getDescription(result);
                    message.obj = desc;
                    if(status){
                        //CategoryService.getInstanse().delete(category);
                        TApplication.categories.remove(category);
                        message.what=Const.MSG_DELETE_CATEGORY_SUCCESS;
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

    //增加商品类别
    public void insertCategory(final Category category,final Handler handler){
        new Thread(){
            @Override
            public void run() {
                String url=Const.URL_ADD_CLASS;
                try {
                    Map<String, String> map = new HashMap<String, String>();

                    map.put("name", category.getName());
                    url = url+"?name="+category.getName();
                    String result = HttpUtils.doPost(url);
                    boolean status = JsonUtils.getStatus(result);
                    Message message = handler.obtainMessage();
                    String desc = JsonUtils.getDescription(result);
                    message.obj = desc;
                    if(status){
                        message.what=Const.MSG_ADD_CATEGORY_SUCCESS;
                        TApplication.categories.add(category);
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

    //修改商品的类别
    public void updateCategory(final Category category,final Handler handler){
        new Thread(){
            @Override
            public void run() {
                String url=Const.URL_UPDATE_CLASS;
                try {
                    Map<String, String> map = new HashMap<String, String>();
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("categoryId", String.valueOf(category.getCategoryId()));
                    jsonObject.put("name", category.getName());
                    String result = HttpUtils.doJsonPut(url, jsonObject.toString());
                    boolean status = JsonUtils.getStatus(result);
                    Message message = handler.obtainMessage();
                    String desc = JsonUtils.getDescription(result);
                    message.obj = desc;
                    if(status){
                        message.what=Const.MSG_UPDATE_CATEGORY_SUCCESS;
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
