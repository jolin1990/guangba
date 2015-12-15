package com.yunxiang.shopkeeper.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.yunxiang.shopkeeper.R;
import com.yunxiang.shopkeeper.TApplication;
import com.yunxiang.shopkeeper.base.BaseTitle;
import com.yunxiang.shopkeeper.biz.CustomerBiz;
import com.yunxiang.shopkeeper.model.ShopCustomer;
import com.yunxiang.shopkeeper.utils.AndroidUtils;
import com.yunxiang.shopkeeper.utils.Const;
import com.yunxiang.shopkeeper.utils.StringUtils;

import java.util.ArrayList;

public class CustomerListActivity extends Activity implements View.OnClickListener{

    private int cheekedCount = 0;
    private int count;
    private TextView txtHadSelected,txtSelectAll,txtSure;
    private MyAdapter adapter;
    private boolean isSelectAll; //是否全部选中
    //private List<ShopCustomer> customers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);
        TApplication.selectedCustomers.clear();
        BaseTitle.getInstance().setTitle(this, "选择顾客");
        addView();
        addListener();

        if(TApplication.versionType == Const.TEST_VERTION){
            if(TApplication.shopCustomerList == null){
                 TApplication.shopCustomerList = new ArrayList<ShopCustomer>();
            }else {
                TApplication.shopCustomerList.clear();
            }
            count = 20;
            for(int i=0; i<20; i++){
                ShopCustomer shopCustomer = new ShopCustomer();
                TApplication.shopCustomerList.add(shopCustomer);
            }
            setListView();

        }else {
            if(TApplication.shopCustomerList == null){
                Handler.Callback callback = new ShopCustomerCallback();
                Handler handler = new Handler(callback);
                CustomerBiz.getInstance().selectAllShopCustomers(handler);
            }else {
                count = TApplication.shopCustomerList.size();
                setListView();
            }
        }
    }


    private void addListener() {
        txtSelectAll.setOnClickListener(this);
        txtSure.setOnClickListener(this);
    }

    private void addView() {
        txtHadSelected = (TextView)findViewById(R.id.txt_had_selected);
        txtSelectAll = (TextView)findViewById(R.id.txt_select_all);
        txtSure = (TextView)findViewById(R.id.txt_sure);
    }

    private void setListView(){
        ListView listView = (ListView)findViewById(R.id.list);
        adapter = new MyAdapter();
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_sure:
                if(cheekedCount == 0){
                    AndroidUtils.show(this,"请选择");
                    break;
                }
                Intent intent = new Intent();
                if(TApplication.map.get(Const.MAP_KEY_ACITIVITY).equals(Const.MAP_VAL_PERSONAL_POCKET)){
                    intent.setClass(CustomerListActivity.this, PacketEditActivity.class);
                }else {
                    intent.setClass(CustomerListActivity.this, CouponEditActivity.class);
                }
                TApplication.map.put(Const.MAP_KEY_ACITIVITY,"CustomerListActivity");
                startActivity(intent);
                break;
            case R.id.txt_select_all:
                isSelectAll = !isSelectAll;
                TApplication.selectedCustomers.clear();
                if(isSelectAll){//点击全选后显示取消
                    TApplication.selectedCustomers.addAll(TApplication.shopCustomerList);
                    cheekedCount = count;
                    txtSelectAll.setText("取消");
                }else {//点击取消后显示全选
                    cheekedCount = 0;
                    txtSelectAll.setText("全选");
                }
                for (int i = 0; i < count; i++) {
                    adapter.getIsSelectedArray().append(i,isSelectAll);
                }
                adapter.notifyDataSetChanged();
                String text = "已选("+cheekedCount+")";
                txtHadSelected.setText(text);
                break;
        }
    }

    class MyAdapter extends BaseAdapter {

        private LayoutInflater inflater;

        // 用来控制CheckBox的选中状况
        private SparseBooleanArray isSelectedArray;

        public SparseBooleanArray getIsSelectedArray() {
            return isSelectedArray;
        }


        public MyAdapter() {
            super();
            inflater = LayoutInflater.from(CustomerListActivity.this);
            isSelectedArray = new SparseBooleanArray();
            initDate();
        }

        // 初始化isSelected的数据
        private void initDate() {
            for (int i = 0; i < count; i++) {
                getIsSelectedArray().append(i,false);
            }
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;

            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = inflater.inflate(R.layout.item_customer_select, parent, false);
                viewHolder.txtMoney = (TextView)convertView.findViewById(R.id.txt_money);
                viewHolder.txtName = (TextView)convertView.findViewById(R.id.txt_name);
                viewHolder.txtUptime = (TextView)convertView.findViewById(R.id.txt_up_time);
                viewHolder.txtUpUpTime = (TextView)convertView.findViewById(R.id.txt_up_up_time);
                viewHolder.txtCount = (TextView)convertView.findViewById(R.id.txt_count);
                viewHolder.txtSend = (TextView)convertView.findViewById(R.id.txt_send);
                viewHolder.txtTotal = (TextView)convertView.findViewById(R.id.txt_total);
                viewHolder.checkBox = (CheckBox)convertView.findViewById(R.id.checked);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder)convertView.getTag();
            }
            // 根据isSelected来设置checkbox的选中状况
            viewHolder.checkBox.setChecked(getIsSelectedArray().get(position));
            viewHolder.checkBox.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (viewHolder.checkBox.isChecked()) {
                        if (TApplication.shopCustomerList != null) {
                            TApplication.selectedCustomers.add(TApplication.shopCustomerList.get(position));
                        }
                        cheekedCount++;
                    } else {
                        if (TApplication.shopCustomerList != null) {
                            TApplication.selectedCustomers.remove(TApplication.shopCustomerList.get(position));
                        }
                        cheekedCount--;
                    }
                    String text = String.valueOf("已选(" + cheekedCount) + ")";
                    txtHadSelected.setText(text);
                }
            });

            if(TApplication.versionType == Const.TEST_VERTION){
                return convertView;
            }else {
                ShopCustomer customer = TApplication.shopCustomerList.get(position);
                viewHolder.txtMoney.setText(String.valueOf(customer.getConsumption()));
                viewHolder.txtName.setText(customer.getCustomerName());
                String upTime = StringUtils.getMD(customer.getUpdateTime());
                viewHolder.txtUptime.setText(upTime);
                upTime = StringUtils.getHM(customer.getCreateTime());
                viewHolder.txtUpUpTime.setText(upTime);
                viewHolder.txtCount.setText(String.valueOf("到店:"+customer.getTimes()+"次"));
                if(customer.isTakeOut()){
                    viewHolder.txtSend.setVisibility(View.VISIBLE);
                }else {
                    viewHolder.txtSend.setVisibility(View.GONE);
                }
               // viewHolder.txtTotal.setText(customer.getUpdateTime());
            }
            return convertView;
        }
    }

    //     ViewHolder 模式, 效率提高 50%
    static class ViewHolder {
        TextView txtMoney,txtUptime,txtUpUpTime,txtName,txtCount,txtSend,txtTotal;
        CheckBox checkBox;
    }


    class ShopCustomerCallback implements Handler.Callback{
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what){
                case Const.MSG_SUCCESS:
                    count = TApplication.shopCustomerList.size();
                    setListView();
                    break;
                case Const.MSG_FAILURE:
                    break;
            }
            return true;
        }
    }

}
