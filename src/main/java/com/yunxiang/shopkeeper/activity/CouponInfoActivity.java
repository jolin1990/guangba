package com.yunxiang.shopkeeper.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yunxiang.shopkeeper.R;
import com.yunxiang.shopkeeper.TApplication;
import com.yunxiang.shopkeeper.base.BaseTitle;
import com.yunxiang.shopkeeper.biz.ImageBiz;
import com.yunxiang.shopkeeper.biz.PacketBiz;
import com.yunxiang.shopkeeper.model.Packet;
import com.yunxiang.shopkeeper.model.PacketBatch;
import com.yunxiang.shopkeeper.utils.Const;

import java.util.ArrayList;
import java.util.List;

public class CouponInfoActivity extends Activity implements View.OnClickListener {
    private int count;
    private TextView txtLeft, txtRight;
    private boolean isLeft = true;
    private ListView listView;
    private MyAdapter adapter1, adapter2;
    private TextView txtMoney, txtDesc, txtDate1, txtDate2, txtName, txtUsed, txtAmount;
    private PacketBatch packetBatch;
    private List<Packet> receivePackets;//领取红包记录
    private List<Packet> usedPackets;//使用红包记录
    private List<Packet> packets;//使用红包记录

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_info);
        BaseTitle.getInstance().setTitle(this, "优惠券");

        addView();
        addListener();
        packets = new ArrayList<Packet>();

        if (TApplication.versionType == Const.TEST_VERTION) {
            count = 20;
            setAdapter();
        } else {
            setView();
            if (receivePackets == null) {
                Handler.Callback callback = new CouponCallback();
                Handler handler = new Handler(callback);
                PacketBiz.getInstance().selectReceivePacketByBatch(packetBatch, handler);
            } else {
                count = receivePackets.size();
                packets.clear();
                packets.addAll(receivePackets);
                setAdapter();
            }
        }
    }

    private void setView() {
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);
        packetBatch = TApplication.couponBatchList.get(position);
        txtMoney.setText(String.valueOf("￥" + packetBatch.getAmountMoney()));
        txtDesc.setText(packetBatch.getDesc());
        txtDate1.setText(packetBatch.getStartDate());
        txtDate2.setText(packetBatch.getStopDate());
        txtName.setText(packetBatch.getName());
        txtUsed.setText(packetBatch.getReceiveCount());
        txtAmount.setText(String.valueOf("/" + packetBatch.getTotalCount() + "张"));
        txtLeft.setBackgroundColor(getResources().getColor(R.color.white));
        txtRight.setBackgroundColor(getResources().getColor(R.color.gray_light_n));
    }

    private void addView() {
        txtLeft = (TextView) findViewById(R.id.txt_left);
        txtRight = (TextView) findViewById(R.id.txt_right);
        listView = (ListView) findViewById(R.id.list);
        txtMoney = (TextView) findViewById(R.id.txt_money);
        txtDesc = (TextView) findViewById(R.id.txt_info);
        txtDate1 = (TextView) findViewById(R.id.txt_date1);
        txtDate2 = (TextView) findViewById(R.id.txt_date2);
        txtName = (TextView) findViewById(R.id.txt_name);
        txtUsed = (TextView) findViewById(R.id.txt_used);
        txtAmount = (TextView) findViewById(R.id.txt_amount);
    }

    private void addListener() {
        txtLeft.setOnClickListener(this);
        txtRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_left:
                if (!isLeft) {//领取
                    isLeft = true;
                    txtLeft.setBackgroundColor(getResources().getColor(R.color.white));
                    txtRight.setBackgroundColor(getResources().getColor(R.color.gray_light_n));
                    if(TApplication.versionType == Const.TEST_VERTION){
                        setAdapter();
                        break;
                    }
                    if(receivePackets == null){
                        Handler.Callback callback = new CouponCallback();
                        Handler handler = new Handler(callback);
                        PacketBiz.getInstance().selectReceivePacketByBatch(packetBatch, handler);
                    }else {
                        count = receivePackets.size();
                        packets.clear();
                        packets.addAll(receivePackets);
                        setAdapter();
                    }
                }
                break;
            case R.id.txt_right://使用记录
                if (isLeft) {
                    isLeft = false;
                    txtLeft.setBackgroundColor(getResources().getColor(R.color.gray_light_n));
                    txtRight.setBackgroundColor(getResources().getColor(R.color.white));
                    if(TApplication.versionType == Const.TEST_VERTION){
                        setAdapter();
                        break;
                    }
                    if(usedPackets == null){
                        Handler.Callback callback = new CouponCallback();
                        Handler handler = new Handler(callback);
                        PacketBiz.getInstance().selectUsedPacketByBatch(packetBatch, handler);
                    }else {
                        count = usedPackets.size();
                        packets.clear();
                        packets.addAll(usedPackets);
                        setAdapter();
                    }
                }
                break;
        }
    }

    class MyAdapter extends BaseAdapter {

        private LayoutInflater inflater;

        public MyAdapter() {
            super();
            inflater = LayoutInflater.from(CouponInfoActivity.this);
        }

        @Override
        public int getCount() {
            return count;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = inflater.inflate(R.layout.item_bond_record, parent, false);
                viewHolder.txtUse = (TextView) convertView.findViewById(R.id.txt_use);
                viewHolder.txtDate = (TextView) convertView.findViewById(R.id.txt_date);
                viewHolder.txtName = (TextView) convertView.findViewById(R.id.txt_name);
                viewHolder.imageView = (ImageView)convertView.findViewById(R.id.img_item);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder)convertView.getTag();
            }

            if (isLeft) {
                viewHolder.txtUse.setText("领取优惠券");
            } else {
                viewHolder.txtUse.setText("消费使用");
            }

            if(TApplication.versionType == Const.TEST_VERTION){
                return convertView;
            }
            try {
                Packet packet = packets.get(position);
                viewHolder.txtDate.setText(packet.getUpdateTime());
                viewHolder.txtName.setText(packet.getCustomerName());
                ImageBiz imageBiz = new ImageBiz(false);
                imageBiz.execute(packet.getHeadImgId(), viewHolder.imageView);
            }catch (Exception e){
                e.printStackTrace();
            }


            return convertView;
        }
    }

    //     ViewHolder 模式, 效率提高 50%
    static class ViewHolder {
        TextView txtDate,txtUse,txtName;
        ImageView imageView;
    }

    private void setAdapter() {
        if (isLeft) {
            if (adapter1 == null) {
                adapter1 = new MyAdapter();
            }
            listView.setAdapter(adapter1);
        } else {
            if (adapter2 == null) {
                adapter2 = new MyAdapter();
            }
            listView.setAdapter(adapter2);
        }
    }

    class CouponCallback implements Handler.Callback {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case Const.MSG_RECEIVE_PACKET_SUCCESS:
                    receivePackets = (List<Packet>)message.obj;
                    count = receivePackets.size();
                    packets.clear();
                    packets.addAll(receivePackets);
                    setAdapter();
                    break;
                case Const.MSG_USED_PACKET_SUCCESS:
                    usedPackets = (List<Packet>)message.obj;
                    count = usedPackets.size();
                    packets.clear();
                    packets.addAll(usedPackets);
                    setAdapter();
                    break;
                case Const.MSG_FAILURE:
                    break;
            }
            return true;
        }
    }
}
