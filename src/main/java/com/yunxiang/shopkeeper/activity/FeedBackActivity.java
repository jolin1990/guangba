package com.yunxiang.shopkeeper.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.yunxiang.shopkeeper.R;
import com.yunxiang.shopkeeper.TApplication;
import com.yunxiang.shopkeeper.base.BaseTitle;
import com.yunxiang.shopkeeper.utils.Const;

/**
 * desc:用户反馈
 * auther:jiely
 * create at 2015/12/1 16:28
 */
public class FeedBackActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);

        if(TApplication.versionType == Const.TEST_VERTION){

        }
        addTitle();
        Button btnSubmit = (Button)findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(FeedBackActivity.this,MyShopActivity.class);
                finish();
            }
        });
    }

    private void addTitle() {
        BaseTitle.getInstance().setTitle(this, "意见反馈");
    }


}
