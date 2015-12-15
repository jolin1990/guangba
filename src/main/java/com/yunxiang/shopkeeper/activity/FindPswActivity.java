package com.yunxiang.shopkeeper.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yunxiang.shopkeeper.R;
import com.yunxiang.shopkeeper.base.BaseTitle;
import com.yunxiang.shopkeeper.biz.UserBiz;
import com.yunxiang.shopkeeper.model.User;
import com.yunxiang.shopkeeper.utils.AndroidUtils;
import com.yunxiang.shopkeeper.utils.Const;

import java.util.Timer;
import java.util.TimerTask;

public class FindPswActivity extends Activity implements View.OnClickListener{

    private EditText edtUsername,edtPassword,edtVerify,edtConfirmPsw;
    private TextView txtVerify;
    private Button btnSubmit;
    private boolean isVerify = false;
    private int recLen = 60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_psw);

        addTitle();
        addView();
        addListener();
    }

    private void addListener() {
        txtVerify.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    private void addView() {
        edtUsername = (EditText)findViewById(R.id.edt_username);
        edtPassword = (EditText)findViewById(R.id.edt_password);
        edtVerify = (EditText)findViewById(R.id.edt_verify_key);
        edtConfirmPsw = (EditText)findViewById(R.id.edt_confirm_psw);
        txtVerify = (TextView)findViewById(R.id.txt_verify_key);
        btnSubmit = (Button)findViewById(R.id.btn_submit);
    }

    private void addTitle() {
        BaseTitle.getInstance().setTitle(this, "忘记密码");
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        String phone;
        Handler handler;
        Handler.Callback callback;
        UserBiz userBiz = UserBiz.getInstance();
        switch (v.getId()){
            case R.id.txt_verify_key:
                if (!isVerify) {
                    phone = edtUsername.getText().toString();
                    if (phone.length() == 0) {
                        AndroidUtils.show(this, "请输入手机号");
                        break;
                    }
                    isVerify = true;
                    final Timer timer = new Timer();
                    TimerTask task = new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {      // UI thread
                                @Override
                                public void run() {
                                    recLen--;
                                    txtVerify.setText("" + recLen);
                                    if (recLen < 0) {
                                        timer.cancel();
                                        txtVerify.setText("获取验证码");
                                        isVerify = false;
                                    }
                                }
                            });
                        }
                    };

                    timer.schedule(task, 1000, 1000);
                    callback = new CaptchaHandlerCallback();
                    handler = new Handler(callback);
                    userBiz.visitCaptcha(phone, handler);
                }
                break;
            case R.id.btn_submit:

                phone = edtUsername.getText().toString();
                if (phone.length() == 0) {
                    AndroidUtils.show(this, "请输入手机号");
                    break;
                }
                String captcha = edtVerify.getText().toString();
                if (captcha.length() == 0) {
                    AndroidUtils.show(this, "请输入验证码");
                    break;
                }
                String psw = edtPassword.getText().toString();
                if (psw.length() == 0) {
                    AndroidUtils.show(this, "请输入密码");
                    break;
                }

                User user = new User();
                user.setUserName(phone);
                user.setPassword(psw);

                callback = new RegisterHandlerCallback();
                handler = new Handler(callback);
                userBiz.register(user, captcha, handler);

                break;
        }
    }

    private class CaptchaHandlerCallback implements Handler.Callback {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what){
                case Const.MSG_SUCCESS:
                    break;
                case Const.MSG_FAILURE:
                    if(message.obj != null){
                        AndroidUtils.show(FindPswActivity.this, message.obj.toString());
                    }
                    break;
            }
            return true;
        }
    }

    private class RegisterHandlerCallback implements Handler.Callback {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what){
                case Const.MSG_SUCCESS:
                    Intent intent = new Intent();
                    intent.setClass(FindPswActivity.this,AccountManageActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case Const.MSG_FAILURE:
                    if(message.obj != null){
                        AndroidUtils.show(FindPswActivity.this, message.obj.toString());
                    }
                    break;
            }

            return false;
        }
    }
}
