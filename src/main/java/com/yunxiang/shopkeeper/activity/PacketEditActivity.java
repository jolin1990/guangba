package com.yunxiang.shopkeeper.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yunxiang.shopkeeper.R;
import com.yunxiang.shopkeeper.TApplication;
import com.yunxiang.shopkeeper.base.BaseTitle;
import com.yunxiang.shopkeeper.base.DateSet;
import com.yunxiang.shopkeeper.biz.PacketBiz;
import com.yunxiang.shopkeeper.model.PacketBatch;
import com.yunxiang.shopkeeper.utils.AndroidUtils;
import com.yunxiang.shopkeeper.utils.Const;
import com.yunxiang.shopkeeper.utils.DebugUtils;

public class PacketEditActivity extends Activity implements View.OnClickListener {

    private EditText edtPackageNum, edtMoney, edtMsg, edtName;
    private TextView txtSwitch,txtLeft,txtAmount,txtMoney,txtPin;
    private Button btnSend;
    private boolean isNormal = true;//是否是普通红包
    private int watchNum = 0;  //是否是可发送状态
    private TextView txtDate1, txtDate2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packet_edit);

        if(TApplication.versionType == Const.TEST_VERTION){

        }
        addTitle();
        addView();
        setView();
        addListener();
    }

    private void addTitle() {
        //群红包 or 个人红包
        String title = TApplication.map.get(Const.MAP_KEY_ACITIVITY);
        BaseTitle.getInstance().setTitle(this, title);
    }

    private void addListener() {
        txtSwitch.setOnClickListener(this);
        btnSend.setOnClickListener(this);
        txtDate1.setOnClickListener(this);
        txtDate2.setOnClickListener(this);
    }

    private void addView() {
        edtPackageNum = (EditText) findViewById(R.id.edt_num);

        edtMoney = (EditText) findViewById(R.id.edt_money);

        edtMsg = (EditText) findViewById(R.id.edt_msg);
        edtName = (EditText) findViewById(R.id.edt_name);

        txtSwitch = (TextView) findViewById(R.id.txt_switch);
        txtLeft = (TextView)findViewById(R.id.txt_left);
        txtAmount = (TextView)findViewById(R.id.txt_amount);
        txtMoney = (TextView)findViewById(R.id.txt_money);
        txtPin = (TextView)findViewById(R.id.txt_pin);
        txtDate1 = (TextView) findViewById(R.id.txt_date1);
        txtDate2 = (TextView) findViewById(R.id.txt_date2);

        btnSend = (Button) findViewById(R.id.btn_send);
    }

    /**
     * desc: 设置红包金额的显示
     * auther:jiely
     * create at 2015/12/7 15:11
     */
    private void setView(){
        edtPackageNum.addTextChangedListener(new MyWatcher());
        edtMoney.addTextChangedListener(new MyWatcher());
        edtName.addTextChangedListener(new MyWatcher());
        txtDate1.addTextChangedListener(new MyWatcher());
        txtDate2.addTextChangedListener(new MyWatcher());

        btnSend.setEnabled(false);
        btnSend.setTextColor(getResources().getColor(R.color.gray));
        btnSend.setBackgroundColor(getResources().getColor(R.color.light_blue));

        if(isNormal){
            txtLeft.setText("当前为普通红包");
            txtMoney.setText("单个金额");
            txtPin.setText("");
            edtMoney.setHint("红包单个金额");
        }else {
            txtLeft.setText("当前为拼手气红包");
            txtMoney.setText("总金额");
            txtPin.setText("拼");
            edtMoney.setHint("红包总金额");
        }
    }

    @Override
    public void onClick(View v) {
        DateSet dateSet;
        switch (v.getId()) {
            case R.id.txt_date1:
                dateSet = new DateSet(getFragmentManager(),txtDate1);
                dateSet.show();
                break;
            case R.id.txt_date2:
                dateSet = new DateSet(getFragmentManager(),txtDate2);
                dateSet.show();
                break;
            case R.id.txt_switch:
                isNormal = !isNormal;
                setView();
                break;
            case R.id.btn_send:
                if(TApplication.versionType == Const.TEST_VERTION){
                    Intent intent = new Intent();
                    intent.setClass(PacketEditActivity.this, PacketRecordActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                }

                String name = edtName.getText().toString();
                String num = edtPackageNum.getText().toString();
                String money = edtMoney.getText().toString();
                String desc = edtMsg.getText().toString();
                int type;
                if (isNormal) {
                    type = Const.PACKET_COMMON;
                } else {
                    type = Const.PACKET_FIGHT_LUCK;
                }
                PacketBatch packetBatch = new PacketBatch(type,name, desc);
                packetBatch.setType(type);
                packetBatch.setAmountMoney(money);
                packetBatch.setTotalCount(num);
                String startDate = txtDate1.getText().toString();
                String stopDate = txtDate2.getText().toString();
                packetBatch.setStartDate(startDate);
                packetBatch.setStopDate(stopDate);

                //调用支付接口----陈久玲

                //支付接口调用成功后调用改方法
                Handler handler = new Handler(new PacketHandlerCallback());
                PacketBiz.getInstance().insertPacket(packetBatch, handler);
                break;
        }
    }


    class PacketHandlerCallback implements Handler.Callback {

        @Override
        public boolean handleMessage(Message message) {
            String desc = message.obj.toString();
            switch (message.what) {
                case Const.MSG_SUCCESS:
                    Intent intent = new Intent();
                    intent.setClass(PacketEditActivity.this, PacketRecordActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case Const.MSG_FAILURE:
                    break;
            }
            AndroidUtils.show(PacketEditActivity.this, desc);
            return true;
        }
    }

    /**
     * desc: 检测EditText，如果EditText有内容，按钮变亮，否则按钮变灰
     * auther:jiely
     * create at 2015/12/7 14:59
     */
    public class MyWatcher implements TextWatcher {
        private boolean check1 = false, check2 = true;
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if (s.length() > 0) {
                check1 = true;
            } else if(s.length() == 0){
                check2 = true;							//开门
                check1 = false;
            }
            DebugUtils.d("PacketEditActivity","check1="+check1+",check2="+check2);
            if(check1){
                if(check2){
                    watchNum++;
                    check2 = false;
                    DebugUtils.d("PacketEditActivity","watchNum="+watchNum);//关门
                    if(watchNum == 5){
                        btnSend.setEnabled(true);
                        if(isNormal){
                            String title = TApplication.map.get(Const.MAP_KEY_ACITIVITY);
                            if(title.equals("群红包")){
                                txtAmount.setText(edtMoney.getText().toString());
                            }else {
                                int money = Integer.parseInt(edtMoney.getText().toString());
                                int num = TApplication.selectedCustomers==null ? 0 :TApplication.selectedCustomers.size();
                                txtAmount.setText(String.valueOf("￥"+money*num));
                            }
                        }
                        btnSend.setTextColor(getResources().getColor(R.color.white));
                        btnSend.setBackgroundColor(getResources().getColor(R.color.sky_blue));
                    }
                }
            }else{
                watchNum--;
                if(watchNum < 5){
                    btnSend.setEnabled(false);
                    btnSend.setTextColor(getResources().getColor(R.color.gray));
                    btnSend.setBackgroundColor(getResources().getColor(R.color.light_blue));
                }
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //DebugUtils.d("PacketEditActivity","beforeTextChanged");
        }

        @Override
        public void afterTextChanged(Editable s) {
            //DebugUtils.d("PacketEditActivity","afterTextChanged");
        }
    }

}
