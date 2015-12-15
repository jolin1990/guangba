package com.yunxiang.shopkeeper.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * desc:
 * auther:jiely
 * create at 2015/10/21 14:28
 */
public class NetUtil {

    /**
     * desc:get方式，通过问好传参提交数据
     * auther:jiely
     * create at 2015/10/21 14:53
     */
    public static String getContent(String url) {
        StringBuilder sb = new StringBuilder();
        try {
            HttpGet get = new HttpGet(url);
            HttpClient client = new DefaultHttpClient();
            HttpResponse response = client.execute(get);
            StatusLine state = response.getStatusLine();
            if (state.getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity eneity = response.getEntity();
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        eneity.getContent()));
                String content;
                while ((content = br.readLine()) != null) {
                    sb.append(content);
                }
            }
            get.abort();
        } catch (Exception e) {
            e.printStackTrace();
            return sb.toString();
        }
        return sb.toString();
    }


    /**
     * desc:post方式，通过问好传参提交数据
     * auther:jiely
     * create at 2015/10/21 15:04
     */
    public static String getPostContent(String url)
            throws Exception {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);

        HttpResponse response = client.execute(post);
        /** 返回状态 **/
        int statusCode = response.getStatusLine().getStatusCode();
        StringBuffer sb = new StringBuffer();
        if (statusCode == HttpStatus.SC_OK) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        instream));
                String tempLine;
                while ((tempLine = br.readLine()) != null) {
                    sb.append(tempLine);
                }
            }
        }
        post.abort();
        return sb.toString();
    }

    /**
     * 检查网络是否可用
     *
     * @param paramContext
     * @return
     */
    public static boolean checkEnable(Context paramContext) {
        boolean i = false;
        NetworkInfo localNetworkInfo = ((ConnectivityManager) paramContext
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if ((localNetworkInfo != null) && (localNetworkInfo.isAvailable()))
            return true;
        return false;
    }

    /**
     * 将ip的整数形式转换成ip形式
     *
     * @param ipInt
     * @return
     */
    public static String int2ip(int ipInt) {
        StringBuilder sb = new StringBuilder();
        sb.append(ipInt & 0xFF).append(".");
        sb.append((ipInt >> 8) & 0xFF).append(".");
        sb.append((ipInt >> 16) & 0xFF).append(".");
        sb.append((ipInt >> 24) & 0xFF);
        return sb.toString();
    }

    /**
     * 获取当前ip地址
     *
     * @param context
     * @return
     */
    public static String getLocalIpAddress(Context context) {
        try {

            WifiManager wifiManager = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int i = wifiInfo.getIpAddress();
            return int2ip(i);
        } catch (Exception ex) {
            return " 获取IP出错鸟!!!!请保证是WIFI,或者请重新打开网络!\n" + ex.getMessage();
        }
    }
}
