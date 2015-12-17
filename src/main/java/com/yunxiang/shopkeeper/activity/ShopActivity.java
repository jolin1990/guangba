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


                intent.setClass(ShopActivity.this, ScanCodeActivity.class);
               startActivity(intent);
                break;
        }
    }


}
