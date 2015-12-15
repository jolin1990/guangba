package com.yunxiang.shopkeeper.biz;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.yunxiang.shopkeeper.TApplication;
import com.yunxiang.shopkeeper.model.Shop;
import com.yunxiang.shopkeeper.model.User;
import com.yunxiang.shopkeeper.service.impl.UserService;
import com.yunxiang.shopkeeper.utils.Const;
import com.yunxiang.shopkeeper.utils.HttpUtils;
import com.yunxiang.shopkeeper.utils.JsonUtils;


/**
 * @desc: Created by jiely on 2015/9/9.
 */
public class UserBiz {

    private static UserBiz instance;
    public static UserBiz getInstance(){
        if(instance == null){
            instance = new UserBiz();
        }
        return instance;
    }

    private UserBiz() {

    }

    public void login(final User user, final Handler handler){
        new Thread(){
            @Override
            public void run() {
                Looper.prepare();
                setLogin(user, handler);
            }
        }.start();
    }

    public void register(final User user, final String captcha,final Handler handler){
        new Thread(){
            @Override
            public void run() {
                setRegister(Const.URL_LOGIN, user, captcha, handler);
            }
        }.start();
    }

    public void changePsw(final User user, final String captcha,final Handler handler){
        new Thread(){
            @Override
            public void run() {
                // Looper.prepare();
                setRegister(Const.URL_CHANGE_PSW, user, captcha, handler);
            }
        }.start();
    }

    private void setLogin(User user, Handler handler){
        StringBuilder sb = new StringBuilder();
        sb.append(Const.URL_LOGIN);
        sb.append(user.getUserName());
        sb.append("&password=");
        sb.append(user.getPassword());
        String urlPath = sb.toString();
        String jsonContent = HttpUtils.doGetLogin(urlPath);
        boolean status = JsonUtils.getStatus(jsonContent);
        String desc = JsonUtils.getDescription(jsonContent);

        if(status){
            User newUser = JsonUtils.getUser(jsonContent);
            newUser.setPassword(user.getPassword());
            UserService.getInstanse().refresh(newUser);
            TApplication.user = newUser;

            //获取shop
            Shop shop=JsonUtils.getShopByUser(jsonContent);
            if(shop != null){
                ShopBiz.refreshShop(shop);
            }
        }

        if(handler != null){
            Message msg = handler.obtainMessage();
            msg.obj = desc;
            msg.what = status ? Const.MSG_SUCCESS : Const.MSG_FAILURE;
            handler.sendMessage(msg);
        }
    }

    private void setRegister(String url,User user, String captcha,Handler handler){
        Message msg = handler.obtainMessage();
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        sb.append(user.getUserName());
        sb.append("&password=");
        sb.append(user.getPassword());
        sb.append("&captcha=");
        sb.append(captcha);
        String urlPath = sb.toString();
        String jsonContent;
        try {
            jsonContent = HttpUtils.doPost(urlPath);
            boolean status = JsonUtils.getStatus(jsonContent);
            String desc = JsonUtils.getDescription(jsonContent);
            msg.obj = desc;
            if( status){
                setLogin(user, null);
                msg.what = Const.MSG_SUCCESS;
            }else {
                msg.what = Const.MSG_FAILURE;
            }
            handler.sendMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
            msg.what = Const.MSG_FAILURE;
            handler.sendMessage(msg);
        }
    }

    public void visitCaptcha(final String username, final Handler handler){
        new Thread(){
            @Override
            public void run() {
                String url = Const.URL_GET_VERIFY_CODE+username;
                String jsonContent = HttpUtils.doGetLogin(url);
                boolean status = JsonUtils.getStatus(jsonContent);
                Message msg = handler.obtainMessage();
                String desc = JsonUtils.getDescription(jsonContent);
                msg.obj = desc;
                if(!status){
                    msg.what = Const.MSG_FAILURE;
                }
                handler.sendMessage(msg);
            }
        }.start();
    }

    /**
     * desc: 检查是否登录
     * auther:jiely
     * create at 2015/12/2 10:27
     */
    public void selectIsLogin(final Handler handler){
        new Thread(){
            @Override
            public void run() {
                String url = Const.URL_IS_LOGIN;
                String jsonContent = HttpUtils.doGetAsyn(url);
                boolean isLogin = JsonUtils.getIsLogin(jsonContent);
                Message message = handler.obtainMessage();
                if(isLogin){
                    message.what = Const.MSG_SUCCESS;
                }else {
                    setLogin(TApplication.user,null);
                    message.what = Const.MSG_FAILURE;
                }
                handler.sendMessage(message);
            }
        }.start();
    }

    /**
     * desc:退出登录
     * auther:jiely
     * create at 2015/12/2 10:27
     */
    public void selectLoginOut(final Handler handler){
        new Thread(){
            @Override
            public void run() {
                String url = Const.URL_LOGIN_OUT;
                String jsonContent = HttpUtils.doGetAsyn(url);
                boolean isLogin = JsonUtils.getIsLogin(jsonContent);
                String desc = JsonUtils.getDescription(jsonContent);
                Message message = handler.obtainMessage();
                message.obj = desc;
                if(isLogin){
                    message.what = Const.MSG_SUCCESS;
                }else {
                    message.what = Const.MSG_FAILURE;
                }

                handler.sendMessage(message);
            }
        }.start();
    }
}
