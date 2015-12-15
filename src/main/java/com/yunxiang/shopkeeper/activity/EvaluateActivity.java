package com.yunxiang.shopkeeper.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yunxiang.shopkeeper.R;
import com.yunxiang.shopkeeper.TApplication;
import com.yunxiang.shopkeeper.base.BaseTitle;
import com.yunxiang.shopkeeper.biz.ImageBiz;
import com.yunxiang.shopkeeper.biz.UserMessageBiz;
import com.yunxiang.shopkeeper.model.UserMessage;
import com.yunxiang.shopkeeper.utils.Const;

/**
 * desc:评价管理
 * auther:jiely
 * create at 2015/12/8 18:27
 */
public class EvaluateActivity extends Activity {

    private int count;

    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_leave);

        addView();

        BaseTitle.getInstance().setTitle(this, "评价管理");

        if(TApplication.versionType == Const.TEST_VERTION){
            count = 20;
            setListView();
        }else {
            if(TApplication.userMessageList == null){
                Handler.Callback callback = new MessageCallback();
                Handler handler = new Handler(callback);
                UserMessageBiz.getInstance().selectAll(handler);
            }else {
                setListView();
            }
        }
    }

    private void addView() {
        listview = (ListView)findViewById(R.id.list);

    }

    private void setListView(){
        MyAdapter adapter = new MyAdapter();
        listview.setAdapter(adapter);
    }

    class MyAdapter extends BaseAdapter {

        private LayoutInflater inflater;

        public MyAdapter() {
            super();
            inflater = LayoutInflater.from(EvaluateActivity.this);
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
                convertView = inflater.inflate(R.layout.item_leave_message, parent, false);
                viewHolder.imgHead = (ImageView)convertView.findViewById(R.id.img_item);
                viewHolder.imgReply = (ImageView)convertView.findViewById(R.id.img_reply);
                viewHolder.txtMessage = (TextView) convertView.findViewById(R.id.txt_content);
                viewHolder.txtTime = (TextView) convertView.findViewById(R.id.txt_time);
                viewHolder.txtCustomer = (TextView) convertView.findViewById(R.id.txt_customer_msg);
                viewHolder.txtRepay = (TextView) convertView.findViewById(R.id.txt_reply);
                viewHolder.lineStar = (LinearLayout)convertView.findViewById(R.id.line_star);

                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder)convertView.getTag();
            }

            viewHolder.lineStar.setVisibility(View.VISIBLE);

            if(TApplication.versionType == Const.TEST_VERTION){
                return convertView;
            }
            UserMessage userMessage = TApplication.userMessageList.get(position);

            ImageBiz imageBiz = new ImageBiz(false);
            imageBiz.execute(userMessage.getImgId(), viewHolder.imgHead);
            viewHolder.txtTime.setText(userMessage.getCreateDate());
            viewHolder.txtMessage.setText(userMessage.getContent());
            viewHolder.txtCustomer.setText(userMessage.getCustomerName());
            if(userMessage.getMessageReply().length() == 0){
                viewHolder.txtRepay.setVisibility(View.GONE);
            }else {
                viewHolder.txtRepay.setVisibility(View.VISIBLE);
                String strs = TApplication.shop.getShopName()+":"+userMessage.getMessageReply();
                SpannableStringBuilder style=new SpannableStringBuilder(strs);
                style.setSpan(new BackgroundColorSpan(0x0017cbdb), 0, TApplication.shop.getShopName().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                viewHolder.txtRepay.setText(style);
            }

            return convertView;
        }
    }

    //     ViewHolder 模式, 效率提高 50%
    static class ViewHolder {
        TextView txtMessage,txtTime,txtCustomer,txtRepay;
        ImageView imgHead,imgReply;
        LinearLayout lineStar;
    }

    class MessageCallback implements Handler.Callback{
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what){
                case Const.MSG_SUCCESS:
                    setListView();
                    break;
                case Const.MSG_FAILURE:
                    break;
            }
            return true;
        }
    }

}
