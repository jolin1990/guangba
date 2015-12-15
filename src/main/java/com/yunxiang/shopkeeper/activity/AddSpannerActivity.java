package com.yunxiang.shopkeeper.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yunxiang.shopkeeper.R;
import com.yunxiang.shopkeeper.TApplication;
import com.yunxiang.shopkeeper.base.Base64Coder;
import com.yunxiang.shopkeeper.biz.PhotoBiz;
import com.yunxiang.shopkeeper.utils.Const;
import com.yunxiang.shopkeeper.utils.DictionaryUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class AddSpannerActivity extends Activity implements View.OnClickListener {

    private int countMode = 1;//1,2,4,8,16,32
    private ImageView imgPhoto, imgDelete, imgLeft, imgTest;
    private LinearLayout layout;
    private TextView txtPhotograph, txtPicture, txtCancel, txtDelete;
    private GridView gridView;
    private File savePath;
    private List<String> pathList;
    private List<String> imgList;
    private GrideViewAdapter adapter;
    private boolean isDelete = false;
    private int CAMERA_RESULT = 100;
    private int RESULT_LOAD_IMAGE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_image);

        if(TApplication.versionType == Const.TEST_VERTION){

        }
        addTitle();
        addData();
        addView();
        addAdapter();
        addListener();
    }

    private void addData() {
        imgList = new ArrayList<String>();
        String mode = DictionaryUtil.read(Const.VAL_SPANNER_MODE);
        if (mode == null || mode.length() == 0 ) {
            countMode = 1;
        } else {
            countMode = Integer.parseInt(mode);
            if(countMode == 0){
                countMode = 1;
            }
        }

        pathList = new ArrayList<String>();
        for (int i = 1; i < countMode; i *= 2) {
            String path = DictionaryUtil.read(Const.VAL_SPANNER_PIC + i);
            imgList.add(path);
            pathList.add(path);
        }
        pathList.add("");

    }

    private void addListener() {
        txtCancel.setOnClickListener(this);
        txtPhotograph.setOnClickListener(this);
        txtPicture.setOnClickListener(this);
        imgLeft.setOnClickListener(this);
        txtDelete.setOnClickListener(this);
    }

    private void addAdapter() {

        adapter = new GrideViewAdapter(this);

        gridView.setAdapter(adapter);
        if(!isDelete){
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int mode = positionToMode(position);
                    if ((countMode & mode) == mode) {
                        layout.setVisibility(View.VISIBLE);
                    }
                }
            });
        }

    }

    private int positionToMode(int position) {
        int mode = 1;
        for (int i = 0; i < position; i++) {
            mode *= 2;
        }
        return mode;
    }

    private void addView() {

        layout = (LinearLayout) findViewById(R.id.line_dialog);
        txtPicture = (TextView) findViewById(R.id.txt_picture);
        txtPhotograph = (TextView) findViewById(R.id.txt_photograph);
        txtCancel = (TextView) findViewById(R.id.txt_cancel);
        gridView = (GridView) findViewById(R.id.gv_photo);
        imgTest = (ImageView) findViewById(R.id.img_test);
    }

    private void addTitle() {
        imgLeft = (ImageView) findViewById(R.id.img_left);
        txtDelete = (TextView) findViewById(R.id.txt_delete);

    }


    private class GrideViewAdapter extends BaseAdapter {

        private LayoutInflater inflater;

        public GrideViewAdapter(Context context) {
            super();
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return pathList.size();
        }

        @Override
        public Object getItem(int position) {

            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View view, ViewGroup parent) {
            if (view == null) {
                view = inflater.inflate(R.layout.layout_photo, parent, false);
            }

            try {
                imgPhoto = (ImageView) view.findViewById(R.id.img_photo);

                if (position < pathList.size() - 1) {
                    int mode = positionToMode(position);
                    String path = DictionaryUtil.read(Const.VAL_SPANNER_PIC + mode);

                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmapOptions.inSampleSize = 8;
                    Bitmap bitmap = BitmapFactory.decodeFile(path, bitmapOptions);
                    imgPhoto.setImageBitmap(bitmap);

                    imgDelete = (ImageView) view.findViewById(R.id.img_delete);
                    if (isDelete) {
                        imgDelete.setVisibility(View.VISIBLE);
                        imgDelete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                imgDelete.setVisibility(View.INVISIBLE);
                                pathList.remove(position);
                                if(countMode>1){
                                    countMode /= 2;
                                    DictionaryUtil.write(Const.VAL_SPANNER_MODE, String.valueOf(countMode));
                                }

                                imgList.remove(position);
                                int mode = positionToMode(position);
                                for (int i = mode * 2, j = 0; i < countMode; i *= 2, j++) {
                                    DictionaryUtil.write(Const.VAL_SPANNER_PIC + i, pathList.get(position + j));
                                }

                                pathList.clear();
                                pathList.addAll(imgList);
                                pathList.add("");
                                adapter.notifyDataSetChanged();
                            }
                        });
                    } else {
                        imgDelete.setVisibility(View.INVISIBLE);
                    }
                } else {
                    imgPhoto.setImageBitmap(null);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return view;
        }
    }



    @Override
    public void onClick(View v) {
        createPhotoFile();

        Intent intent;
        switch (v.getId()) {
            case R.id.txt_picture://上传照片
                layout.setVisibility(View.INVISIBLE);
                intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);

                break;
            case R.id.txt_photograph://拍照上传
                layout.setVisibility(View.INVISIBLE);
//                String state = Environment.getExternalStorageState();
//                if (state.equals(Environment.MEDIA_MOUNTED)) {
//                    intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
//                    startActivityForResult(intent, 2);
//                } else {
//                    Toast.makeText(getApplication(), "sdcard无效或没有插入!",
//                            Toast.LENGTH_SHORT).show();
//                }

                intent = new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                        .fromFile(new File(Environment
                                .getExternalStorageDirectory(),
                                "jolin.jpg")));
                startActivityForResult(intent, 2);




                break;
            case R.id.txt_cancel:
                layout.setVisibility(View.INVISIBLE);
                break;

            case R.id.img_left:
                onBackPressed();
                break;
            case R.id.txt_delete:
                isDelete = !isDelete;
                pathList.clear();
                pathList.addAll(imgList);
                pathList.add("");
                adapter.notifyDataSetChanged();
                break;
        }
    }

    private void createPhotoFile() {
        savePath = new File(Const.PATH_SHOP_BANNER);
        if (!savePath.exists()) {
            savePath.mkdirs();
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        PhotoBiz photoBiz = PhotoBiz.getInstance();
//        String imgPath = "";
//        if (requestCode == CAMERA_RESULT && resultCode == RESULT_OK) {//从照相机
//            imgPath = photoBiz.savePhotoFromCamera(Const.PATH_SHOP_BANNER);
//            refreshAdapter(imgPath);
//        }
//        //从照片中
//        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
//            imgPath = photoBiz.savePhotoFromAlbum(Const.PATH_SHOP_BANNER,data);
//            refreshAdapter(imgPath);
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // 如果是直接从相册获取
            case 1:
                startPhotoZoom(data.getData());
                break;
            // 如果是调用相机拍照时
            case 2:
                File temp = new File(Environment.getExternalStorageDirectory()
                        + "/jolin.jpg");
                startPhotoZoom(Uri.fromFile(temp));
                break;
            // 取得裁剪后的图片
            case 3:
                /**
                 　　* 非空判断大家一定要验证，如果不验证的话，
                 　　* 在剪裁之后如果发现不满意，要重新裁剪，丢弃
                 　　* 当前功能时，会报NullException，小马只
                 　　* 在这个地方加下，大家可以根据不同情况在合适的
                 　　* 地方做判断处理类似情况
                 　　*
                 　　*/
                if(data != null){
                    setPicToView(data);
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    /**
     　　* 裁剪图片方法实现
     　　* @param uri
     　　*/
    public void startPhotoZoom(Uri uri) {
            /*
    　　* 至于下面这个Intent的ACTION是怎么知道的，大家可以看下自己路径下的如下网页
    　　* yourself_sdk_path/docs/reference/android/content/Intent.html
    　　* 直接在里面Ctrl+F搜：CROP ，之前小马没仔细看过，其实安卓系统早已经有自带图片裁剪功能,
    　　* 是直接调本地库的，小马不懂C C++ 这个不做详细了解去了，有轮子就用轮子，不再研究轮子是怎么
    　　* 制做的了...吼吼
    　　*/
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 3);
        intent.putExtra("aspectY", 2);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }
    /**
     　　* 保存裁剪之后的图片数据
     　　* @param picdata
     　　*/
    private void setPicToView(Intent picdata) {
        Bundle extras = picdata.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(photo);

            /**
             　　* 下面注释的方法是将裁剪之后的图片以Base64Coder的字符方式上
             　　* 传到服务器，QQ头像上传采用的方法跟这个类似
             　　*/
               ByteArrayOutputStream stream = new ByteArrayOutputStream();
    photo.compress(Bitmap.CompressFormat.JPEG, 60, stream);
    byte[] b = stream.toByteArray();


            // 将图片流以字符串形式存储下来
   String tp = new String(Base64Coder.encodeString(b));
//    　　这个地方大家可以写下给服务器上传图片的实现，直接把tp直接上传就可以了，
//    　　服务器处理的方法是服务器那边的事了，吼吼
//    　　如果下载到的服务器的数据还是以Base64Coder的形式的话，可以用以下方式转换
//    　　为我们可以用的图片类型就OK啦...吼吼
//    　　Bitmap dBitmap = BitmapFactory.decodeFile(tp);
//    　　Drawable drawable = new BitmapDrawable(dBitmap);
//    　　*/
//            ib.setBackgroundDrawable(drawable);
//            iv.setBackgroundDrawable(drawable);
            refreshAdapter(tp);
        }
    }













    private void refreshAdapter(String path){
        pathList.clear();
        pathList.addAll(imgList);
        pathList.add(path);
        DictionaryUtil.write(Const.VAL_SPANNER_PIC + countMode, path);
        imgList.add(path);
        pathList.add("");
        adapter.notifyDataSetChanged();
        countMode *= 2;
        DictionaryUtil.write(Const.VAL_SPANNER_MODE, String.valueOf(countMode));//"spanner_mode",
    }

}
