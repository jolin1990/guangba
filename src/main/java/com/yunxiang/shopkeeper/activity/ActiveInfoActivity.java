package com.yunxiang.shopkeeper.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunxiang.shopkeeper.R;
import com.yunxiang.shopkeeper.TApplication;
import com.yunxiang.shopkeeper.base.BaseTitle;
import com.yunxiang.shopkeeper.biz.ImageBiz;
import com.yunxiang.shopkeeper.model.Active;
import com.yunxiang.shopkeeper.utils.Const;
import com.yunxiang.shopkeeper.utils.DataUtils;


/**
 * desc:活动页面
 * auther:jiely
 * create at 2015/10/14 15:38
 */
public class ActiveInfoActivity extends Activity implements View.OnClickListener {

    private ImageView  ivBack,ivActivity;
    private TextView txtName,txtStartTime,txtEndTime,TxtContent;
    private Active active;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_info);

        if(TApplication.versionType == Const.TEST_VERTION){

        }
        addView();
        addListener();
        addData();
        setView();
    }

    private void addData() {
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);
        active = TApplication.activeList.get(position);

    }

    private void addListener() {
        ivBack.setOnClickListener(this);
    }

    private void addView() {
        ivBack= (ImageView) findViewById(R.id.img_left);
        ivActivity= (ImageView) findViewById(R.id.img_head);
        txtName= (TextView) findViewById(R.id.txt_name);
        txtStartTime= (TextView) findViewById(R.id.txt_date);
        TxtContent= (TextView) findViewById(R.id.txt_info);
    }

    private void setView(){
        if(TApplication.versionType == Const.TEST_VERTION){
            BaseTitle.getInstance().setTitle(this, "中秋钜惠促销活动");
        }else {
            if(active != null){
                String name = active.getActiveName();
                BaseTitle.getInstance().setTitle(this, name);

                String decription = active.getDescription();
                String url = active.getImgUrl();
                String startDate= active.getStartDate();
                String stopDate= active.getStopDate();

                ImageBiz imageBiz=new ImageBiz(false);
                imageBiz.execute(url, ivActivity);
                txtName.setText(name);
                txtEndTime.setText(DataUtils.getAdate(stopDate));
                txtStartTime.setText(DataUtils.getAdate(startDate));
                TxtContent.setText(decription);
            }
        }

    }

    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.img_left){
            finish();
        }
    }

}
