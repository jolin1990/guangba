package com.yunxiang.shopkeeper.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yunxiang.shopkeeper.R;
import com.yunxiang.shopkeeper.TApplication;
import com.yunxiang.shopkeeper.base.BaseTitle;
import com.yunxiang.shopkeeper.biz.PacketBiz;
import com.yunxiang.shopkeeper.model.PacketBatch;
import com.yunxiang.shopkeeper.utils.Const;

import java.util.ArrayList;

/**
 * desc: 红包和抵用券的记录 pocket Coupons
 * auther:jiely
 * create at 2015/11/27 16:13
 */
public class PacketRecordActivity extends Activity {

    private int count;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packet_recorder);
        addTitle();
        listView = (ListView) findViewById(R.id.list);
        if(TApplication.versionType == Const.TEST_VERTION){
            count = 20;
            if(TApplication.packetBatchList == null){
                TApplication.packetBatchList = new ArrayList<PacketBatch>();
            }
            for(int i=0; i<20; i++){
                PacketBatch batch = new PacketBatch();
                TApplication.packetBatchList.add(batch);
            }
            setListView();
        }


        addData();
    }

    private void addData() {
        if (TApplication.packetBatchList == null) {
            RecorderCallback callback = new RecorderCallback();
            Handler handler = new Handler(callback);
            PacketBiz.getInstance().selectPacketAll(handler);
        } else {
            count =  TApplication.packetBatchList.size();
            setListView();
        }
    }

    private void setListView() {
        MyAdapter adapter = new MyAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("position", position);
                intent.setClass(PacketRecordActivity.this, PacketInfoActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addTitle() {
        BaseTitle.getInstance().setTitle(this, "记录");
    }

    class MyAdapter extends BaseAdapter {
        private LayoutInflater inflater;

        public MyAdapter() {
            super();
            inflater = LayoutInflater.from(PacketRecordActivity.this);
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
        public View getView(int position, View v, ViewGroup parent) {
            Holder holder;
            if (v == null) {
                holder = new Holder();
                v = inflater.inflate(R.layout.item_pocket_record, parent, false);
                holder.txtAmount1 = (TextView) v.findViewById(R.id.txt_amount1);
                holder.txtUsedCount = (TextView) v.findViewById(R.id.txt_used_num);
                holder.txtAmount2 = (TextView) v.findViewById(R.id.txt_amount2);
                holder.txtDate = (TextView) v.findViewById(R.id.txt_date);
                holder.txtName = (TextView) v.findViewById(R.id.txt_name);
                v.setTag(holder);
            } else {
                holder = (Holder) v.getTag();
            }
            PacketBatch packetBatch = TApplication.packetBatchList.get(position);
            if(TApplication.versionType != Const.TEST_VERTION){
                holder.txtDate.setText(packetBatch.getCreateDate());
                String text = packetBatch.getAmountMoney() + "元";
                holder.txtAmount1.setText(text);
                holder.txtName.setText(packetBatch.getName());
                holder.txtUsedCount.setText(packetBatch.getReceiveCount());
                text = "/" + packetBatch.getTotalCount() + "个";
                holder.txtAmount2.setText(text);
            }

            return v;
        }

    }

    class Holder {
        private TextView txtName, txtDate, txtAmount1,txtAmount2,txtUsedCount;
    }


    class RecorderCallback implements Handler.Callback {

        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case Const.MSG_PACKET_SUCCESS:
                    count =  TApplication.packetBatchList.size();
                    setListView();
                    break;
            }
            return true;
        }
    }

}
