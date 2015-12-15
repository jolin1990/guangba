package com.yunxiang.shopkeeper.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.yunxiang.shopkeeper.R;
import com.yunxiang.shopkeeper.base.BaseTitle;

public class ModifyLoginpswActivity extends Activity implements View.OnClickListener{

    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_loginpsw);
        addTitle();

        btnSubmit = (Button)findViewById(R.id.btn_submit);

        addListenner();

    }

    private void addListenner() {
        btnSubmit.setOnClickListener(this);
    }

    private void addTitle() {
        BaseTitle.getInstance().setTitle(this, "修改登录密码");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_submit:
                Intent intent = new Intent();
                intent.setClass(this,MyShopActivity.class);
                finish();
                break;
        }
    }
}
