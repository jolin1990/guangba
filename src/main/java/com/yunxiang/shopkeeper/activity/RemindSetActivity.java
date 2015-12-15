package com.yunxiang.shopkeeper.activity;

import android.app.Activity;
import android.os.Bundle;

import com.yunxiang.shopkeeper.R;
import com.yunxiang.shopkeeper.TApplication;
import com.yunxiang.shopkeeper.base.BaseTitle;
import com.yunxiang.shopkeeper.utils.Const;

public class RemindSetActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind_set);
        if(TApplication.versionType == Const.TEST_VERTION){

        }
        addTitle();
    }

    private void addTitle() {
        BaseTitle.getInstance().setTitle(this, "提醒设置");
    }
}
