package com.yunxiang.shopkeeper.biz;

import android.os.Handler;
import android.os.Message;

import com.yunxiang.shopkeeper.TApplication;
import com.yunxiang.shopkeeper.model.Order;
import com.yunxiang.shopkeeper.utils.Const;
import com.yunxiang.shopkeeper.utils.HttpUtils;
import com.yunxiang.shopkeeper.utils.JsonUtils;

import java.util.List;

/**
 * desc: Created by jiely on 2015/9/9.
 */
public class UserMessageBiz {
    private static UserMessageBiz instance;

    public static UserMessageBiz getInstance() {
        if (instance == null) {
            instance = new UserMessageBiz();
        }
        return instance;
    }

    //获取商店的所有用户留言
    public void selectAll(final Handler handler) {
        new Thread() {
            @Override
            public void run() {
                String url = Const.URL_USER_MESSAGE_LIST;
                Message message = handler.obtainMessage();
                String jsonString = HttpUtils.doGetAsyn(url);
                boolean status = JsonUtils.getStatus(jsonString);
                if(status){
                    TApplication.userMessageList = JsonUtils.getUserMessages(jsonString);
                    message.what = Const.MSG_SUCCESS;
                    handler.sendMessage(message);
                }
            }
        }.start();
    }

    //根据订单类型获得订单列表
    public void selectOrdersByStatus(final String type, final Handler handler) {
        new Thread() {
            @Override
            public void run() {
                Message message = handler.obtainMessage();
                getOrdersByStatus(type);
                message.what = Const.MSG_SUCCESS;
                handler.sendMessage(message);
            }
        }.start();
    }


    /**
     * desc: 根据订单类型得到订单列表
     * auther:jiely
     * create at 2015/11/27 10:43
     */
    private List<Order> getOrdersByStatus(String type){
        String url = Const.URL_SHOP_ORDERS;
        url = url + "1" + "&orderStatus=" + type;
        String jsonString = HttpUtils.doGetAsyn(url);
        boolean status = JsonUtils.getStatus(jsonString);
        List<Order> orders = null;
        if(status){
            int orderStatus = Integer.parseInt(type);
            //orderStatus /= 10;
            orders = JsonUtils.getShopOrders(jsonString);
            TApplication.orderArrays.append(orderStatus,JsonUtils.getShopOrders(jsonString));
        }
        return orders;
    }

    //提交订单拒接的原因
    public void insertRefuse(final String reason, final Order order, final Handler handler) {
        new Thread() {
            @Override
            public void run() {
                //Map<String, Object> map = new HashMap<String, Object>();
                String url = Const.URL_ORDER_REFUSE;
                long orderId = order.getOrderId();
                StringBuilder builder = new StringBuilder(url);
                builder.append(TApplication.shop.getShopId())
                        .append("&orderId=").append(orderId)
                        .append("&reason=").append(reason);
                Message message = handler.obtainMessage();
                url = builder.toString();
                String jsonString = HttpUtils.doPostAsyn(url);
                boolean status = JsonUtils.getStatus(jsonString);
                String desc = JsonUtils.getDescription(jsonString);
                message.obj = desc;
                if(status){
                    //取消订单列表中新增，新订单列表中移除
                    List<Order> orders = TApplication.orderArrays.get(6);
                    if(orders == null){//取消的订单
                        orders = getOrdersByStatus("6");
                        TApplication.orderArrays.append(6,orders);
                    }
                    TApplication.orderArrays.get(6).add(order);
                    TApplication.orderArrays.get(0).remove(order);
                    message.what = Const.MSG_ORDER_REFUSE_SUCCESS;
                    handler.sendMessage(message);
                }else {
                    message.what = Const.MSG_FAILURE;
                }
                handler.sendMessage(message);

            }
        }.start();
    }

    //提交接单信息
    public void insertReiveOrder(final Order order, final Handler handler) {
        new Thread() {
            @Override
            public void run() {
                String url = Const.URL_ORDER_RECEIVE;
                long orderId = order.getOrderId();
                StringBuilder builder = new StringBuilder(url);
                builder.append(TApplication.shop.getShopId())
                        .append("&orderId=").append(orderId);

                Message message = handler.obtainMessage();
                url = builder.toString();
                String jsonString = HttpUtils.doPostAsyn(url);
                boolean status = JsonUtils.getStatus(jsonString);
                String desc = JsonUtils.getDescription(jsonString);
                message.obj = desc;
                if(status){
                    //接单成功后 已接单列表中新增，新订单移除
                    List<Order> orders = TApplication.orderArrays.get(2);
                    if(orders == null){
                        orders = getOrdersByStatus("23");
                        TApplication.orderArrays.append(2,orders);
                    }
                    TApplication.orderArrays.get(2).add(order);//已接单列表中新增
                    TApplication.orderArrays.get(0).remove(order);//新订单移除
                    message.what = Const.MSG_ORDER_RECEIVE_SUCCESS;
                    handler.sendMessage(message);
                }else {
                    message.what = Const.MSG_FAILURE;
                }
                handler.sendMessage(message);
            }
        }.start();
    }


    //提交送货状态
    public void insertDeliverOrder(final Order order, final Handler handler) {
        new Thread() {
            @Override
            public void run() {
                String url = Const.URL_ORDER_DELIVER;
                long orderId = order.getOrderId();
                StringBuilder builder = new StringBuilder(url);
                builder.append(TApplication.shop.getShopId())
                        .append("&orderId=").append(orderId);
                Message message = handler.obtainMessage();
                url = builder.toString();
                String jsonString = HttpUtils.doPostAsyn(url);
                boolean status = JsonUtils.getStatus(jsonString);
                String desc = JsonUtils.getDescription(jsonString);
                message.obj = desc;
                if(status){
                    //点击送货后后 完成列表中新增，已接单列表中移除
                    List<Order> orders = TApplication.orderArrays.get(2);
                    if(orders == null){
                        orders = getOrdersByStatus("23");
                        TApplication.orderArrays.append(2,orders);
                    }
                    TApplication.orderArrays.get(4).add(order);//完成列表中新增
                    TApplication.orderArrays.get(2).remove(order);//已接单列表中移除
                    message.what = Const.MSG_ORDER_DELIVER_SUCCESS;
                    handler.sendMessage(message);
                }else {
                    message.what = Const.MSG_FAILURE;
                }
                handler.sendMessage(message);
            }
        }.start();
    }

}
