package com.yunxiang.shopkeeper.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yunxiang.shopkeeper.R;
import com.yunxiang.shopkeeper.TApplication;
import com.yunxiang.shopkeeper.base.BaseTitle;
import com.yunxiang.shopkeeper.model.Address;
import com.yunxiang.shopkeeper.model.Merchandise;
import com.yunxiang.shopkeeper.model.Order;
import com.yunxiang.shopkeeper.utils.Const;
import com.yunxiang.shopkeeper.utils.DataUtils;

public class OrderItemActivity extends Activity implements View.OnClickListener{

    private Order order;
    private ListView listView;
    private TextView txtName,txtAddress,txtPhone,txtPrice;
    private TextView txtSendingDate,txtDate1,txtDate2,txtDate3,txtDate4;
    private TextView txtOrder1,txtOrder2,txtOrder3,txtOrder4;
    private Button btnEnd,btnCall;
    private LinearLayout lineEnd;
    private TextView txtRefuse,txtReceiveOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        order = TApplication.order;
        setContentView(R.layout.activity_order_item);
        if(TApplication.versionType == Const.TEST_VERTION){

        }
        addTitle();
        addView();
        addListener();
        setView();
        GoodsAdapter adapter = new GoodsAdapter(this);
        listView.setAdapter(adapter);
    }

    private void addListener() {
    }

    private void addView() {
        listView = (ListView)findViewById(R.id.list);
        listView.setVisibility(View.VISIBLE);
        txtName = (TextView)findViewById(R.id.txt_name);
        txtAddress = (TextView)findViewById(R.id.txt_address);
        txtPrice = (TextView)findViewById(R.id.txt_money);
        txtSendingDate = (TextView)findViewById(R.id.txt_sending_date);
        txtDate1 = (TextView)findViewById(R.id.txt_date1);
        txtDate2 = (TextView)findViewById(R.id.txt_date2);
        txtDate3 = (TextView)findViewById(R.id.txt_date3);
        txtDate4 = (TextView)findViewById(R.id.txt_date4);
        txtPhone = (TextView)findViewById(R.id.txt_phone);
        txtOrder1 = (TextView)findViewById(R.id.txt_order1);
        txtOrder2 = (TextView)findViewById(R.id.txt_order2);
        txtOrder3 = (TextView)findViewById(R.id.txt_order3);
        txtOrder4 = (TextView)findViewById(R.id.txt_order4);

        lineEnd = (LinearLayout)findViewById(R.id.line_end);
        btnEnd = (Button)findViewById(R.id.btn_end);  //完成 取消
        btnCall = (Button)findViewById(R.id.btn_call); //联系顾客
        txtRefuse = (TextView) findViewById(R.id.txt_no_receive);//拒接
        txtReceiveOrder = (TextView) findViewById(R.id.txt_receive);//接单 送货
    }

    private void setView(){
        Address address = order.getAddress();
        if(address != null){
            String name = "收货人："+address.getName();
            txtName.setText(name);
            String phone = "电 话："+address.getPhone();
            txtPhone.setText(phone);
            String street = "地 址："+address.getStreet();
            txtAddress.setText(street);
        }else {
            txtName.setText("收货人：");
            txtPhone.setText("电 话：");
            txtAddress.setText("地 址：");
        }

        txtPrice.setText(String.valueOf(order.getPrice()));
        txtSendingDate.setText(order.getSendingDate());
        txtDate1.setText(order.getCreatDate());
        txtOrder1.setText("下单");

        lineEnd.setVisibility(View.GONE);
        btnEnd.setVisibility(View.GONE);
        btnCall.setVisibility(View.GONE);
        txtReceiveOrder.setVisibility(View.GONE);

        switch (order.getStatus()){
            case 0:
            case 1://新订单
                lineEnd.setVisibility(View.VISIBLE);
                txtReceiveOrder.setVisibility(View.VISIBLE);
                txtReceiveOrder.setText("拒单");
                txtReceiveOrder.setText("接单");
                break;
            case 2://已接单
            case 3:
                lineEnd.setVisibility(View.VISIBLE);
                btnCall.setVisibility(View.VISIBLE);
                txtReceiveOrder.setText("送货");

                txtDate2.setText(order.getReceivingDate());
                txtOrder2.setText("接单");
                break;
            case 4://送货中
                btnEnd.setVisibility(View.VISIBLE);
                btnEnd.setText("送货中");
                txtDate2.setText(order.getReceivingDate());
                txtOrder2.setText("接单");
                txtDate3.setText(order.getDeliverDate());
                txtOrder3.setText("送货");
                break;
            case 5://已完成
                btnEnd.setVisibility(View.VISIBLE);
                btnEnd.setText("完成");

                txtDate2.setText(order.getReceivingDate());
                txtOrder2.setText("接单");
                txtDate3.setText(order.getDeliverDate());
                txtOrder3.setText("送货");
                txtDate4.setText(order.getFinishDate());
                txtOrder4.setText("客户已确认");
                break;
            case 6:
            case 7://已取消
                btnEnd.setVisibility(View.VISIBLE);
                btnEnd.setText("取消");

                txtDate2.setText(order.getFinishDate());
                txtOrder2.setText("取消");
                break;
        }

    }

    private void addTitle() {
        BaseTitle.getInstance().setTitle(this, "订单详情");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_no_receive://拒接

                break;
            case R.id.txt_receive://接单 送货
                break;
            case R.id.btn_call://联系顾客
                break;
        }
    }

    class GoodsAdapter extends BaseAdapter {

        private LayoutInflater inflater;

        public GoodsAdapter(Context context) {
            super();
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return order.getMerchandises() == null ? 0 :order.getMerchandises().size();
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
                convertView = inflater.inflate(R.layout.item_order_goods, parent, false);
                viewHolder.txtGoodsName = (TextView) convertView.findViewById(R.id.txt_name);
                viewHolder.txtNumber = (TextView) convertView.findViewById(R.id.txt_number);
                viewHolder.txtGoodsMoney = (TextView) convertView.findViewById(R.id.txt_money);

                //viewHolder.btnUse = (Button)convertView.findViewById(R.id.btn_use);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder)convertView.getTag();
            }

            Merchandise merchandise = order.getMerchandises().get(position);
            viewHolder.txtGoodsName.setText(merchandise.getName());
            viewHolder.txtNumber.setText(merchandise.getNumber());
            String money = DataUtils.doubleToStr(merchandise.getPrice());
            viewHolder.txtGoodsMoney.setText(money);
            return convertView;
        }
    }

    //     ViewHolder 模式, 效率提高 50%
    static class ViewHolder {
        TextView txtGoodsName,txtNumber,txtGoodsMoney;
    }
}
