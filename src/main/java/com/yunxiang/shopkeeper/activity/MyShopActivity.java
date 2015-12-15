package com.yunxiang.shopkeeper.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunxiang.shopkeeper.R;
import com.yunxiang.shopkeeper.TApplication;
import com.yunxiang.shopkeeper.base.BaseTitle;
import com.yunxiang.shopkeeper.biz.UserBiz;
import com.yunxiang.shopkeeper.service.impl.UserService;
import com.yunxiang.shopkeeper.utils.AndroidUtils;
import com.yunxiang.shopkeeper.utils.Const;

public class MyShopActivity extends Activity implements View.OnClickListener{
    private RelativeLayout rl1,rl2,rl3,rl4,rl5,rl6,rl7;
    private TextView txtExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_shop);

        if(TApplication.versionType == Const.TEST_VERTION){
            BaseTitle.getInstance().setTitle(this, "渝香辣婆婆");
        }

        addView();
        addListener();
    }

    private void addListener() {
        rl1.setOnClickListener(this);
        rl2.setOnClickListener(this);
        rl3.setOnClickListener(this);
        rl4.setOnClickListener(this);
        rl5.setOnClickListener(this);
        rl6.setOnClickListener(this);
        rl7.setOnClickListener(this);
        txtExit.setOnClickListener(this);
    }

    private void addView() {
        rl1 = (RelativeLayout)findViewById(R.id.rl_1);
        rl2 = (RelativeLayout)findViewById(R.id.rl_2);
        rl3 = (RelativeLayout)findViewById(R.id.rl_3);
        rl4 = (RelativeLayout)findViewById(R.id.rl_4);
        rl5 = (RelativeLayout)findViewById(R.id.rl_5);
        rl6 = (RelativeLayout)findViewById(R.id.rl_6);
        rl7 = (RelativeLayout)findViewById(R.id.rl_7);
        txtExit = (TextView)findViewById(R.id.txt_exit);
    }



    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.rl_1:
                TApplication.map.put(Const.MAP_KEY_ACITIVITY,"MyShopActivity");
                intent.setClass(this, ShopInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_2://留言板
                intent.setClass(this, MessageLeaveActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_3://评价管理
                intent.setClass(this, EvaluateActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_4://提醒设置
                intent.setClass(this,RemindSetActivity.class);
                startActivity(intent);

                break;
            case R.id.rl_5://修改密码
                intent.setClass(this,ModifyPswActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_6://意见反馈
                intent.setClass(this,FeedBackActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_7://关于我们
                intent.setClass(this,AboutUsActivity.class);
                startActivity(intent);
                break;
            case R.id.txt_exit://退出登录
                if(TApplication.versionType == Const.TEST_VERTION){
                    //Intent intent = new Intent();
                    intent.setClass(MyShopActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Handler.Callback callback = new LoginOutCallback();
                    Handler handler = new Handler(callback);
                    UserBiz.getInstance().selectLoginOut(handler);
                }

                break;
        }
    }

    class LoginOutCallback implements Handler.Callback{
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case Const.MSG_SUCCESS:
                    UserService.getInstanse().clear();
                    TApplication.user = null;
                    Intent intent = new Intent();
                    intent.setClass(MyShopActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case Const.MSG_FAILURE:
                    AndroidUtils.show(MyShopActivity.this,msg.obj.toString());
                    break;
            }
            return true;
        }
    }
}
