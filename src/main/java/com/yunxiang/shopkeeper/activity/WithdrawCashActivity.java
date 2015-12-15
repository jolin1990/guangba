package com.yunxiang.shopkeeper.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunxiang.shopkeeper.R;


public class WithdrawCashActivity extends Activity implements View.OnClickListener {

    private RelativeLayout rlBack;
    private CheckBox ali,bank;
    private EditText etMoney;
    private Button btCommit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_cash);
        setCashTitle();
        initView();
        setListener();
    }

    private void setListener() {
        rlBack.setOnClickListener(this);
        btCommit.setOnClickListener(this);
        ali.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    bank.setChecked(false);
                }else{
                    bank.setChecked(true);
                }
            }
        });
        bank.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    ali.setChecked(false);
                }else{
                    ali.setChecked(true);
                }
            }
        });
    }

    private void initView() {
        rlBack= (RelativeLayout) findViewById(R.id.rl_back);
        etMoney= (EditText) findViewById(R.id.et_money);
        ali= (CheckBox) findViewById(R.id.cb_ali);
        bank= (CheckBox) findViewById(R.id.cb_bank);
        btCommit= (Button) findViewById(R.id.bt_commit);
    }

    private void setCashTitle() {
        TextView title= (TextView) findViewById(R.id.tv_title);
        title.setText("提现");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.bt_commit:
                Intent intent=null;
                if (ali.isChecked()){
                    intent=new Intent(this,WithdrawCashAliActivity.class);
                }else if(bank.isChecked()){
                    intent=new Intent(this,WithdrawCashBankActivity.class);
                }
                startActivity(intent);
                break;
        }
    }
}
