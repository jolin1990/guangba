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


public class WithdrawCashBankActivity extends Activity implements View.OnClickListener,RechargeDialog.RechargeDialogCallback {
    private Button btCommit;
    private RelativeLayout rlBack;
    private EditText etName,etAccount;
  private RechargeDialog rechargeDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_cash_bank);
        setTitleTxt();
        initView();
    }

    private void initView() {
        btCommit= (Button) findViewById(R.id.bt_commit);
        rlBack= (RelativeLayout) findViewById(R.id.rl_back);
        etName= (EditText) findViewById(R.id.et_name);
        etAccount= (EditText) findViewById(R.id.et_account);
        btCommit.setOnClickListener(this);
        rlBack.setOnClickListener(this);
    }

    private void setTitleTxt() {
        TextView tvTitle= (TextView) findViewById(R.id.tv_title);
        tvTitle.setText("提现");
    }

    @Override
    public void onClick(View v) {
        if (v.getId()== R.id.bt_commit){
            rechargeDialog=new RechargeDialog(this, R.style.MyDialogStyle,"******334455332398573","￥55555.55","提现到银行卡");
            rechargeDialog.show();

        }else if(v.getId()== R.id.rl_back){
            finish();
        }
    }

    @Override
    public void rechargeDialogCallbackClose() {
        rechargeDialog.dismiss();
    }

    @Override
    public void rechargeDialogCallbackButton() {
        Intent intent=new Intent(this,WithdrawCashActivity.class);
        startActivity(intent);
    }
}
