package com.yunxiang.shopkeeper.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.yunxiang.shopkeeper.R;
import com.yunxiang.shopkeeper.TApplication;
import com.yunxiang.shopkeeper.base.BaseTitle;
import com.yunxiang.shopkeeper.biz.ImageBiz;
import com.yunxiang.shopkeeper.biz.PhotoBiz;
import com.yunxiang.shopkeeper.biz.ShopBiz;
import com.yunxiang.shopkeeper.model.Shop;
import com.yunxiang.shopkeeper.service.impl.ShopService;
import com.yunxiang.shopkeeper.utils.Const;

public class ShopInfoActivity extends Activity implements View.OnClickListener {
    private EditText edtName, edtAddress, edtInfo, edtPhone,edtMaster,edtMasterPhone;
    private Spinner spType;
    private LinearLayout layoutBottom;
    private ImageView imgHead;
    private Button btnCommit,btnLocation;
    private TextView txtphoto, txtCamara, txtCancle;//,txt_modify_head;
    private int CAMERA_RESULT = 100;
    private int RESULT_LOAD_IMAGE = 200;
    private  static final String[] ShopCategoryArray={"餐饮美食","休闲娱乐","美容美发","其他"};
    private  String imgPath = null;
    private boolean isEdit = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_info);

        if(TApplication.versionType == Const.TEST_VERTION){

        }
        addTitle();
        addView();
        addIntent();
        setListener();

    }

    private void addIntent() {
        String activityName = TApplication.map.get(Const.MAP_KEY_ACITIVITY);
        if("MyShopActivity".equals(activityName)){//
            //显示商店数据
            disPlayShopInfo();
            spType.setClickable(false);
        }else{
            spType.setClickable(true);
            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,R.layout.layout_spinner,ShopCategoryArray);
            spType.setAdapter(spinnerAdapter);
        }
    }

    private void setListener() {
        imgHead.setOnClickListener(this);
        txtCamara.setOnClickListener(this);
        txtphoto.setOnClickListener(this);
        txtCancle.setOnClickListener(this);
        btnCommit.setOnClickListener(this);
        btnLocation.setOnClickListener(this);
    }

    private void addView() {
        edtName = (EditText) findViewById(R.id.edt_shop_name);
        edtPhone = (EditText) findViewById(R.id.edt_shop_phone);
        edtAddress = (EditText) findViewById(R.id.edt_shop_address);
        spType = (Spinner) findViewById(R.id.sp_type);
        edtMaster = (EditText)findViewById(R.id.edt_shop_master);
        edtMasterPhone = (EditText)findViewById(R.id.edt_shop_master_phone);
        edtInfo = (EditText) findViewById(R.id.edt_introduce);
        imgHead = (ImageView) findViewById(R.id.img_head);
        layoutBottom = (LinearLayout) findViewById(R.id.line_dialog);
        btnCommit = (Button) findViewById(R.id.btn_commit);
        btnLocation = (Button) findViewById(R.id.btn_location);

        TextView txt_modify_head = (TextView)findViewById(R.id.txt_modify_head);
        txtCamara = (TextView) findViewById(R.id.txt_photograph);
        txtCancle = (TextView) findViewById(R.id.txt_cancel);
        txtphoto = (TextView) findViewById(R.id.txt_picture);

        txt_modify_head.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//加下划线
    }

    private void disPlayShopInfo() {
        if(TApplication.shop != null){
            edtName.setHint(TApplication.shop.getShopName());
            edtAddress.setHint(TApplication.shop.getAddress());
            edtInfo.setHint(TApplication.shop.getDescribe());
            edtPhone.setHint(TApplication.shop.getShopPhone());
            spType.setSelection(TApplication.shop.getTypeId(), true);
            edtMaster.setText(TApplication.shop.getMasterName());
            edtMasterPhone.setText(TApplication.shop.getMasterPhone());
            //填充图片
            ImageBiz imagebiz=new ImageBiz(false);
            if(TApplication.shop.getSmallImageURL() != null){
                imagebiz.execute(TApplication.shop.getSmallImageURL(),imgHead);
            }
        }
    }

    private void addTitle() {
        BaseTitle.getInstance().setTitle(this, "店铺信息");
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.txt_head_img://更换头像
                layoutBottom.setVisibility(View.VISIBLE);
                btnCommit.setVisibility(View.GONE);
                break;
            case R.id.txt_picture://照片
                intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_LOAD_IMAGE);
                break;
            case R.id.txt_photograph://相机
                String state = Environment.getExternalStorageState();
                if (state.equals(Environment.MEDIA_MOUNTED)) {
                    intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
                    startActivityForResult(intent, CAMERA_RESULT);
                } else {
                    Toast.makeText(getApplication(), "sdcard无效或没有插入!",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.txt_cancel://取消
                layoutBottom.setVisibility(View.GONE);
                btnCommit.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_commit://提交
                UpDataShopInfoCallback callback=new UpDataShopInfoCallback();
                Handler handler=new Handler(callback);
                Shop shop;
                if(TApplication.shop == null){
                    shop = new Shop();
                }else {
                    shop = TApplication.shop;
                }
                shop.setDescribe(edtInfo.getText().toString());
                shop.setAddress(edtAddress.getText().toString());
                shop.setTypeId((int) spType.getSelectedItemId());
                shop.setShopName(edtName.getText().toString());
                shop.setShopPhone(edtPhone.getText().toString());
                shop.setMasterName(edtMaster.getText().toString());
                shop.setMasterPhone(edtMasterPhone.getText().toString());

                if(TApplication.shop == null){
                    ShopBiz.getInstance().insertShop(shop, handler);
                }else {
                    if(!shop.equals(TApplication.shop)){//内容有更改
                        ShopBiz.getInstance().updateShop(shop,handler);
                    }
                }
                break;
            case R.id.btn_location://百度地图获取地址
                String address = "";
                edtAddress.setText(address);
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PhotoBiz photoBiz = PhotoBiz.getInstance();

        if (requestCode == CAMERA_RESULT && resultCode == RESULT_OK) {//从照相机
            imgPath = photoBiz.savePhotoFromCamera(Const.PATH_SHOP_BANNER);
        }
        //从照片中
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            imgPath = photoBiz.savePhotoFromAlbum(Const.PATH_SHOP_BANNER, data);
        }

        UpDataShopInfoCallback callback=new UpDataShopInfoCallback();
        Handler handler=new Handler(callback);
        if(TApplication.shop.getSmallImageURL() == null){
            ShopBiz.getInstance().insertShopImage(imgPath, handler);
        }else {
            ShopBiz.getInstance().updateShopImage(imgPath, handler);
        }
        setImageView(imgPath, imgHead);
    }

    private void setImageView(String path, ImageView imgView) {
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = 8;
        Bitmap bitmap = BitmapFactory.decodeFile(path, bitmapOptions);
        imgView.setImageBitmap(bitmap);
        //还原底部button
        layoutBottom.setVisibility(View.GONE);
        btnCommit.setVisibility(View.VISIBLE);
    }

    class UpDataShopInfoCallback implements Handler.Callback{
        @Override
        public boolean handleMessage(Message message) {
            String desc = message.obj.toString();
            switch (message.what){
                case Const.MSG_SUCCESS:
                    break;
                case Const.MSG_INSERT_SHOP_SUCCESS:
                case Const.MSG_UPDATE_SHOP_SUCCESS:
                    ShopService.getInstanse().getShop();
                    Toast.makeText(ShopInfoActivity.this, desc, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setClass(ShopInfoActivity.this,AccountManageActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case Const.MSG_FAILURE:
                    Toast.makeText(ShopInfoActivity.this,desc,Toast.LENGTH_SHORT).show();
                    break;
            }
            return  true;
        }
    }

}
