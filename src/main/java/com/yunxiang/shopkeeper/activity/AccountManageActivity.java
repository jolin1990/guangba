package com.yunxiang.shopkeeper.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yunxiang.shopkeeper.R;
import com.yunxiang.shopkeeper.TApplication;
import com.yunxiang.shopkeeper.base.BaseTitle;
import com.yunxiang.shopkeeper.biz.ImageBiz;
import com.yunxiang.shopkeeper.biz.OrderBiz;
import com.yunxiang.shopkeeper.model.Order;
import com.yunxiang.shopkeeper.utils.Const;
import com.yunxiang.shopkeeper.utils.DateUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AccountManageActivity extends Activity implements View.OnClickListener{
    private int count;
    private TextView txtDate,txtMoney;
    private Calendar cl;
    private ListView listView;
    private List<Order> orders;
    private String currentDate;//当前日期


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_manage);

        addView();
        if(TApplication.versionType == Const.TEST_VERTION){
            count = 20;
            orders = new ArrayList<Order>();
            BaseTitle.getInstance().setTitle(this, "渝香辣婆婆");
            for(int i=0; i<count; i++){
                Order order = new Order();
                orders.add(order);
            }
            addListView();
        }else {
            BaseTitle.getInstance().setTitle(this, TApplication.shop.getShopName());
            addData();
        }

        BaseTitle.getInstance().setTitle(this, TApplication.shop.getShopName());
        addListener();
    }

    private void addData() {
        if(TApplication.orderArrays.get(10) == null){
            OrderCallback callback=new OrderCallback();
            Handler handler=new Handler(callback);
            OrderBiz.getInstance().selectOrdersByDate(currentDate, handler);
        }else {
            orders = TApplication.orderArrays.get(10);
            count = orders.size();
        }

    }

    private void addListener() {
        txtDate.setOnClickListener(this);
    }

    private void addView() {
        txtDate = (TextView) findViewById(R.id.txt_date);
        listView = (ListView)findViewById(R.id.list);
        txtMoney= (TextView) findViewById(R.id.txt_money);
        cl = Calendar.getInstance();
        Date date = new Date();
        cl.setTime(date);
        //用于显示界面
        String text = (cl.get(Calendar.MONTH) + 1) + "月" + cl.get(Calendar.DAY_OF_MONTH) + "日";
        //用于传送到后台
        //currentDate=cl.get(Calendar.YEAR)+"-"+(cl.get(Calendar.MONTH) + 1) + "-" + cl.get(Calendar.DAY_OF_MONTH);
        currentDate = DateUtil.getFormatDate(date);
        txtDate.setText(text);
    }

    private void addListView() {
        MyAdapter adapter = new MyAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(AccountManageActivity.this,PayRecordActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_date:
                DatePickerDialog dialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) {
                                txtDate.setText((monthOfYear + 1 )+ "月" + dayOfMonth + "日");
                                currentDate=year+"-"+(monthOfYear + 1) + "-" + dayOfMonth;
                                addData();
                            }
                        }, cl.get(Calendar.YEAR)
                        , cl.get(Calendar.MONTH)
                        , cl.get(Calendar.DAY_OF_MONTH));
                dialog.show();
        }
    }

    class MyAdapter extends BaseAdapter {

        private LayoutInflater inflater;

        public MyAdapter() {
            super();
            inflater = LayoutInflater.from(AccountManageActivity.this);
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
            Hoder hoder;
            if (v == null) {
                v = inflater.inflate(R.layout.item_pay_record, parent, false);
                hoder=new Hoder();
                hoder.imgView= (ImageView) v.findViewById(R.id.img_item);
                hoder.txtSn= (TextView) v.findViewById(R.id.txt_sn);
                hoder.txtPrice= (TextView) v.findViewById(R.id.txt_money);
                hoder.txtTime= (TextView) v.findViewById(R.id.txt_time);
                hoder.txtUser= (TextView) v.findViewById(R.id.txt_user);
                v.setTag(hoder);
            }else {
                hoder= (Hoder) v.getTag();
            }

            if(TApplication.versionType == Const.TEST_VERTION){
               return v;
            }

            ImageBiz imageBiz=new ImageBiz(false);
            Order order = orders.get(position);
            imageBiz.execute(order.getImgUrl(), hoder.imgView);

            hoder.txtTime.setText(order.getFinishDate());
            hoder.txtPrice.setText(String.valueOf(order.getPrice()));
            String sn = "流水号"+order.getSerialNumber()+"("+order.getDiscount()+")";
            hoder.txtSn.setText(sn);
            hoder.txtUser.setText(order.getCustomerName());
            return v;
        }

    }

    class Hoder{
        private ImageView imgView;
        private TextView txtTime,txtPrice,txtSn,txtUser;
    }

    class OrderCallback implements Handler.Callback{
        @Override
        public boolean handleMessage(Message message) {
            if (message.what== Const.MSG_SUCCESS){
                txtMoney.setText(String.valueOf(TApplication.orderAmount+"元"));
                orders = TApplication.orderArrays.get(10);
                count = orders.size();
                addListView();
            }
            return true;
        }
    }

}
