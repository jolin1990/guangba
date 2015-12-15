package com.yunxiang.shopkeeper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.yunxiang.shopkeeper.activity.LoginActivity;
import com.yunxiang.shopkeeper.activity.ShopActivity;
import com.yunxiang.shopkeeper.activity.ShopInfoActivity;
import com.yunxiang.shopkeeper.biz.UserBiz;
import com.yunxiang.shopkeeper.service.impl.ShopService;
import com.yunxiang.shopkeeper.service.impl.UserService;
import com.yunxiang.shopkeeper.utils.Const;
import com.yunxiang.shopkeeper.utils.DebugUtils;
import com.yunxiang.shopkeeper.utils.HttpUtils;

public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(TApplication.versionType == Const.TEST_VERTION){
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, ShopActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        TApplication.user = UserService.getInstanse().getUser();
        TApplication.shop = ShopService.getInstanse().getShop();
        if(TApplication.user != null){
            DebugUtils.d("Tapplication", "user=" + TApplication.user.toString());
        }

        if(!HttpUtils.isNetworkAvailable()){//无网络时
            setJump();
        }else {
            //判断服务器端是否缓存了登录状态，如果没有就登录
            Intent intent = new Intent();
            if (TApplication.user == null) {
                intent.setClass(this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                Handler.Callback callback = new CheckLoginCallback();
                Handler handler = new Handler(callback);
                UserBiz.getInstance().selectIsLogin(handler);
            }
        }
    }

    class CheckLoginCallback implements Handler.Callback{
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case Const.MSG_SUCCESS://已登录
                case Const.MSG_FAILURE://未登录
                    Intent intent = new Intent();
                    if(TApplication.shop == null){
                        intent.setClass(MainActivity.this, ShopInfoActivity.class);
                    }else {
                        intent.setClass(MainActivity.this, ShopActivity.class);
                    }
                    startActivity(intent);
                    finish();
                    break;
            }
            return true;
        }
    }

    private void setJump(){
        Intent intent = new Intent();
        if (TApplication.user != null) {
            intent.setClass(this, ShopActivity.class);
            if(TApplication.shop == null){
                intent.setClass(this, ShopInfoActivity.class);
            }
        } else {
            intent.setClass(this, LoginActivity.class);
        }
        startActivity(intent);
        finish();
    }

}
