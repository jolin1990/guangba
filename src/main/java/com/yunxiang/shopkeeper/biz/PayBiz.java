package com.yunxiang.shopkeeper.biz;

import android.os.Handler;
import android.os.Message;
import android.util.Log;


import com.yunxiang.shopkeeper.TApplication;
import com.yunxiang.shopkeeper.utils.Const;
import com.yunxiang.shopkeeper.utils.HttpUtils;
import com.yunxiang.shopkeeper.utils.JsonUtils;
import com.yunxiang.shopkeeper.utils.NetUtil;

import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;


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
        final List<BasicNameValuePair> list=new ArrayList<BasicNameValuePair>();
        list.add(new BasicNameValuePair("amount",amount));
        list.add(new BasicNameValuePair("subject","测试"));
//        list.add(new BasicNameValuePair("couponId","9"));
        list.add(new BasicNameValuePair("body","支付宝测试"));
        list.add(new BasicNameValuePair("channel", TApplication.CHANNEL));

        list.add(new BasicNameValuePair("client_ip", NetUtil.getLocalIpAddress(TApplication.context)));
        new Thread(){
            @Override
            public void run() {
                String url= Const.URL_RECHARGE;
                String result = HttpUtils.doPostAsyn(url, list);
                Log.d("TAG","result="+result);
//                //解析charge ,用于支付成功后修改订单状态
               TApplication.charge= JsonUtils.getCharge(result);
                Message message = Message.obtain();
                message.what=Const.MSG_SUCCESS;
                message.obj=result;
                handler.sendMessage(message);
            }

        }.start();
    };
    //支付成功修改订单状态
    public void upDataOrderStatus(final Handler handler){

        new Thread(){
            @Override
            public void run() {
                String url=Const.URL_UPDATA_RECHARGE;
                List<BasicNameValuePair> list=new ArrayList<BasicNameValuePair>();
                list.add(new BasicNameValuePair("id",TApplication.charge.getOrderNo()));
                String result = HttpUtils.doPostAsyn(url, list);
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
