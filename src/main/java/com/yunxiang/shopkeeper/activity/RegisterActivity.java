package com.yunxiang.shopkeeper.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunxiang.shopkeeper.R;
import com.yunxiang.shopkeeper.TApplication;
import com.yunxiang.shopkeeper.base.BaseTitle;
import com.yunxiang.shopkeeper.biz.UserBiz;
import com.yunxiang.shopkeeper.model.User;
import com.yunxiang.shopkeeper.utils.AndroidUtils;
import com.yunxiang.shopkeeper.utils.Const;

public class RegisterActivity extends Activity implements View.OnClickListener {

    private EditText edtUsername, edtPassword, edtVerify, edtConfirmPsw;
    private TextView txtOne, txtTwo, txtThree, txtGetverify, txtUpverify, txtComplete;
    private RelativeLayout rl1, rl2, rl3;
    private String phone;
    private String verifyKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        if(TApplication.versionType == Const.TEST_VERTION){

        }
        addTitle();
        addView();
        disPlayUI(1);
        addListener();
    }

    private void addTitle() {
        BaseTitle.getInstance().setTitle(this, "注册");
    }

    private void addListener() {
        txtGetverify.setOnClickListener(this);
        txtUpverify.setOnClickListener(this);
        txtComplete.setOnClickListener(this);
    }

    private void addView() {
        edtUsername = (EditText) findViewById(R.id.edt_username);
        edtPassword = (EditText) findViewById(R.id.edt_password);
        edtConfirmPsw = (EditText) findViewById(R.id.edt_confirm_psw);
        edtVerify = (EditText) findViewById(R.id.edt_verify_key);

        txtOne = (TextView) findViewById(R.id.txt_one);
        txtTwo = (TextView) findViewById(R.id.txt_two);
        txtThree = (TextView) findViewById(R.id.txt_three);
        txtGetverify = (TextView) findViewById(R.id.txt_get_verify);
        txtUpverify = (TextView) findViewById(R.id.txt_up_verify);
        txtComplete = (TextView) findViewById(R.id.txt_complete);

        rl1 = (RelativeLayout) findViewById(R.id.rl_1);
        rl2 = (RelativeLayout) findViewById(R.id.rl_2);
        rl3 = (RelativeLayout) findViewById(R.id.rl_3);
    }

    private void disPlayUI(int type) {
        switch (type) {
            case 1://获取验证码
                rl1.setVisibility(View.VISIBLE);
                rl2.setVisibility(View.GONE);
                rl3.setVisibility(View.GONE);
                txtOne.setTextColor(getResources().getColor(R.color.sky_blue));
                txtTwo.setTextColor(getResources().getColor(R.color.gray));
                txtThree.setTextColor(getResources().getColor(R.color.gray));
                break;
            case 2://输入验证码
                rl1.setVisibility(View.GONE);
                rl2.setVisibility(View.VISIBLE);
                rl3.setVisibility(View.GONE);
                txtOne.setTextColor(getResources().getColor(R.color.gray));
                txtTwo.setTextColor(getResources().getColor(R.color.sky_blue));
                txtThree.setTextColor(getResources().getColor(R.color.gray));
                TextView txtPhone = (TextView)findViewById(R.id.txt_text);
                txtPhone.setText("我们已将验证码发送至"+phone);
                break;
            case 3://完成
                rl1.setVisibility(View.GONE);
                rl2.setVisibility(View.GONE);
                rl3.setVisibility(View.VISIBLE);
                txtOne.setTextColor(getResources().getColor(R.color.gray));
                txtTwo.setTextColor(getResources().getColor(R.color.gray));
                txtThree.setTextColor(getResources().getColor(R.color.sky_blue));
                break;
        }
    }

    @Override
    public void onClick(View v) {

        Handler handler;
        Handler.Callback callback = new RegisterHandlerCallback();
        UserBiz userBiz = UserBiz.getInstance();
        switch (v.getId()) {
            case R.id.txt_get_verify://获取验证码
                //disPlayUI(1);
                phone = edtUsername.getText().toString();
                if (phone.length() == 0) {
                    AndroidUtils.show(this, "请输入手机号");
                    break;
                }
                handler = new Handler(callback);
                userBiz.visitCaptcha(phone, handler);
                disPlayUI(2);
                break;
            case R.id.txt_up_verify://输入验证码

                verifyKey = edtVerify.getText().toString();
                if (verifyKey.length() == 0) {
                    AndroidUtils.show(this, "请输入验证码");
                    break;
                }
                disPlayUI(3);
                break;
            case R.id.txt_complete://完成

                String psw = edtPassword.getText().toString();
                String confirmPsw = edtConfirmPsw.getText().toString();
                if (psw.length() == 0 || confirmPsw.length() == 0) {
                    AndroidUtils.show(this, "请输入密码");
                    break;
                }
                if(psw.equals(confirmPsw)){
                    User user = new User();
                    user.setUserName(phone);
                    user.setPassword(psw);
                    callback = new RegisterHandlerCallback();
                    handler = new Handler(callback);
                    userBiz.register(user, verifyKey, handler);
                }else {
                    AndroidUtils.show(this, "密码不一致");
                }
                break;
        }
    }



    private class RegisterHandlerCallback implements Handler.Callback {
        @Override
        public boolean handleMessage(Message msg) {

            switch (msg.what) {
                case Const.MSG_FAILURE:
                    String desc = msg.obj.toString();
                    AndroidUtils.show(RegisterActivity.this, desc);
                    break;
                case Const.MSG_SUCCESS:
                    Intent intent = new Intent();
                    intent.putExtra("action","add");
                    intent.setClass(RegisterActivity.this, ShopInfoActivity.class);
                    startActivity(intent);
                    break;
            }
            return true;
        }
    }
}
