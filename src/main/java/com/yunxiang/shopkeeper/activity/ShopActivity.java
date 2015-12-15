package com.yunxiang.shopkeeper.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cardinfolink.cashiersdk.listener.CashierListener;
import com.cardinfolink.cashiersdk.model.InitData;
import com.cardinfolink.cashiersdk.model.OrderData;
import com.cardinfolink.cashiersdk.model.ResultData;
import com.cardinfolink.cashiersdk.sdk.CashierSdk;
import com.yunxiang.shopkeeper.R;
import com.yunxiang.shopkeeper.TApplication;
import com.yunxiang.shopkeeper.biz.ImageBiz;
import com.yunxiang.shopkeeper.utils.AndroidUtils;
import com.yunxiang.shopkeeper.utils.Const;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ShopActivity extends Activity implements View.OnClickListener {

    private Button btnMerchandiseManage, btnSendFavorable, btnSendPacket, btnAccountsManage, btnOrder, btnClientManage;
    private Button btnShopActive, btnMyAccount, btnMyShop, btnScan;
    private ImageView imgShop, imgAddPic;
    private TextView txtShopName;
    private int count;


    //    mchntid 	String 	商户号,由讯联数据分配
//    inscd 	String 	机构号，商户所属机构标识
//    terminalid 	String 	终端号
//    isProduce 	boolean 	生产环境必须将此值设为TRUE
//    signKey 	String 	双方约定的签名密钥

    private static final String mchntid="100000000010001";
    private static final String inscd="10134001";
    private static final String terminalid="00000021";
    private static final boolean isProduce=false;
    private static final String signkey="zsdfyreuoyamdphhaweyrjbvzkgfdycs";

    private static final int REQUESTCODE=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        addView();
        addListener();
        if(TApplication.versionType == Const.TEST_VERTION){
            return;
        }
        setView();
        //App版本更新
        AndroidUtils.updateAPP(this);



    }

    private void addView() {
        imgShop = (ImageView) findViewById(R.id.img_shop);
        imgAddPic = (ImageView) findViewById(R.id.img_add_pic);
        txtShopName = (TextView) findViewById(R.id.txt_shop_name);

        btnMerchandiseManage = (Button) findViewById(R.id.btn_merchandise_manage);
        btnSendFavorable = (Button) findViewById(R.id.btn_send_favorable);
        btnSendPacket = (Button) findViewById(R.id.btn_send_packet);
        btnAccountsManage = (Button) findViewById(R.id.btn_accounts_manage);
        btnOrder = (Button) findViewById(R.id.btn_order);
        btnClientManage = (Button) findViewById(R.id.btn_client_manage);
        btnMyAccount = (Button) findViewById(R.id.btn_my_account);
        btnShopActive = (Button) findViewById(R.id.btn_shop_active);
        btnMyShop = (Button) findViewById(R.id.btn_my_shop);
        btnScan = (Button) findViewById(R.id.btn_scan);
    }

    private void setView() {
        if (TApplication.shop != null) {
            ImageBiz imageBiz = new ImageBiz(false);
            long id = TApplication.shop.getBannerIds()[0];
            imageBiz.execute(id, imgShop);
            txtShopName.setText(TApplication.shop.getShopName());
        }
    }

    private void addListener() {
        imgAddPic.setOnClickListener(this);
        btnMerchandiseManage.setOnClickListener(this);
        btnSendFavorable.setOnClickListener(this);
        btnSendPacket.setOnClickListener(this);
        btnAccountsManage.setOnClickListener(this);
        btnOrder.setOnClickListener(this);
        btnClientManage.setOnClickListener(this);
        btnMyAccount.setOnClickListener(this);
        btnShopActive.setOnClickListener(this);
        btnMyShop.setOnClickListener(this);
        btnScan.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.img_add_pic://添加图片
                intent.setClass(ShopActivity.this, AddSpannerActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_merchandise_manage://商品管理
                intent.setClass(ShopActivity.this, MerchandiseActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_send_favorable://发优惠
                intent.setClass(ShopActivity.this, CouponActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_send_packet://发红包
                intent.setClass(ShopActivity.this,PacketActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_accounts_manage://账目管理
                intent.setClass(ShopActivity.this, AccountManageActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_order://订单
                intent.setClass(ShopActivity.this, OrderActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_client_manage://客户管理
                intent.setClass(ShopActivity.this, CustomerManageActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_my_account://我的账户
                intent.setClass(ShopActivity.this, MyCountActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_shop_active://店铺活动
                intent.setClass(ShopActivity.this, ActiveActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_my_shop://我的店铺
                intent.setClass(ShopActivity.this, MyShopActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_scan: //扫码收款
                intent.setClass(ShopActivity.this, CaptureActivity.class);
                startActivityForResult(intent, REQUESTCODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String scanCodeId="";
        if (requestCode==REQUESTCODE&&resultCode==RESULT_OK){
            scanCodeId=data.getStringExtra("code");
            Log.d("TAG","##scanCodeId="+scanCodeId);
        }
        if (scanCodeId!=null&&scanCodeId.length()!=0){
            //下单 txamt,orderNum,scanCodeId,currency
            OrderData orderData = new OrderData();
            orderData.currency="156";
            Date date=new Date();
            DateFormat dateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
            String formatDate = dateFormat.format(date);

            orderData.orderNum="yx"+formatDate;
            orderData.txamt="0.01";
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
