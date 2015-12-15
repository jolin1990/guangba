package com.yunxiang.shopkeeper.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.yunxiang.shopkeeper.utils.Const;

public class CustomerManageActivity extends Activity implements View.OnClickListener{
    private int count = 20;
    private Button btnToMsg;
    private TextView txtMy,txtMsg;
    private View vLeft,vRight;
    private ListView listView;
    private boolean isMy = true;

    private BaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_manage);

        BaseTitle.getInstance().setTitle(this, "客户管理");

        addView();

        addListener();

        if(TApplication.versionType == Const.TEST_VERTION){
            addListView();
        }

    }

    private void addListener() {
        txtMy.setOnClickListener(this);
        txtMsg.setOnClickListener(this);
        btnToMsg.setOnClickListener(this);
    }

    private void addView() {
        btnToMsg = (Button)findViewById(R.id.btn_to_msg);
        txtMy = (TextView)findViewById(R.id.txt_my);
        txtMsg = (TextView)findViewById(R.id.txt_msg);
        listView = (ListView)findViewById(R.id.list);
        vLeft = findViewById(R.id.line_left);
        vRight = findViewById(R.id.line_right);

        vLeft.setVisibility(View.VISIBLE);
        vRight.setVisibility(View.GONE);
    }

    private void  addListView(){
        if(isMy){
            adapter = new CustomerAdapter();
        }else {
            adapter = new MsgAdapter();
        }
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_to_msg:
                Intent intent = new Intent();
                intent.setClass(CustomerManageActivity.this,MessageSendActivity.class);
                startActivity(intent);
                break;
            case R.id.txt_my:
                if(!isMy){
                    isMy = true;
                    txtMy.setTextColor(getResources().getColor(R.color.sky_blue));
                    txtMsg.setTextColor(getResources().getColor(R.color.black_text_n));
                    vLeft.setVisibility(View.VISIBLE);
                    vRight.setVisibility(View.GONE);
                    if(TApplication.versionType == Const.TEST_VERTION){
                        addListView();
                    }
                }

                break;
            case R.id.txt_msg:
                if(isMy){
                    isMy = false;
                    txtMy.setTextColor(getResources().getColor(R.color.black_text_n));
                    txtMsg.setTextColor(getResources().getColor(R.color.sky_blue));
                    vLeft.setVisibility(View.GONE);
                    vRight.setVisibility(View.VISIBLE);
                    if(TApplication.versionType == Const.TEST_VERTION){
                        addListView();
                    }
                }


                break;
        }
    }

    class CustomerAdapter extends BaseAdapter {

        private LayoutInflater inflater;

        public CustomerAdapter() {
            super();
            inflater = LayoutInflater.from(CustomerManageActivity.this);
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
            final ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = inflater.inflate(R.layout.item_customer_select, parent, false);
                viewHolder.txtMoney = (TextView) convertView.findViewById(R.id.txt_money);
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
            viewHolder.checkBox.setVisibility(View.GONE);

            return convertView;
        }
    }

    //     ViewHolder 模式, 效率提高 50%
    static class ViewHolder {
        TextView txtMoney,txtUptime,txtUpUpTime,txtName,txtCount,txtSend,txtTotal;
        CheckBox checkBox;
    }


    class MsgAdapter extends BaseAdapter {

        private LayoutInflater inflater;

        public MsgAdapter() {
            super();
            inflater = LayoutInflater.from(CustomerManageActivity.this);
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
            ViewHolder viewHolder2;
            if (convertView == null) {
                viewHolder2 = new ViewHolder();
                convertView = inflater.inflate(R.layout.item_message, parent, false);

                convertView.setTag(viewHolder2);
            }else {
                viewHolder2 = (ViewHolder)convertView.getTag();
            }
            return convertView;
        }
    }

    //     ViewHolder 模式, 效率提高 50%
    static class ViewHolder2 {
        TextView txtMoney,txtValid,txtName;
        Button btnUse;
    }

}
