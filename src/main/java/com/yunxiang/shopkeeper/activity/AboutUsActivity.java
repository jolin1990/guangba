package com.yunxiang.shopkeeper.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.yunxiang.shopkeeper.R;
import com.yunxiang.shopkeeper.TApplication;
import com.yunxiang.shopkeeper.base.BaseTitle;
import com.yunxiang.shopkeeper.utils.AndroidUtils;
import com.yunxiang.shopkeeper.utils.Const;

public class AboutUsActivity extends Activity {


    private TextView txtVersion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        if(TApplication.versionType == Const.TEST_VERTION){

        }

        txtVersion = (TextView)findViewById(R.id.txt_version);
        String version = AndroidUtils.getThisAppVersion();
        txtVersion.setText(version);

        addTitle();
    }

    private void addTitle() {
        BaseTitle.getInstance().setTitle(this, "关于我们");
    }
}
