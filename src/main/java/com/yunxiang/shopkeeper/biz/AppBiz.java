package com.yunxiang.shopkeeper.biz;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.yunxiang.shopkeeper.model.AppVersion;
import com.yunxiang.shopkeeper.utils.AndroidUtils;
import com.yunxiang.shopkeeper.utils.Const;
import com.yunxiang.shopkeeper.utils.DataUtils;
import com.yunxiang.shopkeeper.utils.DownloadUtils;
import com.yunxiang.shopkeeper.utils.ExceptionUtil;
import com.yunxiang.shopkeeper.utils.HttpUtils;
import com.yunxiang.shopkeeper.utils.JsonUtils;
import com.yunxiang.shopkeeper.utils.MyFileUtil;

import java.io.File;

/**
 * desc: 升级等
 */
public class AppBiz {
    private static Context context;
    private String urlString;

    public AppBiz(Context context){
        this.context = context;
    }


    /**
     * desc:获取划客最新APPVersion，如果版本旧了就更新
     * auther:jiely
     * create at 2015/10/10 19:54
     */
    public AppVersion getAppVertionFromUrl(String url){
        String jsonString = HttpUtils.doGetAsyn(url);
        boolean status = JsonUtils.getStatus(jsonString);
        if(!status){
            return null;
        }

        AppVersion appVersion = JsonUtils.getAppVersion(jsonString);
        return appVersion;

    }

    /**
     * desc:下载安装apk,先访问网络获取APP版本号，显示是否升级提示的dialog
     * auther:jiely
     * create at 2015/10/10 19:58
     */
    public void downApkAndInstall() {
        Handler.Callback callback = new UpdateDialogCallback();
        final Handler handler = new Handler(callback);
        new Thread(){
            public void run() {
                Looper.prepare();//解决错误 Can't create handler inside thread that has not called Looper.prepare()
                AppVersion appVersion = getAppVertionFromUrl(Const.URL_SHOPKEEPER_APP_VERSION);
                if(appVersion != null){
                    String thisAppVersion = AndroidUtils.getThisAppVersion();
                    if(DataUtils.isNewVersion(appVersion.getVersion(), thisAppVersion)){
                        Message message = handler.obtainMessage();
                        urlString = appVersion.getUrl();
                        message.what = 11;
                        handler.sendMessage(message);
                    }
                }
            };
        }.start();
    }

    class UpdateDialogCallback implements Handler.Callback{

        @Override
        public boolean handleMessage(final Message msg) {

            switch(msg.what) {//提示错误信息
                case 11:
                    new AlertDialog.Builder(context)
                            .setMessage("是否升级")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    AndroidUtils.show(context, "APP在下载中，请稍等");
                                    try {
                                        new DownApkTask().execute(urlString);
                                    } catch (Exception e) {
                                        ExceptionUtil.handle(e);
                                    }
                                }
                            }).setNegativeButton("取消", null).show();
                    break;
            }
            return true;
        }
    }


    class  DownApkTask extends AsyncTask<String,Void,Integer> {
        private String absolutePath;

        @Override
        protected Integer doInBackground(String... params) {
            long appLength;
            try {

                Boolean isSuccess = MyFileUtil.deleteAllFile(Const.UPDATE_APK_PATH);
                if(!isSuccess){
                    return 600;
                }
                String fileName = MyFileUtil.getFileName(urlString);
                //MyFileUtil.deleteAndCreateFile(Const.UPDATE_APK_PATH, fileName);
                absolutePath = Const.UPDATE_APK_PATH+fileName;
                appLength = DownloadUtils.downloadFileToLocal(urlString, absolutePath, null);
                if(appLength > 0){
                    return 200;
                }
            } catch (Exception e) {
                ExceptionUtil.handle(e);
                return 500;
            }

            return 500;
        }

        @Override
        protected void onPostExecute(Integer result) {

            if(result == 200){
                // 下载完成，点击安装
                File file = new File(absolutePath);
                Uri uri = Uri.fromFile(file);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(uri, "application/vnd.android.package-archive");
                context.startActivity(intent);
            }else if(result == 500){
                AndroidUtils.show(context, "下载失败");
            }else{
                AndroidUtils.show(context, "文件错误");
            }
        }
    }


}
