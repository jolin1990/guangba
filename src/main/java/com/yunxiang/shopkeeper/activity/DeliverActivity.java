package com.yunxiang.shopkeeper.activity;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.yunxiang.shopkeeper.R;
import com.yunxiang.shopkeeper.TApplication;
import com.yunxiang.shopkeeper.base.BaseTitle;
import com.yunxiang.shopkeeper.biz.ShopBiz;
import com.yunxiang.shopkeeper.model.Deliver;
import com.yunxiang.shopkeeper.utils.AndroidUtils;
import com.yunxiang.shopkeeper.utils.Const;

public class DeliverActivity extends Activity implements View.OnClickListener{

    private EditText edtSendingFee,edtPackFee,edtDeliverFee;
    private Button btnInvoice,btnCOD,btnCalling,btnSave;
    private Deliver deliver;
    private boolean isModify = false;//本页面是否修改

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliver);

        if(TApplication.versionType == Const.TEST_VERTION){

        }
        addTitle();
        addDate();
        addView();
        addListener();
    }

    private void addDate() {
        deliver = TApplication.shop.getDeliver();
        if(deliver != null){
            edtSendingFee.setText(deliver.getSendingFee());
            edtPackFee.setText(deliver.getPockFee());
            edtDeliverFee.setText(deliver.getDeliverFee());
            if(deliver.isCallingMan()){
                btnCalling.setBackgroundResource(R.drawable.icon_switch_s);
            }else{
                btnCalling.setBackgroundResource(R.drawable.icon_switch_n);
            }
            if(deliver.isCOD()){
                btnCOD.setBackgroundResource(R.drawable.icon_switch_s);
            }else{
                btnCOD.setBackgroundResource(R.drawable.icon_switch_n);
            }
            if(deliver.isInvoice()){
                btnInvoice.setBackgroundResource(R.drawable.icon_switch_s);
            }else{
                btnInvoice.setBackgroundResource(R.drawable.icon_switch_n);
            }

        }else {
            isModify = false;
        }
    }

    private void addView() {
        edtSendingFee = (EditText)findViewById(R.id.edt_sending_fee);
        edtPackFee = (EditText)findViewById(R.id.edt_pack_fee);
        edtDeliverFee = (EditText)findViewById(R.id.edt_deliver_fee);
        btnInvoice = (Button)findViewById(R.id.btn_is_invoice);
        btnCOD = (Button)findViewById(R.id.btn_is_cod);
        btnCalling = (Button)findViewById(R.id.btn_is_calliing_man);
        btnSave = (Button)findViewById(R.id.btn_save);
    }

    private void addTitle() {
        BaseTitle.getInstance().setTitle(this, "配送");
    }

    private void addListener(){
        btnInvoice.setOnClickListener(this);
        btnCOD.setOnClickListener(this);
        btnCalling.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Drawable switchYes = getResources().getDrawable(R.drawable.icon_switch_s);
        //Drawable switchNo = getResources().getDrawable(R.drawable.icon_switch_n);
        Drawable background;
        switch (v.getId()){
            case R.id.btn_is_invoice://是否开具发票
                background = btnInvoice.getBackground();
                if(background.equals(switchYes)){
                    btnInvoice.setBackgroundResource(R.drawable.icon_switch_n);
                }else {
                    btnInvoice.setBackgroundResource(R.drawable.icon_switch_s);
                }
                break;
            case R.id.btn_is_cod://是否货到付款
                background = btnCOD.getBackground();
                if(background.equals(switchYes)){
                    btnCOD.setBackgroundResource(R.drawable.icon_switch_n);
                }else {
                    btnCOD.setBackgroundResource(R.drawable.icon_switch_s);
                }
                break;
            case R.id.btn_is_calliing_man://是否呼叫送货员
                background = btnCalling.getBackground();
                if(background.equals(switchYes)){
                    btnCalling.setBackgroundResource(R.drawable.icon_switch_n);
                }else {
                    btnCalling.setBackgroundResource(R.drawable.icon_switch_s);
                }
                break;
            case R.id.btn_save://保存
                String sendingFee = edtSendingFee.getText().toString();
                String pockFee = edtPackFee.getText().toString();
                String deliverFee = edtDeliverFee.getText().toString();
                if(sendingFee.length() == 0){
                    AndroidUtils.show(this,"请输入金额");
                    break;
                }
                if(pockFee.length() == 0){
                    AndroidUtils.show(this,"请输入金额");
                    break;
                }
                if(deliverFee.length() == 0){
                    AndroidUtils.show(this,"请输入金额");
                    break;
                }
                Deliver newDeliver = new Deliver();
                newDeliver.setSendingFee(sendingFee);
                newDeliver.setPockFee(pockFee);
                newDeliver.setDeliverFee(deliverFee);
                background = btnInvoice.getBackground();
                if(background.equals(switchYes)){
                    newDeliver.setIsInvoice(true);
                }else {
                    newDeliver.setIsInvoice(false);
                }

                background = btnCOD.getBackground();
                if(background.equals(switchYes)){
                    newDeliver.setIsCOD(true);
                }else {
                    newDeliver.setIsCOD(false);
                }

                background = btnCalling.getBackground();
                if(background.equals(switchYes)){
                    newDeliver.setIsCallingMan(true);
                }else {
                    newDeliver.setIsCallingMan(false);
                }

                if(!newDeliver.equals(deliver)){
                    Handler.Callback callback = new DeliverCallback();
                    Handler handler = new Handler(callback);
                    ShopBiz.getInstance().insertDeliver(deliver, handler);
                }else {
                    AndroidUtils.show(this,"数据未做修改");
                    onBackPressed();
                }
                break;
        }
    }

    class DeliverCallback implements Handler.Callback{
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what){
                case Const.MSG_SUCCESS:
                    AndroidUtils.show(DeliverActivity.this,message.obj.toString());
                    onBackPressed();
                    break;
                case Const.MSG_FAILURE:
                    AndroidUtils.show(DeliverActivity.this,message.obj.toString());
                    onBackPressed();
                    break;
            }
            return true;
        }
    }
}
