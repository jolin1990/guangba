package com.yunxiang.shopkeeper.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yunxiang.shopkeeper.R;
import com.yunxiang.shopkeeper.TApplication;
import com.yunxiang.shopkeeper.biz.ImageBiz;
import com.yunxiang.shopkeeper.biz.OrderBiz;
import com.yunxiang.shopkeeper.model.Address;
import com.yunxiang.shopkeeper.model.Order;
import com.yunxiang.shopkeeper.utils.AndroidUtils;
import com.yunxiang.shopkeeper.utils.Const;
import com.yunxiang.shopkeeper.utils.DebugUtils;

import java.util.List;

public class OrderActivity extends Activity implements View.OnClickListener{

    //private List<Order> orders;
    private TextView rdTxt1,rdTxt2,rdTxt3,rdTxt4,rdLine1,rdLine2,rdLine3,rdLine4;
    private LinearLayout line1,line2,line3,line4;
    private Order order;//订单
    private List<Order> orders;
    private int status = 0;//订单状态
    private ListView listView;
    private Handler.Callback callback;
    private  Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        if(TApplication.versionType == Const.TEST_VERTION){

        }
        addView();
        addListener();
        findOrderDate();

    }

    private void findOrderDate() {

        if(callback == null){
            callback = new OrderCallback();
            handler = new Handler(callback);
        }
        if(TApplication.orderArrays.get(0) == null){
            OrderBiz.getInstance().selectOrdersByStatus("0", handler);
        }else {
            orders = TApplication.orderArrays.get(0);
            setOrderListView();
        }
    }

    private void addListener() {
        line1.setOnClickListener(this);
        line2.setOnClickListener(this);
        line3.setOnClickListener(this);
        line4.setOnClickListener(this);
    }

    private void addView() {
        listView = (ListView) findViewById(R.id.list);
        line1 = (LinearLayout)findViewById(R.id.line1);
        line2 = (LinearLayout)findViewById(R.id.line2);
        line3 = (LinearLayout)findViewById(R.id.line3);
        line4 = (LinearLayout)findViewById(R.id.line4);

        rdTxt1 = (TextView)findViewById(R.id.rd_txt1);
        rdTxt2 = (TextView)findViewById(R.id.rd_txt2);
        rdTxt3 = (TextView)findViewById(R.id.rd_txt3);
        rdTxt4 = (TextView)findViewById(R.id.rd_txt4);
        rdLine1 = (TextView)findViewById(R.id.rd_line1);
        rdLine2 = (TextView)findViewById(R.id.rd_line2);
        rdLine3 = (TextView)findViewById(R.id.rd_line3);
        rdLine4 = (TextView)findViewById(R.id.rd_line4);

        ImageView imgBack = (ImageView)findViewById(R.id.img_left);
        ImageView imgSetting = (ImageView)findViewById(R.id.img_right);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        imgSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(OrderActivity.this,DeliverActivity.class);
                startActivity(intent);
            }
        });
    }

    private void  setOrderListView(){
        OrderAdapter adapter = new OrderAdapter(this);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                TApplication.order = order;
                intent.setClass(OrderActivity.this,OrderItemActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.line1:
                if(status != 0){
                    status = 0;
                    setMenu(1);
                    orders = TApplication.orderArrays.get(0);
                    if(orders == null){
                        OrderBiz.getInstance().selectOrdersByStatus("0", handler);
                    }else {
                        setOrderListView();
                    }
                }
                break;
            case R.id.line2:
                if(status != 2){
                    status = 2;
                    setMenu(2);
                    orders = TApplication.orderArrays.get(2);
                    if(orders == null){
                        OrderBiz.getInstance().selectOrdersByStatus("2", handler);
                    }else {
                        setOrderListView();
                    }
                }
                break;
            case R.id.line3:
                if(status != 4){
                    status = 4;
                    setMenu(4);
                    orders = TApplication.orderArrays.get(4);
                    if(orders == null){
                        OrderBiz.getInstance().selectOrdersByStatus("4", handler);
                    }else {
                        setOrderListView();
                    }
                }
                break;
            case R.id.line4:
                if(status != 6){
                    status = 6;
                    setMenu(8);
                    orders = TApplication.orderArrays.get(6);
                    if(orders == null){
                        OrderBiz.getInstance().selectOrdersByStatus("6", handler);
                    }else {
                        setOrderListView();
                    }
                }
                break;
        }
    }

    /**
     * desc:
     * auther:jiely  处理 新订单 已接单 已完成 已取消菜单的切换
     * create at 2015/11/27 10:00
     */
    private void setMenu(int mode){
        if((mode & 0x01) == 0x01){
            rdTxt1.setTextColor(getResources().getColor(R.color.sky_blue));
            rdLine1.setBackgroundColor(getResources().getColor(R.color.sky_blue));
        }else{
            rdTxt1.setTextColor(getResources().getColor(R.color.black_text_n));
            rdLine1.setBackgroundColor(getResources().getColor(R.color.white));
        }

        if((mode & 0x02) == 0x02){
            rdTxt2.setTextColor(getResources().getColor(R.color.sky_blue));
            rdLine2.setBackgroundColor(getResources().getColor(R.color.sky_blue));
        }else{
            rdTxt2.setTextColor(getResources().getColor(R.color.black_text_n));
            rdLine2.setBackgroundColor(getResources().getColor(R.color.white));
        }

        if((mode & 0x04) == 0x04){
            rdTxt3.setTextColor(getResources().getColor(R.color.sky_blue));
            rdLine3.setBackgroundColor(getResources().getColor(R.color.sky_blue));
        }else{
            rdTxt3.setTextColor(getResources().getColor(R.color.black_text_n));
            rdLine3.setBackgroundColor(getResources().getColor(R.color.white));
        }

        if((mode & 0x08) == 0x08){
            rdTxt4.setTextColor(getResources().getColor(R.color.sky_blue));
            rdLine4.setBackgroundColor(getResources().getColor(R.color.sky_blue));
        }else{
            rdTxt4.setTextColor(getResources().getColor(R.color.black_text_n));
            rdLine4.setBackgroundColor(getResources().getColor(R.color.white));
        }

    }

    class OrderAdapter extends BaseAdapter {

        private LayoutInflater inflater;

        public OrderAdapter(Context context) {
            super();
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return orders == null ? 0:orders.size();
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
                convertView = inflater.inflate(R.layout.item_order, parent, false);
                viewHolder.txtMoney = (TextView) convertView.findViewById(R.id.txt_money);
                viewHolder.imageView = (ImageView) convertView.findViewById(R.id.img_head);
                viewHolder.txtName  = (TextView) convertView.findViewById(R.id.txt_name);
                viewHolder.txtReceiveName = (TextView) convertView.findViewById(R.id.txt_receive_name);
                viewHolder.txtdate = (TextView) convertView.findViewById(R.id.txt_date);
                viewHolder.txtPhone = (TextView) convertView.findViewById(R.id.txt_phone);
                viewHolder.txtAddress = (TextView) convertView.findViewById(R.id.txt_address);
                viewHolder.txtRefuse = (TextView) convertView.findViewById(R.id.txt_no_receive);//拒接
                viewHolder.txtReceiveOrder = (TextView) convertView.findViewById(R.id.txt_receive);//接单
                viewHolder.listView1 = (ListView)convertView.findViewById(R.id.list);
                viewHolder.btnCall = (Button) convertView.findViewById(R.id.btn_call_receiver);//联系顾客
                viewHolder.txtHasSent = (TextView) convertView.findViewById(R.id.txt_has_sent);//已送货等待客户确认
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder)convertView.getTag();
            }

            order = orders.get(position);
            viewHolder.txtMoney.setText(String.valueOf(order.getPrice()));
            ImageBiz imageBiz = new ImageBiz(false);
            imageBiz.execute(order.getImgUrl(), viewHolder.imageView);
            viewHolder.txtName.setText(order.getCustomerName());

            viewHolder.txtdate.setText(order.getCreatDate());
            Address address = order.getAddress();
            if(address != null){
                String name = "收货人："+address.getName();
                viewHolder.txtReceiveName.setText(name);
                String phone = "电 话："+address.getPhone();
                viewHolder.txtPhone.setText(phone);
                String street = "地 址："+address.getStreet();
                viewHolder.txtAddress.setText(street);
            }else {
                viewHolder.txtReceiveName.setText("收货人：");
                viewHolder.txtPhone.setText("电 话：");
                viewHolder.txtAddress.setText("电 话：");
            }

            viewHolder.txtHasSent.setText("");
            viewHolder.btnCall.setVisibility(View.GONE);
            viewHolder.txtRefuse.setVisibility(View.GONE);
            viewHolder.txtReceiveOrder.setVisibility(View.GONE);
            DebugUtils.d("orderActivity","order.getStatus()="+order.getStatus());
            switch (order.getStatus()){
                case 0:
                case 1://新订单
                    viewHolder.txtRefuse.setVisibility(View.VISIBLE);
                    viewHolder.txtRefuse.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {//拒单
                            setRefuseDialog();
                        }
                    });
                    viewHolder.txtReceiveOrder.setVisibility(View.VISIBLE);
                    viewHolder.txtReceiveOrder.setText("接单");
                    viewHolder.txtReceiveOrder.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            setReceiveDialog();
                        }
                    });
                    break;
                case 2://已接单
                case 3:
                    viewHolder.btnCall.setVisibility(View.VISIBLE);
                    viewHolder.btnCall.setOnClickListener(new View.OnClickListener() {//打电话
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + viewHolder.txtPhone.getText()));
                            //通知activtity处理传入的call服务
                            OrderActivity.this.startActivity(intent);
                        }
                    });
                    viewHolder.txtReceiveOrder.setVisibility(View.VISIBLE);
                    viewHolder.txtReceiveOrder.setText("送货");
                    viewHolder.txtReceiveOrder.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            order.setStatus(5);
                            OrderBiz.getInstance().insertDeliverOrder(order, handler);
                        }
                    });
                    break;
                case 4://送货中
                    viewHolder.txtHasSent.setText("已送货，等待顾客确认");
                    break;
                case 5://已完成
                    viewHolder.txtHasSent.setText("已完成");
                    break;
                case 6:
                case 7://已取消
                    break;
            }

            return convertView;
        }
    }

    //     ViewHolder 模式, 效率提高 50%
    static class ViewHolder {
        TextView txtMoney,txtReceiveName,txtName,txtdate,txtPhone,txtAddress;
        TextView txtRefuse,txtReceiveOrder,txtHasSent;
        ImageView imageView;
        ListView listView1;
        Button btnCall;
    }

    /**
     * desc: 设置拒接的对话框
     * auther:jiely
     * create at 2015/11/27 14:28
     */
    private void setRefuseDialog(){
        final Dialog dialog = new Dialog(OrderActivity.this);
        LayoutInflater li=(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        View dialogView = li.inflate(R.layout.dialog_order_refuse, null);
        dialog.setContentView(dialogView);
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        lp.x = 0; // 新位置X坐标
        lp.y = 100; // 新位置Y坐标
        dialogWindow.setAttributes(lp);
        dialog.show();
        final TextView txtReason1 = (TextView)dialogView.findViewById(R.id.txt_reason1);
        final TextView txtReason2 = (TextView)dialogView.findViewById(R.id.txt_reason2);
        final TextView txtReason3 = (TextView)dialogView.findViewById(R.id.txt_reason3);
        TextView txtCancel = (TextView)dialogView.findViewById(R.id.txt_cancel);
        txtReason1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order.setStatus(7);
                OrderBiz.getInstance().insertRefuse(txtReason1.getText().toString(), order, handler);
                dialog.dismiss();
            }
        });
        txtReason2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order.setStatus(7);
                OrderBiz.getInstance().insertRefuse(txtReason2.getText().toString(), order, handler);
                dialog.dismiss();
            }
        });
        txtReason3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order.setStatus(7);
                OrderBiz.getInstance().insertRefuse(txtReason3.getText().toString(), order, handler);
                dialog.dismiss();
            }
        });
        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    /**
     * desc: 设置接单的对话框
     * auther:jiely
     * create at 2015/11/27 14:28
     */
    private void setReceiveDialog(){
        final Dialog dialog = new Dialog(OrderActivity.this);
        LayoutInflater li=(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        View dialogView = li.inflate(R.layout.dialog_order_receive, null);
        dialog.setContentView(dialogView);
        Window dialogWindow = dialog.getWindow();
       // WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        dialog.show();

        TextView txtSure = (TextView)dialogView.findViewById(R.id.txt_sure);
        TextView txtCancel = (TextView)dialogView.findViewById(R.id.txt_cancel);

        txtSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order.setStatus(3);
                OrderBiz.getInstance().insertReiveOrder(order,handler);
                dialog.dismiss();
            }
        });
        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    class OrderCallback implements Handler.Callback{
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what){
                case Const.MSG_SUCCESS:
                    orders = TApplication.orderArrays.get(status);
                    setOrderListView();
                    break;
                case Const.MSG_ORDER_DELIVER_SUCCESS://送货
                    setMenu(4);
                    orders = TApplication.orderArrays.get(4);
                    setOrderListView();
                    break;
                case Const.MSG_ORDER_RECEIVE_SUCCESS://接单
                    setMenu(2);
                    orders = TApplication.orderArrays.get(2);
                    setOrderListView();
                    break;
                case Const.MSG_ORDER_REFUSE_SUCCESS://拒单
                    setMenu(8);
                    orders = TApplication.orderArrays.get(6);
                    setOrderListView();
                    break;
                case Const.MSG_FAILURE:
                    AndroidUtils.show(OrderActivity.this,message.obj.toString());
                    break;
            }
            return true;
        }
    }
}
