package com.yunxiang.shopkeeper.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.yunxiang.shopkeeper.R;
import com.yunxiang.shopkeeper.TApplication;
import com.yunxiang.shopkeeper.base.BaseTitle;
import com.yunxiang.shopkeeper.utils.Const;

/**
 * desc: 扫码收款
 * auther:jiely
 * create at 2015/11/25 15:25
 */
public class ScanCodeActivity extends Activity {

    private Button btnCertain;
    private EditText edtMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code);
        if(TApplication.versionType == Const.TEST_VERTION){

        }
        addView();
        addTitle();
    }

    private void addView() {
        edtMoney = (EditText)findViewById(R.id.edt_money);
        btnCertain = (Button)findViewById(R.id.btn_certain);

        btnCertain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void addTitle() {
        BaseTitle.getInstance().setTitle(this, "设置金额");
    }

}
