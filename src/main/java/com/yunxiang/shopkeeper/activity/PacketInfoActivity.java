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

/**
 * desc: 点击单个红包记录
 * auther:jiely
 * create at 2015/10/27 15:29
 */
public class PacketInfoActivity extends Activity {

    private int count;
    private PacketBatch packetBatch;
    private List<Packet> packets;//使用红包记录

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packet_info);
        if (TApplication.versionType == Const.TEST_VERTION) {
            BaseTitle.getInstance().setTitle(this, "中秋钜惠红包");
            if (TApplication.packets == null) {
                TApplication.packets = new ArrayList<Packet>();
            }
            count = 20;
            for (int i = 0; i < 20; i++) {
                Packet packet = new Packet();
                TApplication.packets.add(packet);
            }
            addListView();
        } else {
            setView();
            if (packets == null) {
                Handler.Callback callback = new PacketCallback();
                Handler handler = new Handler(callback);
                PacketBiz.getInstance().selectReceivePacketByBatch(packetBatch, handler);
            } else {
                count = packets.size();
                addListView();
            }
        }

    }

    /**
     * desc: 设置批次页面
     * auther:jiely
     * create at 2015/11/30 11:37
     */
    private void setView() {
        TextView txtAmount1 = (TextView) findViewById(R.id.txt_amount1);
        TextView txtUsedCount = (TextView) findViewById(R.id.txt_used_num);
        TextView txtAmount2 = (TextView) findViewById(R.id.txt_amount2);
        TextView txtDate = (TextView) findViewById(R.id.txt_date);
        TextView txtName = (TextView) findViewById(R.id.txt_name);

        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);
        packetBatch = TApplication.couponBatchList.get(position);

        String name = packetBatch.getName();
        BaseTitle.getInstance().setTitle(this, name);

        txtDate.setText(packetBatch.getCreateDate());
        String text = packetBatch.getAmountMoney() + "元";
        txtAmount1.setText(text);
        txtName.setText(packetBatch.getName());
        txtUsedCount.setText(packetBatch.getReceiveCount());
        text = "/" + packetBatch.getTotalCount() + "个";
        txtAmount2.setText(text);
    }

    private void addListView() {
        ListView listview = (ListView) findViewById(R.id.list);
        MyAdapter adapter = new MyAdapter();
        listview.setAdapter(adapter);
    }


    class MyAdapter extends BaseAdapter {

        private LayoutInflater inflater;

        public MyAdapter() {
            super();
            inflater = LayoutInflater.from(PacketInfoActivity.this);
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
                convertView = inflater.inflate(R.layout.item_accept_pocket, parent, false);
                viewHolder.txtMoney = (TextView) convertView.findViewById(R.id.txt_money);
                viewHolder.txtDate = (TextView) convertView.findViewById(R.id.txt_date);
                viewHolder.txtName = (TextView) convertView.findViewById(R.id.txt_name);
                viewHolder.imageView = (ImageView)convertView.findViewById(R.id.img_item);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder)convertView.getTag();
            }


            if(TApplication.versionType == Const.TEST_VERTION){
                return convertView;
            }
            Packet packet = packets.get(position);
            viewHolder.txtDate.setText(packet.getUpdateTime());
            viewHolder.txtName.setText(packet.getCustomerName());
            viewHolder.txtMoney.setText(packet.getMoney());
            ImageBiz imageBiz = new ImageBiz(false);
            imageBiz.execute(Const.URL_PICTURE_PATH+packet.getHeadImgId(), viewHolder.imageView);
            return convertView;
        }
    }

    //     ViewHolder 模式, 效率提高 50%
    static class ViewHolder {
        TextView txtDate,txtMoney,txtName;
        ImageView imageView;
    }

    class PacketCallback implements Handler.Callback {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case Const.MSG_RECEIVE_PACKET_SUCCESS:
                    packets = (List<Packet>)message.obj;
                    count = packets.size();
                    addListView();
                    break;
                case Const.MSG_FAILURE:
                    break;
            }
            return true;
        }
    }

}
