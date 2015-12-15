package com.yunxiang.shopkeeper.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yunxiang.shopkeeper.R;
import com.yunxiang.shopkeeper.TApplication;
import com.yunxiang.shopkeeper.base.BaseTitle;
import com.yunxiang.shopkeeper.biz.MerchandiseBiz;
import com.yunxiang.shopkeeper.biz.PhotoBiz;
import com.yunxiang.shopkeeper.model.Merchandise;
import com.yunxiang.shopkeeper.utils.AndroidUtils;
import com.yunxiang.shopkeeper.utils.Const;

public class MerchandiseAddActivity extends Activity implements View.OnClickListener{

    private ImageView imgAdd;
    private EditText edtName,edtPaice,edtInfo;
    private TextView txtSubmit,txtPhotograph,txtPicture,txtCancel;
    private LinearLayout layoutDialog;

    private int CAMERA_RESULT = 100;
    private int RESULT_LOAD_IMAGE = 200;
    private String imgPath;//图片路径
    private boolean isEdit = false;//是否是编辑商品

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_add);

        if(TApplication.versionType == Const.TEST_VERTION){

        }
        BaseTitle.getInstance().setTitle(this, "");
        addView();
        addListener();
    }

    private void addView() {
        imgAdd = (ImageView)findViewById(R.id.img_add);
        edtName = (EditText)findViewById(R.id.edt_name);
        edtPaice = (EditText)findViewById(R.id.edt_price);
        edtInfo = (EditText)findViewById(R.id.edt_info);
        txtSubmit = (TextView)findViewById(R.id.txt_submit);
        txtCancel = (TextView)findViewById(R.id.txt_cancel);
        txtPhotograph = (TextView)findViewById(R.id.txt_photograph);
        txtPicture = (TextView)findViewById(R.id.txt_picture);
        layoutDialog = (LinearLayout)findViewById(R.id.line_dialog);
    }

    private void addListener() {
        imgAdd.setOnClickListener(this);
        txtSubmit.setOnClickListener(this);
        txtCancel.setOnClickListener(this);
        txtPhotograph.setOnClickListener(this);
        txtPicture.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.img_add:
                layoutDialog.setVisibility(View.VISIBLE);
                txtSubmit.setVisibility(View.GONE);
                break;
            case R.id.txt_submit:
                Intent categoryIdIntent = getIntent();
                int categoryId = categoryIdIntent.getIntExtra("categoryId", -1);
                String name = edtName.getText().toString();
                String price = edtPaice.getText().toString();
                String info = edtInfo.getText().toString();
                Merchandise merchandise =new Merchandise();
                merchandise.setName(name);
                merchandise.setPrice(Double.parseDouble(price));
                merchandise.setDescription(info);
                merchandise.setCategoryId(categoryId);
                MerchandiseCallback callback = new MerchandiseCallback();
                Handler handler=new Handler(callback);
                MerchandiseBiz.getInstance().insertMerchandise(merchandise, handler);
                break;
            case R.id.txt_photograph://拍照上传
                layoutDialog.setVisibility(View.GONE);
                String state = Environment.getExternalStorageState();
                if (state.equals(Environment.MEDIA_MOUNTED)) {
                    intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
                    startActivityForResult(intent, CAMERA_RESULT);
                } else {
                    Toast.makeText(getApplication(), "sdcard无效或没有插入!",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.txt_picture://上传照片
                layoutDialog.setVisibility(View.GONE);
                intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_LOAD_IMAGE);
                break;
            case R.id.txt_cancel:
                txtSubmit.setVisibility(View.VISIBLE);
                layoutDialog.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        PhotoBiz photoBiz = PhotoBiz.getInstance();

        if (requestCode == CAMERA_RESULT && resultCode == RESULT_OK) {//从照相机
            imgPath = photoBiz.savePhotoFromCamera(Const.PATH_SHOP_IMAGE);
            setImageView();
        }
        //从照片中
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            imgPath = photoBiz.savePhotoFromAlbum(Const.PATH_SHOP_BANNER,data);
            setImageView();
        }

    }
    class MerchandiseCallback implements Handler.Callback{
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what){
                case Const.MSG_SUCCESS:
                    onBackPressed();
                    break;
                case Const.MSG_FAILURE:
                    break;
            }
            String desc = message.obj.toString();
            AndroidUtils.show(MerchandiseAddActivity.this,desc);
            return true;
        }
    }

    private void setImageView(){
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = 8;
        Bitmap bitmap = BitmapFactory.decodeFile(imgPath, bitmapOptions);
        imgAdd.setImageBitmap(bitmap);

        txtSubmit.setVisibility(View.VISIBLE);
    }
}
