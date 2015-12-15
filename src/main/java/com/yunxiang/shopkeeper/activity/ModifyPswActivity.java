package com.yunxiang.shopkeeper.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.yunxiang.shopkeeper.R;
import com.yunxiang.shopkeeper.base.BaseTitle;

public class ModifyPswActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_psw);

        addTitle();

        RelativeLayout rl1 = (RelativeLayout)findViewById(R.id.rl_1);
        rl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ModifyPswActivity.this, ModifyLoginpswActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout rl2 = (RelativeLayout)findViewById(R.id.rl_2);
        rl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ModifyPswActivity.this,ModifyLoginpswActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addTitle() {
        BaseTitle.getInstance().setTitle(this, "修改密码");
    }
}
