package com.yunxiang.shopkeeper.base;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunxiang.shopkeeper.R;


/**
 * desc: 界面标题栏
 * Created by jiely on 2015/8/31.
 */
public class BaseTitle {
    private Activity activity;
    private View view;

    public BaseTitle() {}

    public static BaseTitle getInstance(){
        return new BaseTitle();
    }

    public void setTitle(Activity activity, String middle){
        this.activity = activity;
        init(middle);
    }

    public void setTitle(View view, String middle){
        this.view = view;
        init(middle);
    }

    public void init(String middle){
        ImageView imgLeft;
        TextView txtMiddle;
        if(view != null){
            imgLeft = (ImageView) view.findViewById(R.id.in_title)
                    .findViewById(R.id.img_left);
            txtMiddle = (TextView) view.findViewById(R.id.in_title)
                    .findViewById(R.id.txt_middle);
        }else{
            imgLeft = (ImageView) activity.findViewById(R.id.in_title)
                    .findViewById(R.id.img_left);
            txtMiddle = (TextView) activity.findViewById(R.id.in_title)
                    .findViewById(R.id.txt_middle);
        }

        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onBackPressed();
            }
        });
        txtMiddle.setText(middle);
    }


}
