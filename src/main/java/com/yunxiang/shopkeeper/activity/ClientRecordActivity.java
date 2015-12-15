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
import com.yunxiang.shopkeeper.biz.CustomerBiz;
import com.yunxiang.shopkeeper.model.Customer;
import com.yunxiang.shopkeeper.utils.Const;

import java.util.List;
import java.util.Map;

public class ClientRecordActivity extends Activity {
    private ImageView imgClient, imgVip;
    private TextView txtName, txtCount, txtTotalMoney,txtNumber;
    private ListView listView;
   private List<Customer> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_record);

        if(TApplication.versionType == Const.TEST_VERTION){

        }
        addTitle();
        addView();
        addData();
    }

    private void addData() {
        Intent intent = getIntent();
        int customerId = intent.getIntExtra("customerId", 0);
        CustomerOrderCallback callback = new CustomerOrderCallback();
        Handler handler = new Handler(callback);

        CustomerBiz.getInstance().visitUserOrder(String.valueOf(customerId), handler);
    }

    private void addView() {
        imgClient = (ImageView) findViewById(R.id.img_item);
        imgVip = (ImageView) findViewById(R.id.img_vip);
        txtName = (TextView) findViewById(R.id.txt_name);
        txtCount = (TextView) findViewById(R.id.txt_count);
        txtTotalMoney = (TextView) findViewById(R.id.txt_total_money);
        txtNumber= (TextView) findViewById(R.id.txt_number);
        listView = (ListView) findViewById(R.id.list);


    }

    private void addTitle() {
        BaseTitle.getInstance().setTitle(this, "小小花姑娘");

    }

    class MyAdapter extends BaseAdapter {

        private LayoutInflater inflater;

        public MyAdapter() {
            super();
            inflater = LayoutInflater.from(ClientRecordActivity.this);
        }

        @Override
        public int getCount() {
            return list!=null?list.size():0;
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
            if (v == null) {
                v = inflater.inflate(R.layout.item_client_record, parent, false);
                Hoder hoder=new Hoder();
                hoder.txtMoney= (TextView) v.findViewById(R.id.txt_money);
                hoder.txtOrderNumber= (TextView) v.findViewById(R.id.txt_sn);
                hoder.txtTime= (TextView) v.findViewById(R.id.txt_time);
                v.setTag(hoder);
            }
            Hoder hoder= (Hoder) v.getTag();
            hoder.txtTime.setText(list.get(position).getTime());
            hoder.txtOrderNumber.setText("流水号："+list.get(position).getOrderNumber());
            hoder.txtMoney.setText("消费总金额"+list.get(position).getMoney()+"元");

            if (position % 2 == 0) {
                v.setBackgroundColor(getResources().getColor(R.color.white));
            } else {
                v.setBackgroundColor(getResources().getColor(R.color.gray_light_n));
            }

            return v;
        }
        class Hoder{
            private TextView txtTime,txtOrderNumber,txtMoney;
        }
    }

    class CustomerOrderCallback implements Handler.Callback {

        @Override
        public boolean handleMessage(Message message) {
            if (message.what == Const.MSG_SUCCESS) {
                Map<String, Object> map = (Map<String, Object>) message.obj;
                list= (List<Customer>) map.get("orderList");

                String name= (String) map.get("name");
                String number= (String) map.get("phoneNumber");
                Double money= (Double) map.get("totalMoney");
                String imgUrl= (String) map.get("imgUrl");

                MyAdapter adapter = new MyAdapter();
                listView.setAdapter(adapter);

                if (list!=null){

                    txtCount.setText("消费次数："+list.size()+"次");
                }
                txtName.setText(name);
                txtNumber.setText(number);
                txtTotalMoney.setText("消费总额："+money+"元");
                ImageBiz imageBiz=new ImageBiz(false);
                imageBiz.execute(imgUrl,imgClient);
            }
            return true;
        }
    }

}
