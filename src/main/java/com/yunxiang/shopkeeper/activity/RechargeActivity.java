package com.yunxiang.shopkeeper.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.pingplusplus.android.PaymentActivity;
import com.yunxiang.shopkeeper.R;
import com.yunxiang.shopkeeper.TApplication;
import com.yunxiang.shopkeeper.biz.PayBiz;
import com.yunxiang.shopkeeper.utils.Const;
import com.yunxiang.shopkeeper.view.RechargeDialog;


public class RechargeActivity extends Activity implements View.OnClickListener,RechargeDialog.RechargeDialogCallback{
    private Button btCommit;
    private EditText etMoney;
    private RelativeLayout rlBack;
    private CheckBox cbAli, cbChat;
    private  RechargeDialog rechargeDialog;
    private String money;
    private static final int REQUEST_CODE_PAYMENT = 1;
    /**
     * 微信支付渠道
     */
    public static final String CHANNEL_WECHAT = "wx";
    /**
     * 支付支付渠道
     */
    public static final String CHANNEL_ALIPAY = "alipay";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        initView();
    }

    private void initView() {
        btCommit = (Button) findViewById(R.id.bt_commit);
        etMoney = (EditText) findViewById(R.id.et_money);
        rlBack = (RelativeLayout) findViewById(R.id.rl_back);
        cbAli = (CheckBox) findViewById(R.id.cb_ali);
        cbChat = (CheckBox) findViewById(R.id.cb_chat);

        btCommit.setOnClickListener(this);
        rlBack.setOnClickListener(this);
        cbAli.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbChat.setChecked(false);
                } else {
                    cbChat.setChecked(true);
                }
            }
        });
        cbChat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbAli.setChecked(false);
                } else {
                    cbAli.setChecked(true);
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.bt_commit:
                String rechargeCategory="支付宝充值";
                if (cbAli.isChecked()) {
                    rechargeCategory="支付宝充值";
                }
                if (cbChat.isChecked()) {
                    rechargeCategory="微信充值";
                }
                money=etMoney.getText().toString();
                if (money.length()==0){
                    Toast.makeText(this,"请输入充值的金额",Toast.LENGTH_SHORT).show();
                    return;
                }
                //控制金额的输入

                char[] chars = money.toCharArray();
                boolean contains = money.contains(".");
                if (contains){
                    String[] split = money.split("\\.");
                    String s = split[0];
                    if (s.length()>1 && s.charAt(0)=='0'){
                        Toast.makeText(this,"输入有误，请重新输入",Toast.LENGTH_SHORT).show();
                        etMoney.setText("");
                        return;
                    }
                }else{
                    if (chars[0]=='0'){
                        Toast.makeText(this,"输入有误，请重新输入",Toast.LENGTH_SHORT).show();
                        etMoney.setText("");
                        return;
                    }
                }
                double v1 = Double.parseDouble(money);
                if (v1<=0){
                    Toast.makeText(this,"金额必须大于0",Toast.LENGTH_SHORT).show();
                    etMoney.setText("");
                    return;
                }
                money = String.format("%.2f", v1);

                rechargeDialog=new RechargeDialog(this,R.style.MyDialogStyle,"1344557355",money,rechargeCategory);
                rechargeDialog.show();
                break;
        }
    }


    class PayCallback implements Handler.Callback{
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what== Const.MSG_SUCCESS){
                String charge= (String) msg.obj;

                pay(charge);
            }
            return true;
        }
    }
    private void pay(String charge) {
        Intent intent = new Intent();
        String packageName = TApplication.context.getPackageName();

        ComponentName componentName = new ComponentName(packageName, packageName + ".wxapi.WXPayEntryActivity");
        intent.setComponent(componentName);
        intent.putExtra(PaymentActivity.EXTRA_CHARGE, charge);

        startActivityForResult(intent, REQUEST_CODE_PAYMENT);
    }
    public  void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getExtras().getString("pay_result");

                String errorMsg = data.getExtras().getString("error_msg");
                String extraMsg = data.getExtras().getString("extra_msg");
                if("success".equals(result)){

//                    UpdataOrderSatusCallback callback=new UpdataOrderSatusCallback();
//                    Handler handler=new Handler(callback);
//                    PayBiz.getInstance().upDataOrderStatus(handler);
                }else{

                }

            }
        }
    }
    public void rechargeDialogCallbackClose() {
        rechargeDialog.dismiss();
    }

    @Override
    public void rechargeDialogCallbackButton() {

        PayCallback callback=new PayCallback();
        Handler handler=new Handler(callback);
        PayBiz.getInstance().commitOrder(handler, money);
    }
}