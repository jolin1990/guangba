package com.yunxiang.shopkeeper.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.yunxiang.shopkeeper.R;
import com.yunxiang.shopkeeper.TApplication;
import com.yunxiang.shopkeeper.base.BaseTitle;
import com.yunxiang.shopkeeper.utils.Const;

public class SendInfoActivity extends Activity {

    private ImageView imgSend;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_info);
        if(TApplication.versionType == Const.TEST_VERTION){

        }
        addTitle();
        addView();
        addListView();
    }

    private void addView() {
        imgSend = (ImageView)findViewById(R.id.img_send);
        listView = (ListView)findViewById(R.id.list);
    }

    private void addListView(){
        MyAdapter adapter = new MyAdapter();
        listView.setAdapter(adapter);
    }

    private void addTitle() {
        BaseTitle.getInstance().setTitle(this, "发消息");
    }

    class MyAdapter extends BaseAdapter {

        private LayoutInflater inflater;

        public MyAdapter() {
            super();
            inflater = LayoutInflater.from(SendInfoActivity.this);
        }

        @Override
        public int getCount() {

            return 10;
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
                v = inflater.inflate(R.layout.item_message, parent, false);
            }
            return v;
        }
    }
}
