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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunxiang.shopkeeper.R;
import com.yunxiang.shopkeeper.TApplication;
import com.yunxiang.shopkeeper.base.BaseTitle;
import com.yunxiang.shopkeeper.biz.PacketBiz;
import com.yunxiang.shopkeeper.model.PacketBatch;
import com.yunxiang.shopkeeper.utils.Const;
import com.yunxiang.shopkeeper.utils.StringUtils;

import java.util.ArrayList;

public class CouponListActivity extends Activity {

    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_list);
        BaseTitle.getInstance().setTitle(this, "记录");
        if(TApplication.versionType == Const.TEST_VERTION){
            if(TApplication.couponBatchList == null){
                TApplication.couponBatchList = new ArrayList<PacketBatch>();
            }
            count = 20;
            for(int i=0; i<20; i++){
                PacketBatch batch = new PacketBatch();
                batch.setIsTimeOut(false);
                batch.setType(3);
                if(i>10){
                    batch.setType(2);
                    batch.setIsTimeOut(true);
                }
                TApplication.couponBatchList.add(batch);
            }
            setListView();
        }else {
            if(TApplication.couponBatchList == null){
                Handler.Callback callback = new CouponCallback();
                Handler handler = new Handler(callback);
                PacketBiz.getInstance().selectCouponAll(handler);
            }else {
                setListView();
            }

        }

    }

    private void setListView(){
        ListView listView = (ListView)findViewById(R.id.list);
        MyAdapter adapter = new MyAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("position",position);
                intent.setClass(CouponListActivity.this,CouponInfoActivity.class);
                startActivity(intent);
            }
        });
    }

    class MyAdapter extends BaseAdapter {

        private LayoutInflater inflater;

        public MyAdapter() {
            super();
            inflater = LayoutInflater.from(CouponListActivity.this);
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
                convertView = inflater.inflate(R.layout.item_coupon_record, parent, false);
                viewHolder.rlS = (RelativeLayout)convertView.findViewById(R.id.rl_s);
                viewHolder.rlN = (RelativeLayout)convertView.findViewById(R.id.rl_n);
                viewHolder.txtMoneyS = (TextView) convertView.findViewById(R.id.txt_money_s);
                viewHolder.txtNameS = (TextView)convertView.findViewById(R.id.txt_name_s);
                viewHolder.txtDateS = (TextView)convertView.findViewById(R.id.txt_date_s);
                viewHolder.txtCountS = (TextView)convertView.findViewById(R.id.txt_used_num_s);
                viewHolder.txtAmountS = (TextView)convertView.findViewById(R.id.txt_amount_s);
                viewHolder.txtLimitS = (TextView)convertView.findViewById(R.id.txt_limit_s);
                viewHolder.txtEndS = (TextView)convertView.findViewById(R.id.txt_ended_s);

                viewHolder.txtMoneyN = (TextView) convertView.findViewById(R.id.txt_money_n);
                viewHolder.txtNameN = (TextView)convertView.findViewById(R.id.txt_name_n);
                viewHolder.txtDateN = (TextView)convertView.findViewById(R.id.txt_date_n);
                viewHolder.txtCountN = (TextView)convertView.findViewById(R.id.txt_used_num_n);
                viewHolder.txtAmountN = (TextView)convertView.findViewById(R.id.txt_amount_n);
                viewHolder.txtLimitN = (TextView)convertView.findViewById(R.id.txt_limit_n);
                viewHolder.txtEndN = (TextView)convertView.findViewById(R.id.txt_ended_n);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder)convertView.getTag();
            }
            if(TApplication.versionType == Const.TEST_VERTION){
                return convertView;
            }
            PacketBatch packetBatch = TApplication.couponBatchList.get(position);
            String amount = packetBatch.getAmountMoney();
            String name;
            switch (packetBatch.getType()){
                case 0:
                    name = "红包";
                    break;
                case 1:
                    name = "红包";
                    break;
                case 2:
                    name = "折扣券";
                    break;
                case 3:
                    name = "抵用券";
                    break;
                default:
                    name = "红包";
                    break;
            }
            //String name = packetBatch.getType();
            String createDate = packetBatch.getCreateDate();
            String date = StringUtils.getYMD(createDate);
            String amountCount = packetBatch.getTotalCount();
            String receiveCount = packetBatch.getReceiveCount();
            String limit = packetBatch.getMoneyLimit();

            if(packetBatch.isTimeOut()){//过期
                viewHolder.rlS.setVisibility(View.GONE);
                viewHolder.rlN.setVisibility(View.VISIBLE);
                viewHolder.txtMoneyN.setText(String.valueOf("￥" + amount));
                viewHolder.txtNameN.setText(name);
                viewHolder.txtDateN.setText(date);
                viewHolder.txtCountN.setText(String.valueOf(receiveCount+"已领取"));
                viewHolder.txtAmountN.setText(String.valueOf("/"+amountCount));
                viewHolder.txtLimitN.setText(String.valueOf("满"+limit+"元可领取"));
                if(amountCount.equals(receiveCount)){
                    viewHolder.txtEndN.setText("已领完");
                }else {
                    viewHolder.txtEndN.setText("未领完");
                }
            }else {
                viewHolder.rlS.setVisibility(View.VISIBLE);
                viewHolder.rlN.setVisibility(View.GONE);
                viewHolder.txtMoneyS.setText(String.valueOf("￥"+amount));
                viewHolder.txtNameS.setText(name);
                viewHolder.txtDateS.setText(date);
                viewHolder.txtCountS.setText(String.valueOf(receiveCount+"(已领取)"));
                viewHolder.txtAmountS.setText(String.valueOf("/"+amountCount));
                viewHolder.txtLimitS.setText(String.valueOf("满"+limit+"元可领取"));
                if(amountCount.equals(receiveCount)){
                    viewHolder.txtEndS.setText("已领完");
                }
            }
            return convertView;
        }
    }

    //     ViewHolder 模式, 效率提高 50%
    static class ViewHolder {
        TextView txtNameS,txtMoneyS,txtDateS,txtCountS,txtAmountS,txtLimitS,txtEndS;
        TextView txtNameN,txtMoneyN,txtDateN,txtCountN,txtAmountN,txtLimitN,txtEndN;
        RelativeLayout rlS,rlN;
    }

    class CouponCallback implements Handler.Callback{
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what){
                case Const.MSG_SUCCESS:
                    count = TApplication.couponBatchList.size();
                    setListView();
                    break;
                case Const.MSG_FAILURE:
                    break;
            }
            return true;
        }
    }
}
