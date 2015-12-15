package com.yunxiang.shopkeeper.biz;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.yunxiang.shopkeeper.TApplication;
import com.yunxiang.shopkeeper.utils.FileUtil;
import com.yunxiang.shopkeeper.utils.StringUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @desc: Created by jiely on 2015/11/2.
 */
public class PhotoBiz {

    private static Context context;

    private static PhotoBiz instance;

    public static PhotoBiz getInstance(){
        if(instance == null){
            instance = new PhotoBiz();
        }
        return instance;
    }
    private PhotoBiz() {
        context = TApplication.context;
    }

    /**
     * desc: 用照相机拍照并把照片存入指定文件夹下，并创建文件文件名为当前时间.jpg
     * forderPath
     * auther:jiely
     * return:图片地址
     * create at 2015/11/2 16:58
     */
    public  String savePhotoFromCamera(String folderPath){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        StringBuilder sb = new StringBuilder();
        sb.append(folderPath);
        sb.append(str);
        sb.append(".jpg");
        File mPhotoFile = new File(sb.toString());
        if (!mPhotoFile.exists()) {
            try {
                mPhotoFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return sb.toString();
    }

    /**
     * desc:从相册中获取图片并把照片存入指定文件夹下，
     * auther:jiely
     * return:图片地址
     * create at 2015/11/2 17:13
     */
    public  String savePhotoFromAlbum(String folderPath,Intent data){

        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        String fileName = StringUtils.getImageFileName(picturePath);
        String newPath = folderPath + fileName;
        FileUtil.copyFile(picturePath, newPath);
        cursor.close();
        return  newPath;
    }

}
