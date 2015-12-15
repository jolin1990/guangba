package com.yunxiang.shopkeeper.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunxiang.shopkeeper.R;
import com.yunxiang.shopkeeper.TApplication;
import com.yunxiang.shopkeeper.base.BaseTitle;
import com.yunxiang.shopkeeper.utils.Const;

public class ActiveAddActivity extends Activity implements View.OnClickListener{

    private EditText edtName,edtStartDate,edtStopDate,edtInfo;
    private TextView txtSend;
    private ImageView imgAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_add);
        BaseTitle.getInstance().setTitle(this, "新建活动");
        addView();
        addListener();
    }

    private void addListener() {
        txtSend.setOnClickListener(this);
        imgAdd.setOnClickListener(this);
    }

    private void addView() {
        edtName = (EditText)findViewById(R.id.edt_name);
        edtStartDate = (EditText)findViewById(R.id.edt_date1);
        edtStopDate = (EditText)findViewById(R.id.edt_date2);
        edtInfo = (EditText)findViewById(R.id.edt_info);

        txtSend = (TextView)findViewById(R.id.txt_send);
        imgAdd = (ImageView)findViewById(R.id.img_add_pic);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_send:
                if(TApplication.versionType == Const.TEST_VERTION){
                    jumpView();
                }
                break;
            case R.id.img_add_pic:
                break;
        }
    }

    private void jumpView(){
        Intent intent = new Intent();
        intent.setClass(this,ActiveActivity.class);
        startActivity(intent);
        finish();
    }
}
