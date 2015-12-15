package com.yunxiang.shopkeeper.activity;

import android.app.Activity;
import android.os.Bundle;
import com.yunxiang.shopkeeper.R;
import com.yunxiang.shopkeeper.TApplication;
import com.yunxiang.shopkeeper.base.BaseTitle;
import com.yunxiang.shopkeeper.utils.Const;

public class MyBankcardActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bankcard);

        if(TApplication.versionType == Const.TEST_VERTION){

        }

        addTitle();
    }

    private void addTitle() {
        BaseTitle.getInstance().setTitle(this, "我的银行卡");
    }

}
