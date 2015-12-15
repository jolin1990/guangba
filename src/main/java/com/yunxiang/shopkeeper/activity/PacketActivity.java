package com.yunxiang.shopkeeper.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yunxiang.shopkeeper.R;
import com.yunxiang.shopkeeper.TApplication;
import com.yunxiang.shopkeeper.base.BaseTitle;
import com.yunxiang.shopkeeper.utils.Const;

public class PacketActivity extends Activity implements View.OnClickListener{
    private TextView txtRecord,txtTroop,txtPersonal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packet);
        BaseTitle.getInstance().setTitle(this, "发红包");

        addView();
        addListener();
    }

    private void addListener() {
        txtRecord.setOnClickListener(this);
        txtTroop.setOnClickListener(this);
        txtPersonal.setOnClickListener(this);
    }

    private void addView() {
        txtRecord = (TextView)findViewById(R.id.txt_record);
        txtTroop = (TextView)findViewById(R.id.txt_troop);
        txtPersonal = (TextView)findViewById(R.id.txt_personal);
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.txt_record://记录
                intent.setClass(PacketActivity.this, PacketRecordActivity.class);
                startActivity(intent);
                break;
            case R.id.txt_troop://群红包
                TApplication.map.put(Const.MAP_KEY_ACITIVITY,Const.MAP_VAL_CROWD_POCKET);
                intent.setClass(PacketActivity.this,PacketEditActivity.class);
                startActivity(intent);
                break;
            case R.id.txt_personal://个人红包
                intent.setClass(PacketActivity.this, CustomerListActivity.class);
                startActivity(intent);
                TApplication.map.put(Const.MAP_KEY_ACITIVITY, Const.MAP_VAL_PERSONAL_POCKET);
        }
    }
}
