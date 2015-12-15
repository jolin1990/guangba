package com.yunxiang.shopkeeper.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.yunxiang.shopkeeper.R;
import com.yunxiang.shopkeeper.TApplication;
import com.yunxiang.shopkeeper.base.BaseTitle;
import com.yunxiang.shopkeeper.utils.Const;

public class PayRecordActivity extends Activity {

    private int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_record);

        if(TApplication.versionType == Const.TEST_VERTION){
            BaseTitle.getInstance().setTitle(this, "小花花花姑凉");
            addListview();
            count = 20;
        }

    }

    private void addListview(){
        ListView listView = (ListView)findViewById(R.id.list);
        MyAdapter adapter = new MyAdapter();
        listView.setAdapter(adapter);
    }


    class MyAdapter extends BaseAdapter {

        private LayoutInflater inflater;

        public MyAdapter() {
            super();
            inflater = LayoutInflater.from(PayRecordActivity.this);
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
                convertView = inflater.inflate(R.layout.item_consume_record, parent, false);
                viewHolder.txtMoney = (TextView) convertView.findViewById(R.id.txt_money);

                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder)convertView.getTag();
            }
            return convertView;
        }
    }

    //     ViewHolder 模式, 效率提高 50%
    static class ViewHolder {
        TextView txtMoney,txtValid,txtName;
        Button btnUse;
    }
}
