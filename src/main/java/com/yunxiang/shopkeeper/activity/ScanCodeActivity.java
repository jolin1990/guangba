package com.yunxiang.shopkeeper.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cardinfolink.cashiersdk.listener.CashierListener;
import com.cardinfolink.cashiersdk.model.InitData;
import com.cardinfolink.cashiersdk.model.OrderData;
import com.cardinfolink.cashiersdk.model.ResultData;
import com.cardinfolink.cashiersdk.sdk.CashierSdk;
import com.yunxiang.shopkeeper.R;
import com.yunxiang.shopkeeper.TApplication;
import com.yunxiang.shopkeeper.base.BaseTitle;
import com.yunxiang.shopkeeper.utils.Const;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * desc: 扫码收款
 * auther:jiely
 * create at 2015/11/25 15:25
 */
public class ScanCodeActivity extends Activity {

    private Button btnCertain;
    private EditText edtMoney;
    //    mchntid 	String 	商户号,由讯联数据分配
//    inscd 	String 	机构号，商户所属机构标识
//    terminalid 	String 	终端号
//    isProduce 	boolean 	生产环境必须将此值设为TRUE
//    signKey 	String 	双方约定的签名密钥

//    private static final String mchntid="100000000010001";
//    private static final String inscd="10134001";
//    private static final String terminalid="00000021";
//    private static final boolean isProduce=false;
//    private static final String signkey="zsdfyreuoyamdphhaweyrjbvzkgfdycs";

    private static final String mchntid="100000000000202";
    private static final String inscd="10134001";
    private static final String terminalid="00000001";
    private static final boolean isProduce=false;
    private static final String signkey="zsdfyreuoyamdphhaweyrjbvzkgfdycs";



    private static final int REQUESTCODE=0;
    private String money;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code);
        if(TApplication.versionType == Const.TEST_VERTION){

        }
        addView();
        addTitle();
    }
    //回调接口实现
    CashierListener listener = new CashierListener() {
        @Override
        public void onResult(ResultData resultData) {
            //下单支付  txamt,orderNum,scanCodeId,currency
            //用户接收结果，进行处理
            if (resultData.busicd.equals("PURC")) {
                //交易成功
                if ("00".equals(resultData.respcd)) {
                    Log.d("TAG", "交易成功");
                } else if ("09".equals(resultData.respcd)) {
                    //交易处理中，查询订单
                    OrderData orderData = new OrderData();
                    orderData.origOrderNum = resultData.origOrderNum;
                    CashierSdk.startQy(orderData, listener);
                }
            } else if (resultData.busicd.equals("PAUT")) {
                //预下单
            } else if (resultData.busicd.equals("INQY")) {
                //订单查询
                if ("00".equals(resultData.respcd)){
                    Log.d("TAG", "交易成功");
                }else{
                    Log.d("TAG", "交易信息获取失败，请手动查询");
                }
            } else if (resultData.busicd.equals("VOID")) {
                //撤消交易
            } else if (resultData.busicd.equals("REFD")) {
                //退款交易
            } else if (resultData.busicd.equals("VERI")) {
                //卡券核销
            }
        }
        @Override
        public void onError(int errorCode) {
            //处理逻辑代码
            switch (errorCode) {
                case 0:
                    Log.d("TAG", "请求超时");
                    break;
                case 1:
                    Log.d("TAG",":OrderData.txamt:格式错误");
                    break;
                case 2:
                    Log.d("TAG","币种不能为空，或不支持该币种");
                    break;
                case 3:
                    Log.d("TAG", "服务器结果签名错误");
                    break;
                case 4:
                    Log.d("TAG", "服务器无法处理的订单");
                    break;
            }
        }
    };

    private void addView() {
        edtMoney = (EditText)findViewById(R.id.edt_money);
        btnCertain = (Button)findViewById(R.id.btn_certain);

        btnCertain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                money=edtMoney.getText().toString();
                if (money.length()==0){
                    Toast.makeText(ScanCodeActivity.this,"请输入金额",Toast.LENGTH_LONG).show();
                    return;
                }   //控制金额的输入

                char[] chars = money.toCharArray();
                boolean contains = money.contains(".");
                if (contains){
                    String[] split = money.split("\\.");
                    String s = split[0];
                    if (s.length()>1 && s.charAt(0)=='0'){
                        Toast.makeText(ScanCodeActivity.this,"输入有误，请重新输入",Toast.LENGTH_SHORT).show();
                        edtMoney.setText("");
                        return;
                    }
                }else{
                    if (chars[0]=='0'){
                        Toast.makeText(ScanCodeActivity.this,"输入有误，请重新输入",Toast.LENGTH_SHORT).show();
                        edtMoney.setText("");
                        return;
                    }
                }
                double v1 = Double.parseDouble(money);
                if (v1<=0){
                    Toast.makeText(ScanCodeActivity.this,"金额必须大于0",Toast.LENGTH_SHORT).show();
                    edtMoney.setText("");
                    return;
                }
                money = String.format("%.2f", v1);
                Intent intent = new Intent();
                intent.setClass(ScanCodeActivity.this, CaptureActivity.class);
                startActivityForResult(intent, REQUESTCODE);
            }
        });
    }

    private void addTitle() {
        BaseTitle.getInstance().setTitle(this, "设置金额");
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String scanCodeId="";
        if (requestCode==REQUESTCODE&&resultCode==RESULT_OK){
            scanCodeId=data.getStringExtra("code");
            Log.d("TAG", "##scanCodeId=" + scanCodeId);
        }
        if (scanCodeId!=null&&scanCodeId.length()!=0){
            //下单 txamt,orderNum,scanCodeId,currency
            OrderData orderData = new OrderData();
            orderData.currency="156";
            Date date=new Date();
            DateFormat dateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
            String formatDate = dateFormat.format(date);

            orderData.orderNum="yx"+formatDate;
            orderData.txamt=money;
            orderData.scanCodeId=scanCodeId;

            Log.d("TAG","scanCodeId="+scanCodeId+"date="+formatDate);
            init();//初始化扫码支付
            CashierSdk.startPay(orderData, listener);
        }

    }
    private void init() {
        //初始化
        InitData initData = new InitData();
        initData.mchntid=mchntid;
        initData.inscd=inscd;
        initData.terminalid=terminalid;
        initData.isProduce=isProduce;
        initData.signKey=signkey;
        CashierSdk.init(initData);
    }

}
