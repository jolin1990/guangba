package com.yunxiang.shopkeeper.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.yunxiang.shopkeeper.biz.PhotoBiz;
import com.yunxiang.shopkeeper.utils.Const;
import com.yunxiang.shopkeeper.utils.DictionaryUtil;

import java.io.File;
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
                startActivityForResult(intent, RESULT_LOAD_IMAGE);
                break;
            case R.id.txt_photograph://拍照上传
                layout.setVisibility(View.INVISIBLE);
                String state = Environment.getExternalStorageState();
                if (state.equals(Environment.MEDIA_MOUNTED)) {
                    intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
                    startActivityForResult(intent, CAMERA_RESULT);
                } else {
                    Toast.makeText(getApplication(), "sdcard无效或没有插入!",
                            Toast.LENGTH_SHORT).show();
                }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PhotoBiz photoBiz = PhotoBiz.getInstance();
        String imgPath = "";
        if (requestCode == CAMERA_RESULT && resultCode == RESULT_OK) {//从照相机
            imgPath = photoBiz.savePhotoFromCamera(Const.PATH_SHOP_BANNER);
            refreshAdapter(imgPath);
        }
        //从照片中
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            imgPath = photoBiz.savePhotoFromAlbum(Const.PATH_SHOP_BANNER,data);
            refreshAdapter(imgPath);
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
