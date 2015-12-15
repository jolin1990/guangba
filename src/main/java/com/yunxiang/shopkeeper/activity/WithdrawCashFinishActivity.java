package com.yunxiang.shopkeeper.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunxiang.shopkeeper.R;


public class WithdrawCashFinishActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_cash_finish);
        setTitleTxt();
        Button bt_finish= (Button) findViewById(R.id.bt_commit);
        bt_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(WithdrawCashFinishActivity.this,MyCountActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setTitleTxt() {
        TextView tvTitle= (TextView) findViewById(R.id.tv_title);
        ImageView ivBack= (ImageView) findViewById(R.id.iv_back);
        ivBack.setVisibility(View.GONE);
        tvTitle.setText("已提交申请");
    }
}
