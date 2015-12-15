package com.yunxiang.shopkeeper.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yunxiang.shopkeeper.R;
import com.yunxiang.shopkeeper.base.BaseTitle;

public class MyCountActivity extends Activity implements View.OnClickListener {
    private TextView tvRecharge,tvWithdraw;
    private TextView tvTotalMoney;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_count);
        BaseTitle.getInstance().setTitle(this, "我的账户");
        initView();
        setListener();
    }
    private void setListener() {
        tvRecharge.setOnClickListener(this);
        tvWithdraw.setOnClickListener(this);
    }

    private void initView() {
        tvRecharge= (TextView) findViewById(R.id.txt_put_money);
        tvWithdraw= (TextView) findViewById(R.id.txt_obtain_money);
        tvTotalMoney= (TextView) findViewById(R.id.txt_money);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_put_money:
                intent=new Intent(this,RechargeActivity.class);
                startActivity(intent);
                break;
            case R.id.txt_obtain_money:
                intent=new Intent(this,WithdrawCashActivity.class);
                startActivity(intent);
                break;
        }
    }
}
