package com.yunxiang.shopkeeper.biz;

import android.os.Handler;
import android.os.Message;

import com.yunxiang.shopkeeper.TApplication;
import com.yunxiang.shopkeeper.model.PacketBatch;
import com.yunxiang.shopkeeper.utils.Const;
import com.yunxiang.shopkeeper.utils.HttpUtils;
import com.yunxiang.shopkeeper.utils.JsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * desc: Created by yunxiang on 2015/10/30.
 * auther:jiely
 * create at 2015/11/17 15:39
 */
public class PacketBiz {
    private static PacketBiz packetBiz;

    public static PacketBiz getInstance() {
        if (packetBiz == null) {
            packetBiz = new PacketBiz();
        }
        return packetBiz;
    }

    private String getParketJson(PacketBatch packetBatch){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", packetBatch.getName());
            jsonObject.put("type", packetBatch.getType()+"");
            jsonObject.put("description", packetBatch.getDesc());
            jsonObject.put("balance", packetBatch.getAmountMoney());
            jsonObject.put("count", packetBatch.getTotalCount());
            jsonObject.put("startDate", packetBatch.getStartDate());
            jsonObject.put("stopDate", packetBatch.getStopDate());
            jsonObject.put("limit", packetBatch.getMoneyLimit());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  jsonObject.toString();
    }

    public void insertPacket(final PacketBatch packetBatch, final Handler handler) {
        new Thread() {
            @Override
            public void run() {
                String url;
                if("CustomerListActivity".equals(TApplication.map.get(Const.MAP_KEY_ACITIVITY))){
                     url = Const.URL_SEND_PRIVATE_PARKET;
                }else {
                     url = Const.URL_SEND_PARKET;
                }

                try {
                    //String jsonString = getParketJson(packetBatch);
                    Map<String,String> map = new HashMap<String,String>();
                    map.put("name", packetBatch.getName());
                    int type = packetBatch.getType();
                    map.put("type", String.valueOf(type));
                    map.put("description", packetBatch.getDesc());
                    map.put("balance", packetBatch.getAmountMoney());
                    map.put("count", packetBatch.getTotalCount());
                    map.put("startDate", packetBatch.getStartDate());
                    map.put("stopDate", packetBatch.getStopDate());
                    map.put("limit", packetBatch.getMoneyLimit());
                    map.put("customerIdList", packetBatch.getCustomerIdList());
                   //String result = HttpUtils.doJsonPost(url, jsonString);
                    String result = HttpUtils.doMapPost(url, map);
                    Boolean status = JsonUtils.getBooleanStatus(result);
                    if(status == null){
                        return;
                    }
                    String desc = JsonUtils.getDescription(result);
                    Message message = handler.obtainMessage();
                    message.obj = desc;
                    if (status) {
                        message.what = Const.MSG_SUCCESS;
                        if(type == Const.PACKET_COMMON || type == Const.PACKET_FIGHT_LUCK){
                            selectAll(true);
                        }else {
                            selectAll(false);
                        }
                    } else {
                        message.what = Const.MSG_FAILURE;
                    }
                    handler.sendMessage(message);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //查询红包列表 Packets
    public void selectPacketAll(final Handler handler) {
        new Thread() {
            @Override
            public void run() {
                Boolean status = selectAll(true);
                if(status){
                    Message message = handler.obtainMessage();
                    message.what = Const.MSG_SUCCESS;
                    handler.sendMessage(message);
                }
            }
        }.start();
    }

    //查询优惠券列表
    public void selectCouponAll(final Handler handler) {
        new Thread() {
            @Override
            public void run() {
                Boolean status = selectAll(false);
                if(status){
                    Message message = handler.obtainMessage();
                    message.what = Const.MSG_SUCCESS;
                    handler.sendMessage(message);
                }
            }
        }.start();
    }

    /**
     * desc: 获取所有红包批次
     * auther:jiely
     * create at 2015/12/1
     */
    private Boolean selectAll(boolean isPocket) {
        //long shopId = TApplication.shop.getShopId();
        String url = Const.URL_GET_PACKET_RECORD;
        StringBuilder builder = new StringBuilder();
        builder.append(url);
        if (isPocket) {
            builder.append("?type=0");
        }
        url = builder.toString();
        String result =  HttpUtils.doGetAsyn(url);
        Boolean status = JsonUtils.getStatus(result);
        if(status){
            if(isPocket){
                TApplication.packetBatchList = JsonUtils.getPacketBatchList(result);
            }else {
                TApplication.couponBatchList = JsonUtils.getPacketBatchList(result);
            }
        }
        return status;
    }

    //查询已领取红包列表 Packets
    public void selectReceivePacketByBatch(final PacketBatch batch,final Handler handler) {
        new Thread() {
            @Override
            public void run() {
                String url = Const.URL_GET_RECEIVE_PACKET_BY_BATCH+batch.getPatchId();
                String result = HttpUtils.doGetAsyn(url);
                boolean status = JsonUtils.getStatus(result);
                if(status){
                    Message message = handler.obtainMessage();
                    message.obj = JsonUtils.getPacketList(result);
                    message.what = Const.MSG_RECEIVE_PACKET_SUCCESS;
                    handler.sendMessage(message);
                }
            }
        }.start();
    }

    //查询已领取红包列表 Packets
    public void selectUsedPacketByBatch(final PacketBatch batch,final Handler handler) {
        new Thread() {
            @Override
            public void run() {
                String url = Const.URL_GET_USED_PACKET_BY_BATCH+batch.getPatchId();
                String result = HttpUtils.doGetAsyn(url);
                boolean status = JsonUtils.getStatus(result);
                if(status){
                    Message message = handler.obtainMessage();
                    message.obj = JsonUtils.getPacketList(result);
                    message.what = Const.MSG_USED_PACKET_SUCCESS;
                    handler.sendMessage(message);
                }
            }
        }.start();
    }


}
