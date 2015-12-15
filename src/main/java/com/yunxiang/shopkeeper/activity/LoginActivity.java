package com.yunxiang.shopkeeper.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yunxiang.shopkeeper.R;
import com.yunxiang.shopkeeper.TApplication;
import com.yunxiang.shopkeeper.biz.UserBiz;
import com.yunxiang.shopkeeper.model.User;
import com.yunxiang.shopkeeper.utils.AndroidUtils;
import com.yunxiang.shopkeeper.utils.Const;

public class LoginActivity extends Activity implements View.OnClickListener{

    private EditText edtUsername,edtPassword;
    private TextView txFindPsw,txLogin,txRegist;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        addView();
        addListener();

    }

    private void addListener() {
        txFindPsw.setOnClickListener(this);
        txLogin.setOnClickListener(this);
        txRegist.setOnClickListener(this);
    }

    private void addView() {
        edtUsername = (EditText)findViewById(R.id.edt_username);
        edtPassword= (EditText)findViewById(R.id.edt_password);
        txFindPsw = (TextView)findViewById(R.id.txt_forget);
        txLogin = (TextView)findViewById(R.id.txt_login);
        txRegist = (TextView)findViewById(R.id.txt_regist);
        intent = new Intent();
    }


    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.txt_forget:
                intent.setClass(this, FindPswActivity.class);
                startActivity(intent);
                break;
            case R.id.txt_login:
                String phoneString = edtUsername.getText().toString();
                String pswString = edtPassword.getText().toString();
                if(phoneString.length() == 0){
                    AndroidUtils.show(this, "用户名不能为空");
                    break;
                }

                if(pswString.length() == 0){
                    AndroidUtils.show(this,"密码不能为空");
                }

                Handler.Callback callback = new LoginHandlerCallback();
                Handler loginHandler = new Handler(callback);
                User user = new User();
                user.setUserName(phoneString);
                user.setPassword(pswString);
                UserBiz userBiz = UserBiz.getInstance();
                userBiz.login(user, loginHandler);

                break;
            case R.id.txt_regist:
                intent.setClass(this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }


    /**
     * desc:接收登录请求的消息
     * auther:jiely
     * create at 2015/9/9 15:56
     */
    private class LoginHandlerCallback implements Handler.Callback{

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case Const.MSG_SUCCESS:
                   // Shop shop = ShopService.getInstanse().getShop();
                    if(TApplication.shop == null){
                        intent.setClass(LoginActivity.this, ShopInfoActivity.class);
                    }else {
                        intent.setClass(LoginActivity.this, ShopActivity.class);
                    }

                    startActivity(intent);
                    finish();
                    break;
                case Const.MSG_FAILURE:
                    if(msg.obj != null){
                        AndroidUtils.show(LoginActivity.this,msg.obj.toString());
                    }else {
                        AndroidUtils.show(LoginActivity.this,"网络错误");
                    }

                    break;
            }
            return true;
        }
    }
}
