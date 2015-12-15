package com.yunxiang.shopkeeper.biz;


import android.os.Handler;
import android.os.Message;

import com.yunxiang.shopkeeper.TApplication;
import com.yunxiang.shopkeeper.model.Customer;
import com.yunxiang.shopkeeper.utils.Const;
import com.yunxiang.shopkeeper.utils.HttpUtils;
import com.yunxiang.shopkeeper.utils.JsonUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class CustomerBiz {
    private static CustomerBiz customerBiz;
    public static CustomerBiz  getInstance(){
        if (customerBiz==null){
            customerBiz=new CustomerBiz();
        }
        return customerBiz;
    }


    //客户列表或者消息列表
    public void selectAllShopCustomers(final Handler handler){
        new Thread() {
            @Override
            public void run() {
                String url = Const.URL_GET_CUSTOMERS;
                String jsonString = HttpUtils.doGetAsyn(url);
                Message message = handler.obtainMessage();
                boolean status = JsonUtils.getStatus(jsonString);
                if(status){
                    TApplication.shopCustomerList = JsonUtils.getShopCustomerList(jsonString);
                    message.what = Const.MSG_SUCCESS;
                    handler.sendMessage(message);
                }
            }
        }.start();
    }

   //用户订单
    public void visitUserOrder(final String customerId,final Handler handler){
        new Thread(){
            @Override
            public void run() {
                Map<String,Object> map=new HashMap<String,Object>();
                String url=Const.URL_USER_ORDER+customerId;
                StringBuilder stringBuilder=new StringBuilder(url);
                stringBuilder.append("&shopId=").append(TApplication.shop.getShopId());
                url=stringBuilder.toString();
                String result = HttpUtils.doGetAsyn(url);
                Customer customer = JsonUtils.getCustomer(result);

                map.put("totalMoney", customer.getTotalMoney());
                map.put("phoneNumber", customer.getPhoneNumber());
                map.put("name", customer.getName());
                map.put("imgUrl", customer.getImgUrl());

                List<Customer> list = JsonUtils.getCustomers(result);
                map.put("orderList", list);
                Message message = Message.obtain();
                message.what=Const.MSG_SUCCESS;
                message.obj=map;
                handler.sendMessage(message);
            }
        }.start();
    }

}
