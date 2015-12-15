package com.yunxiang.shopkeeper.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.yunxiang.shopkeeper.R;
import com.yunxiang.shopkeeper.TApplication;
import com.yunxiang.shopkeeper.base.BaseTitle;
import com.yunxiang.shopkeeper.utils.Const;

public class CouponActivity extends Activity implements View.OnClickListener{

    private TextView txtRecord,txtTroop,txtPersonal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);
        if(TApplication.versionType == Const.TEST_VERTION){

        }
        BaseTitle.getInstance().setTitle(this, "发优惠券");

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
                intent.setClass(CouponActivity.this,CouponListActivity.class);
                startActivity(intent);
                break;
            case R.id.txt_troop://抵用券
                setRefuseDialog(Const.MAP_VAL_VOUCHERS);//"抵用券"
                break;
            case R.id.txt_personal://折扣券
                setRefuseDialog(Const.MAP_VAL_DISCOUNT);
                break;
        }
    }

    /**
     * desc: 设置选择发送对象的对话框
     * auther:jiely
     * create at 2015/11/27 14:28
     */
    private void setRefuseDialog(final String name){
        final Dialog dialog = new Dialog(CouponActivity.this,R.style.dialog);
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        dialogWindow.setContentView(R.layout.dialog_order_refuse);
        dialogWindow.setAttributes(lp);
        dialog.show();
        final TextView txtReason1 = (TextView)dialogWindow.findViewById(R.id.txt_reason1);
        final TextView txtReason2 = (TextView)dialogWindow.findViewById(R.id.txt_reason2);
        final TextView txtReason3 = (TextView)dialogWindow.findViewById(R.id.txt_reason3);
        txtReason1.setText("给所有顾客");
        txtReason2.setText("仅回馈老顾客");
        txtReason3.setVisibility(View.GONE);
        TextView txtCancel = (TextView)dialogWindow.findViewById(R.id.txt_cancel);
        final Intent intent = new Intent();
        TApplication.map.put(Const.MAP_KEY_ACITIVITY, name);
        txtReason1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(CouponActivity.this, CouponEditActivity.class);
                TApplication.map.put(Const.MAP_KEY_NAME, name);
                TApplication.map.put(Const.MAP_KEY_SLECT, Const.MAP_VAL_SELECT_ALL);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        txtReason2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(CouponActivity.this, CustomerListActivity.class);
                TApplication.map.put(Const.MAP_KEY_NAME, name);
                TApplication.map.put(Const.MAP_KEY_SLECT, Const.MAP_VAL_SELECTED);
                startActivity(intent);
                dialog.dismiss();
            }
        });

        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
