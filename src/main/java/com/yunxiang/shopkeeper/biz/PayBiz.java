package com.yunxiang.shopkeeper.biz;

import android.os.Handler;
import android.os.Message;
import android.util.Log;


import com.yunxiang.shopkeeper.TApplication;
import com.yunxiang.shopkeeper.utils.Const;
import com.yunxiang.shopkeeper.utils.DebugUtils;
import com.yunxiang.shopkeeper.utils.HttpUtils;
import com.yunxiang.shopkeeper.utils.JsonUtils;
import com.yunxiang.shopkeeper.utils.NetUtil;

import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by yunxiang on 2015/11/16.
 */
public class PayBiz {
    private static PayBiz payBiz;
    public static PayBiz getInstance(){
        if (payBiz==null){
            payBiz=new PayBiz();
        }
            return payBiz;
    };
    //customerId  用户id shopId   商店id price  支付金额 couponId 红包或优惠卷Id 可选 subject   商品标题 body
    // 商品描述信息 channel   支付渠道
    //初始金额  originalPrice
    //最终金额  confirmPrice
//    alipay支付宝手机支付
//    wx 微信支付
//    client_ip  发起支付请求终端的 IP 地址

    public  void commitOrder(final Handler handler,String amount){
       final Map<String, String> param=new HashMap<String, String>();
        param.put("amount",amount);
        param.put("subject","测试");
        param.put("body","测试");
        param.put("channel", TApplication.CHANNEL);
        param.put("client_ip", NetUtil.getLocalIpAddress(TApplication.context));


//        final List<BasicNameValuePair> list=new ArrayList<BasicNameValuePair>();
//        list.add(new BasicNameValuePair("amount",amount));
//        list.add(new BasicNameValuePair("subject","测试"));
////        list.add(new BasicNameValuePair("couponId","9"));
//        list.add(new BasicNameValuePair("body","支付宝测试"));
//        list.add(new BasicNameValuePair("channel", TApplication.CHANNEL));
//
//        list.add(new BasicNameValuePair("client_ip", NetUtil.getLocalIpAddress(TApplication.context)));
        new Thread(){
            @Override
            public void run() {
                String url= Const.URL_RECHARGE;
                String result = null;
                try {
                    result = HttpUtils.doMapPost(url, param);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d("TAG","result="+result);
//                //解析charge ,用于支付成功后修改订单状态
                if (result!=null){
                    TApplication.charge= JsonUtils.getCharge(result);
                    Message message = Message.obtain();
                    message.what=Const.MSG_SUCCESS;
                    message.obj=result;
                    handler.sendMessage(message);
                }

            }

        }.start();
    };
    //支付成功修改订单状态
    public void upDataOrderStatus(final Handler handler){

        new Thread(){
            @Override
            public void run() {
                String url=Const.URL_UPDATA_RECHARGE;
                final Map<String, String> param=new HashMap<String, String>();
                param.put("id", TApplication.charge.getId());
                DebugUtils.d("TAG","id="+TApplication.charge.getId());
                String result = null;
                try {
                    result = HttpUtils.doMapPost(url, param);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d("TAG","result="+result);
                boolean status = JsonUtils.getStatus(result);
                Message message=Message.obtain();
                message.what=Const.MSG_SUCCESS;
                if (status){
                    handler.sendMessage(message);
                }
            }
        }.start();

    }
}
