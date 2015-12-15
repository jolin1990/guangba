package com.yunxiang.shopkeeper.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunxiang.shopkeeper.R;
import com.yunxiang.shopkeeper.view.RechargeDialog;


public class WithdrawCashAliActivity extends Activity implements View.OnClickListener ,RechargeDialog.RechargeDialogCallback {
        private RelativeLayout rlBack;
    private Button btCommit;
    private EditText etAli;
    private RechargeDialog rechargeDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_cash_ali);
        setTitleTxt();
        initView();
    }

    private void initView() {
        rlBack= (RelativeLayout) findViewById(R.id.rl_back);
        btCommit= (Button) findViewById(R.id.bt_commit);
        etAli= (EditText) findViewById(R.id.et_ali);

        rlBack.setOnClickListener(this);
        btCommit.setOnClickListener(this);
    }

    private void setTitleTxt() {
        TextView tvTitle= (TextView) findViewById(R.id.tv_title);
        tvTitle.setText("提现");
    }


    @Override
    public void onClick(View v) {
        if (v.getId()== R.id.rl_back){
            finish();
        }else{

            rechargeDialog =new RechargeDialog(this, R.style.MyDialogStyle,"*****4450","99999999,00","提现到支付宝");
            rechargeDialog.show();


        }
    }

    @Override
    public void rechargeDialogCallbackClose() {
        rechargeDialog.dismiss();
    }

    @Override
    public void rechargeDialogCallbackButton() {
        Intent intent=new Intent(this,WithdrawCashFinishActivity.class);
        startActivity(intent);
    }
}
