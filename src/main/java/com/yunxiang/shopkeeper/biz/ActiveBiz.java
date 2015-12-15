package com.yunxiang.shopkeeper.biz;

import android.os.Handler;
import android.os.Message;

import com.yunxiang.shopkeeper.TApplication;
import com.yunxiang.shopkeeper.model.Active;
import com.yunxiang.shopkeeper.utils.Const;
import com.yunxiang.shopkeeper.utils.HttpUtils;
import com.yunxiang.shopkeeper.utils.JsonUtils;


/**
 * @desc: Created by jiely on 2015/9/9.
 */
public class ActiveBiz {
    private static ActiveBiz instance;

    public static ActiveBiz getInstance(){
        if(instance == null){
            instance = new ActiveBiz();
        }
        return instance;
    }

    //活动列表
    public  void selectAll(final Handler handler){
        new Thread(){
            @Override
            public void run() {
                String url=Const.URL_GET_SHOP_ACTIVITY+TApplication.shop.getShopId();
                String result = HttpUtils.doGetAsyn(url);
                boolean status = JsonUtils.getStatus(result);
                if(status){
                    TApplication.activeList = JsonUtils.getActives(result);
                    Message message = handler.obtainMessage();
                    message.what = Const.MSG_SUCCESS;
                    handler.sendMessage(message);
                }
            }
        }.start();
    }

    //活动的详情
    public void selectById(final long activityId,final Handler handler){
        new Thread(){
            @Override
            public void run() {
                String url=Const.URL_SHOP_ACTIVITY_DETAIL+activityId;
                String result = HttpUtils.doGetAsyn(url);
                boolean status = JsonUtils.getStatus(result);
                if(status){
                    Active active = JsonUtils.getActive(result);
                    Message message = handler.obtainMessage();
                    message.what = Const.MSG_SUCCESS;
                    handler.sendMessage(message);
                }

            }
        }.start();
    }


}
