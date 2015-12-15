package com.yunxiang.shopkeeper.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunxiang.shopkeeper.R;
import com.yunxiang.shopkeeper.base.BaseTitle;

public class MessageSendActivity extends Activity implements View.OnClickListener{

    private TextView txtSend,txtInfo;
    private ImageView imgAddPic;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_send);

        BaseTitle.getInstance().setTitle(this, "发消息");
        addView();
        addListener();
        
    }

    private void addListener() {
        txtSend.setOnClickListener(this);
        imgAddPic.setOnClickListener(this);
    }

    private void addView() {
        txtSend = (TextView)findViewById(R.id.txt_send);
        txtInfo = (TextView)findViewById(R.id.txt_info);
        imgAddPic = (ImageView)findViewById(R.id.img_add_pic);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_send:
                Intent intent = new Intent();
                intent.setClass(this,CustomerManageActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.img_add_pic:
                break;
        }
    }
}
