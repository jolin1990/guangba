package com.yunxiang.shopkeeper.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yunxiang.shopkeeper.R;
import com.yunxiang.shopkeeper.TApplication;
import com.yunxiang.shopkeeper.base.BaseTitle;
import com.yunxiang.shopkeeper.base.DateSet;
import com.yunxiang.shopkeeper.biz.PacketBiz;
import com.yunxiang.shopkeeper.model.PacketBatch;
import com.yunxiang.shopkeeper.utils.AndroidUtils;
import com.yunxiang.shopkeeper.utils.Const;

/**
 * desc:优惠券，包括折扣券和抵用券
 * auther:jiely
 * create at 2015/10/27 14:41
 */
@SuppressLint({ "NewApi", "ValidFragment" })
public class CouponEditActivity extends Activity implements View.OnClickListener {
    private LinearLayout lineNumber,lineDiscount,lineLimit;
    private EditText edtName, edtMoney, edtCount, edtInfo,edtDiscount,edtLimit;
    private TextView txtDate1, txtDate2, txtTitle;
    private Button btnSend;
    private boolean isDiscount,isOlderCustomer;//是否是折扣券 true,折扣券  false,抵用券
    private String title;//标题栏的名称


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_edit);

        addTitle();

        addView();
        setView();
        addListener();
    }

    private void addTitle() {
        //抵用券 or 折扣券
        title = TApplication.map.get(Const.MAP_KEY_NAME);
        BaseTitle.getInstance().setTitle(this, title);
    }

    private void addListener() {
        txtDate1.setOnClickListener(this);
        txtDate2.setOnClickListener(this);
        btnSend.setOnClickListener(this);
    }

    private void addView() {
        txtTitle = (TextView) findViewById(R.id.txt_title);
        lineDiscount = (LinearLayout)findViewById(R.id.line_discount);
        lineNumber = (LinearLayout)findViewById(R.id.line_number);
        lineLimit = (LinearLayout)findViewById(R.id.line_limit);
        edtName = (EditText) findViewById(R.id.edt_name);//名称
        edtMoney = (EditText) findViewById(R.id.edt_money);//面值
        edtCount = (EditText) findViewById(R.id.edt_num);//数量
        edtDiscount = (EditText) findViewById(R.id.edt_discount);//折扣
        edtInfo = (EditText) findViewById(R.id.edt_info);//
        txtDate1 = (TextView) findViewById(R.id.txt_date1);
        txtDate2 = (TextView) findViewById(R.id.txt_date2);
        btnSend = (Button) findViewById(R.id.btn_send);
        edtLimit = (EditText) findViewById(R.id.edt_money_limit);
    }

    private void setView(){

        //选择方式:为老用户 所有用户
        String selectStyle = TApplication.map.get(Const.MAP_KEY_SLECT);
        if(Const.MAP_VAL_SELECTED.equals(selectStyle)){//老用户
            isOlderCustomer = true;
            int num = TApplication.selectedCustomers.size();
            String couponInfo = "准备给"+num+"发优惠券";
            txtTitle.setText(couponInfo);
            lineNumber.setVisibility(View.GONE);
        }else if(Const.MAP_VAL_SELECT_ALL.equals(selectStyle)){//所有用户
            isOlderCustomer = false;
            txtTitle.setText("所有用户可领用");
        }

        if(Const.MAP_VAL_DISCOUNT.equals(title)){//折扣券
            lineLimit.setVisibility(View.GONE);
            isDiscount = true;
        }else {//抵用券
            isDiscount = false;
            lineDiscount.setVisibility(View.GONE);
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
            case R.id.btn_send:
                if(TApplication.versionType == Const.TEST_VERTION){
                    Intent intent = new Intent();
                    intent.setClass(CouponEditActivity.this, CouponListActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                }
                int type;
                String name = edtName.getText().toString();
                String num = edtCount.getText().toString();
                String money = edtMoney.getText().toString();
                String desc = edtInfo.getText().toString();
                String startDate = txtDate1.getText().toString();
                String stopDate = txtDate2.getText().toString();
                String discount="";
                String moneyLimit = "";
                if (isDiscount) {
                    type = Const.PACKET_DISCOUNT_COUPON;//"discountcoupons";
                    discount = edtDiscount.getText().toString();
                    if (discount.length() == 0) {
                        Toast.makeText(this, "请输入折扣", Toast.LENGTH_SHORT).show();
                        break;
                    }
                } else {
                    type = Const.PACKET_VOUCHERS;//"vouchers";
                    moneyLimit = edtLimit.getText().toString();
                    if (moneyLimit.length() == 0) {
                        Toast.makeText(this, "请输入限定的金额", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }

                if(!isOlderCustomer){
                   if(num.length()==0){
                       Toast.makeText(this, "请输入数量", Toast.LENGTH_SHORT).show();
                   }
                }

                if ( name.length()==0){
                    Toast.makeText(this,"请输入红包名称",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (money.length()==0){
                    Toast.makeText(this,"请输入红包金额",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (desc.length() == 0){
                    Toast.makeText(this,"请输入您想对顾客说的话",Toast.LENGTH_SHORT).show();
                    return;
                }
                PacketBatch packetBatch = new PacketBatch(type,name,desc);
                if("CustomerListActivity".equals(TApplication.map.get(Const.MAP_KEY_ACITIVITY))){
                    StringBuilder sb = new StringBuilder();

                    for(int i=0; i<TApplication.selectedCustomers.size(); i++){
                        sb.append(TApplication.selectedCustomers.get(i).getCustomerId());
                        if(i != TApplication.selectedCustomers.size()-1){
                            sb.append(",");
                        }
                    }
                    packetBatch.setCustomerIdList(sb.toString());
                }
                packetBatch.setTotalCount(num);
                packetBatch.setAmountMoney(money);
                packetBatch.setMoneyLimit(moneyLimit);
                packetBatch.setStartDate(startDate);
                packetBatch.setStopDate(stopDate);
                packetBatch.setDiscount(discount);
                Handler handler = new Handler(new PacketHandlerCallback());
                PacketBiz.getInstance().insertPacket(packetBatch, handler);
                break;
        }
    }

    class PacketHandlerCallback implements Handler.Callback{

        @Override
        public boolean handleMessage(Message message) {
            String desc = message.obj.toString();
            switch (message.what){
                case Const.MSG_SUCCESS:
                    Intent intent = new Intent();
                    intent.setClass(CouponEditActivity.this, CouponListActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case Const.MSG_FAILURE:
                    break;
            }
            AndroidUtils.show(CouponEditActivity.this, desc);
            return true;
        }
    }

}
