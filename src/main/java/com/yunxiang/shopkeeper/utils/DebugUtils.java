package com.yunxiang.shopkeeper.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.yunxiang.shopkeeper.TApplication;


/**
 * @desc: Created by jiely on 2015/11/6.
 */
public class DebugUtils {

    public static void show(Context context,String text){
        if(TApplication.versionType != Const.RELEASE_VERTION){
            Toast.makeText(context, text, Toast.LENGTH_LONG).show();
        }
    }

    public static void i(String tag,Object msg){
        //1，在用户真机上运行，打到logcat没用，还占时间
        if(TApplication.versionType != Const.RELEASE_VERTION){
            Log.i(tag, String.valueOf(msg));
        }

    }

    public static void d(String tag,Object msg){
        if(TApplication.versionType != Const.RELEASE_VERTION){
            Log.d(tag, String.valueOf(msg));
        }
    }

    public static void m(String tag,Object msg){
        if(TApplication.versionType != Const.RELEASE_VERTION){
            Log.d(tag, String.valueOf(msg));
        }
    }

    public static void e(String tag, String msg) {

        if(TApplication.versionType != Const.RELEASE_VERTION){
            Log.e(tag, String.valueOf(msg));
        }
    }
}
