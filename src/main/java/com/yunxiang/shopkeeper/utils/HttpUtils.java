package com.yunxiang.shopkeeper.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.yunxiang.shopkeeper.TApplication;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * desc: Created by jiely on 2015/9/8.
 */
public class HttpUtils {

    /**
     * 判断是否是wifi连接
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm == null)
            return false;
        int type;
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        if (activeNetworkInfo != null) {
            type = cm.getActiveNetworkInfo().getType();
            return type == ConnectivityManager.TYPE_WIFI;
        }
        return false;
    }

    public static boolean isNetworkAvailable() {
        //Context context = activity.getApplicationContext();
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) TApplication.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        } else {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    //System.out.println(i + "===状态===" + networkInfo[i].getState());
                    //System.out.println(i + "===类型===" + networkInfo[i].getTypeName());
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * desc: 查询 发送http头直接获取用户信息
     * auther:jiely
     * create at 2015/11/4 18:09
     */
    public static String doGetAsyn(String path) {
        DebugUtils.d("httpUtils", "url=" + path);
        HttpGet httpGet = new HttpGet(path);
        String head = DictionaryUtil.read("JSESSIONID");
        httpGet.setHeader("Cookie", "JSESSIONID=" + head);
        HttpClient client = new DefaultHttpClient();
        String strResult = "";
        try {
            //*发送请求并等待响应*//*
            HttpResponse response = client.execute(httpGet);
            StatusLine state = response.getStatusLine();
            HttpResponse httpResponse = new DefaultHttpClient().execute(httpGet);
            //*若状态码为200 ok*//*
            if (state.getStatusCode() == HttpStatus.SC_OK) {
                strResult = EntityUtils.toString(httpResponse.getEntity());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        DebugUtils.d("httpUtils", "jsonString=" + strResult);
        return strResult;
    }

    /**
     * desc: 登录并获取Http头，存入本地sql
     * auther:jiely
     * create at 2015/11/4 18:09
     */
    public static String doGetLogin(String path) {
        HttpGet httpGet = new HttpGet(path);
        DebugUtils.d("httpUtils", "url=" + path);
        String strResult = "";
        try {
            //*发送请求并等待响应*//*
            HttpClient client = new DefaultHttpClient();
            HttpResponse httpResponse = client.execute(httpGet);
            //*若状态码为200 ok*//*
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                strResult = EntityUtils.toString(httpResponse.getEntity());
                CookieStore cookieStore = ((AbstractHttpClient) client).getCookieStore();
                List<Cookie> cookies = cookieStore.getCookies();
                for (int i = 0; i < cookies.size(); i++) {
                    if ("JSESSIONID".equals(cookies.get(i).getName())) {
                        String jsessionid = cookies.get(i).getValue();
                        DictionaryUtil.write("JSESSIONID", jsessionid);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        DebugUtils.d("httpUtils", "jsonString=" + strResult);
        return strResult;
    }


    /**
     * 以POST方式获取数据
     * param url
     * param param
     * param file
     * return
     * throws Exception
     */
    public static String doPost(String url) throws Exception {
        HttpPost post = new HttpPost(url);
        String head = DictionaryUtil.read("JSESSIONID");
        post.setHeader("Cookie", "JSESSIONID=" + head);
        HttpClient client = new DefaultHttpClient();
        DebugUtils.d("httpUtils", "doPost url=" + url);

        HttpResponse response = client.execute(post);
        int stateCode = response.getStatusLine().getStatusCode();
        StringBuilder sb = new StringBuilder();
        if (stateCode == HttpStatus.SC_OK) {
            HttpEntity result = response.getEntity();
            if (result != null) {
                InputStream is = result.getContent();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String tempLine;
                while ((tempLine = br.readLine()) != null) {
                    sb.append(tempLine);
                }
            }
        }
        //post.abort();
        DebugUtils.d("httpUtils", "doPost jsonString=" + sb.toString());
        return sb.toString();
    }

    /**
     * 以POST方式提交文件
     * param url
     * param param
     * param file
     * return
     * throws Exception
     */
    public static String doPostFile(String url, String path)
            throws Exception {
        HttpPost post = new HttpPost(url);
        String head = DictionaryUtil.read("JSESSIONID");
        post.setHeader("Cookie", "JSESSIONID=" + head);
        HttpClient client = new DefaultHttpClient();
        DebugUtils.d("httpUtils", "doPost url=" + url);

        // 添加文件参数
        if (path != null) {
            MultipartEntity entity = new MultipartEntity();
            File file = new File(path);
            if (file.exists()) {
                entity.addPart(String.valueOf(file), new FileBody(file));
            }
            post.setEntity(entity);
        }

        HttpResponse response = client.execute(post);
        int stateCode = response.getStatusLine().getStatusCode();
        StringBuilder sb = new StringBuilder();
        if (stateCode == HttpStatus.SC_OK) {
            HttpEntity result = response.getEntity();
            if (result != null) {
                InputStream is = result.getContent();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String tempLine;
                while ((tempLine = br.readLine()) != null) {
                    sb.append(tempLine);
                }
            }
        }
        //post.abort();
        DebugUtils.d("httpUtils", "doPost jsonString=" + sb.toString());
        return sb.toString();
    }




    /**
     * 以POST方式获取数据
     * param url
     * param param
     * param file
     * return
     * throws Exception
     */
    public static String doPut(String url) throws Exception {
        HttpPut put = new HttpPut(url);
        String head = DictionaryUtil.read("JSESSIONID");
        put.setHeader("Cookie", "JSESSIONID=" + head);
        HttpClient client = new DefaultHttpClient();
        DebugUtils.d("httpUtils", "doPost url=" + url);


        HttpResponse response = client.execute(put);
        int stateCode = response.getStatusLine().getStatusCode();
        StringBuilder sb = new StringBuilder();
        if (stateCode == HttpStatus.SC_OK) {
            HttpEntity result = response.getEntity();
            if (result != null) {
                InputStream is = result.getContent();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String tempLine;
                while ((tempLine = br.readLine()) != null) {
                    sb.append(tempLine);
                }
            }
        }
        //post.abort();
        DebugUtils.d("httpUtils", "doPost jsonString=" + sb.toString());
        return sb.toString();
    }


    /**
     * 以POST方式提交map数据数据
     * param url
     * param param
     * param file
     * return
     * throws Exception
     */
    public static String doMapPut(String url,Map<String, String> param) throws Exception {
        HttpPut put = new HttpPut(url);
        String head = DictionaryUtil.read("JSESSIONID");
        put.setHeader("Cookie", "JSESSIONID=" + head);
        HttpClient client = new DefaultHttpClient();
        DebugUtils.d("httpUtils", "doPost url=" + url);
        if (param.size() > 0) {
            List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>(param.size());
            Set<String> keys = param.keySet();
            for (String key : keys) {
                nameValuePairs.add(new BasicNameValuePair(key, String.valueOf(param.get(key))));
                DebugUtils.d("httpUtils key=" + key, "doPost value=" + String.valueOf(param.get(key)));
            }
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairs, "utf-8");
            put.setEntity(entity);
        }

        HttpResponse response = client.execute(put);
        int stateCode = response.getStatusLine().getStatusCode();
        StringBuilder sb = new StringBuilder();
        if (stateCode == HttpStatus.SC_OK) {
            HttpEntity result = response.getEntity();
            if (result != null) {
                InputStream is = result.getContent();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String tempLine;
                while ((tempLine = br.readLine()) != null) {
                    sb.append(tempLine);
                }
            }
        }
        //post.abort();
        DebugUtils.d("httpUtils", "doPost jsonString=" + sb.toString());
        return sb.toString();
    }


    /**
     * 以PUT方式提交修改文件
     * param url
     * param param
     * param file
     * return
     * throws Exception
     */
    public static String doPutFile(String url, String path)
            throws Exception {
        HttpPut httpPut = new HttpPut(url);
        String head = DictionaryUtil.read("JSESSIONID");
        httpPut.setHeader("Cookie", "JSESSIONID=" + head);
        HttpClient client = new DefaultHttpClient();
        DebugUtils.d("httpUtils", "doPut url=" + url);

        // 添加文件参数
        if (path != null) {
            MultipartEntity entity = new MultipartEntity();
            File file = new File(path);
            if (file.exists()) {
                entity.addPart(String.valueOf(file), new FileBody(file));
            }
            httpPut.setEntity(entity);
        }

        HttpResponse response = client.execute(httpPut);
        int stateCode = response.getStatusLine().getStatusCode();
        StringBuilder sb = new StringBuilder();
        if (stateCode == HttpStatus.SC_OK) {
            HttpEntity result = response.getEntity();
            if (result != null) {
                InputStream is = result.getContent();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String tempLine;
                while ((tempLine = br.readLine()) != null) {
                    sb.append(tempLine);
                }
            }
        }
        //post.abort();
        DebugUtils.d("httpUtils", "doPut jsonString=" + sb.toString());
        return sb.toString();
    }

    /**
     * desc: 以put方式提交Json数据做修改操作
     * auther:jiely
     * create at 2015/12/1 13:02
     */
    public static String doJsonPut(String url, String json) throws Exception {
        String response;
        int timeoutConnection = 6000;
        int timeoutSocket = 8000;
        DebugUtils.d("httpUtils", "doJsonPut url=" + url);
        HttpParams httpParameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
        HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
        HttpClient httpClient = new DefaultHttpClient(httpParameters);
        HttpPut httpPut = new HttpPut(url);
        String head = DictionaryUtil.read("JSESSIONID");
        httpPut.setHeader("Cookie", "JSESSIONID=" + head);
        httpPut.setEntity(new StringEntity(json, "UTF-8"));
        HttpResponse httpResponse = httpClient.execute(httpPut);
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        if (statusCode == HttpStatus.SC_OK) {
            response = EntityUtils.toString(httpResponse.getEntity());
        } else {
            response = String.valueOf(statusCode);
        }
        DebugUtils.d("httpUtils", "doJsonPut jsonString=" + response);
        return response;
    }


    /**
     * 以delete方式提交删除
     * param url
     * param param
     * param file
     * return
     * throws Exception
     */
    public static String doDelete(String url) throws Exception {
        HttpDelete httpDelete = new HttpDelete(url);
        HttpClient client = new DefaultHttpClient();
        String head = DictionaryUtil.read("JSESSIONID");
        httpDelete.setHeader("Cookie", "JSESSIONID=" + head);
        DebugUtils.d("httpUtils", "doDelete url=" + url);

        HttpResponse response = client.execute(httpDelete);
        int stateCode = response.getStatusLine().getStatusCode();
        StringBuffer sb = new StringBuffer();
        if (stateCode == HttpStatus.SC_OK) {
            String jsessionid = DictionaryUtil.read("JSESSIONID");
            httpDelete.setHeader("Cookie", "JSESSIONID=" + jsessionid);
            HttpEntity result = response.getEntity();
            if (result != null) {
                InputStream is = result.getContent();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String tempLine;
                while ((tempLine = br.readLine()) != null) {
                    sb.append(tempLine);
                }
            }
        }
        httpDelete.abort();
        DebugUtils.d("httpUtils", "doPost jsonString=" + sb.toString());
        return sb.toString();
    }

    /**
     * 2015-5-5
     * author jiely
     * since JDK 1.6
     */
    public static String doPostAsyn(final String urlPath) {
        String strResult = "";
        try {
            HttpClient httpClient = new DefaultHttpClient();
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 15000);
            HttpPost post = new HttpPost(urlPath);

            DebugUtils.d("httpUtils", "urlPath=" + urlPath);

            HttpResponse response = httpClient.execute(post);
            int stateCode = response.getStatusLine().getStatusCode();
            if (stateCode == HttpStatus.SC_OK) {
                // 请求结束，返回结果
                strResult = EntityUtils.toString(response.getEntity());
                DebugUtils.d("httpUtils", "jsonContent=" + strResult);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return strResult;
    }



    /**
     * 以POST方式提交表单和文件
     * param url
     * param param
     * param file
     * return
     * throws Exception
     */
    public static String doMapPost(String url, Map<String, String> param)
            throws Exception {
        HttpPost post = new HttpPost(url);
        String head = DictionaryUtil.read("JSESSIONID");
        post.setHeader("Cookie", "JSESSIONID=" + head);
        HttpClient client = new DefaultHttpClient();
        DebugUtils.d("httpUtils", "doPost url=" + url);
        
        if (param.size() > 0) {
            List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>(param.size());
            Set<String> keys = param.keySet();
            for (String key : keys) {
                nameValuePairs.add(new BasicNameValuePair(key, String.valueOf(param.get(key))));
                DebugUtils.d("httpUtils key=" + key, "doPost value=" + String.valueOf(param.get(key)));
            }
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairs, "utf-8");
            post.setEntity(entity);
        }
        
        HttpResponse response = client.execute(post);
        int stateCode = response.getStatusLine().getStatusCode();
        StringBuffer sb = new StringBuffer();
        if (stateCode == HttpStatus.SC_OK) {
            HttpEntity result = response.getEntity();
            if (result != null) {
                InputStream is = result.getContent();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String tempLine;
                while ((tempLine = br.readLine()) != null) {
                    sb.append(tempLine);
                }
            }
        }
        post.abort();
        DebugUtils.d("httpUtils", "doPost jsonString=" + sb.toString());
        return sb.toString();
    }

    /**
     * desc: 以post方式提交Json数据到服务器
     * auther:jiely
     * create at 2015/12/1 13:02
     */
    public static String doJsonPost(String url, String json) throws Exception {
        String result;
        int timeoutConnection = 6000;
        int timeoutSocket = 8000;
        DebugUtils.d("httpUtils", "doJsonPost url=" + url);
        DebugUtils.d("httpUtils", "doJsonPost json=" + json);
        HttpParams httpParameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
        HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
        HttpClient httpClient = new DefaultHttpClient(httpParameters);

        HttpPost httpPost = new HttpPost(url);
        String head = DictionaryUtil.read("JSESSIONID");
        httpPost.setHeader("Cookie", "JSESSIONID=" + head);

        StringEntity reqEntity = new StringEntity(json, "utf-8");
        reqEntity.setContentEncoding("UTF-8");
        reqEntity.setContentType("application/json");
        //reqEntity.setContentType("application/x-www-form-urlencoded");
        httpPost.setEntity(reqEntity);
        HttpResponse httpResponse = httpClient.execute(httpPost);
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        if (statusCode == HttpStatus.SC_OK) {
            result = EntityUtils.toString(httpResponse.getEntity());
        } else {
            result = String.valueOf(statusCode);
        }
        DebugUtils.d("httpUtils", "doJsonPost jsonString=" + result);
        return result;
    }

}
