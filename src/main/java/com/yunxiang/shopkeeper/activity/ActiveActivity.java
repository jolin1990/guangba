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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yunxiang.shopkeeper.R;
import com.yunxiang.shopkeeper.TApplication;
import com.yunxiang.shopkeeper.base.BaseTitle;
import com.yunxiang.shopkeeper.biz.ActiveBiz;
import com.yunxiang.shopkeeper.model.Active;
import com.yunxiang.shopkeeper.utils.Const;

import java.util.ArrayList;

public class ActiveActivity extends Activity {
    private int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active);

        BaseTitle.getInstance().setTitle(this, "店铺活动");

        if(TApplication.versionType == Const.TEST_VERTION){
            count = 20;
            if(TApplication.activeList == null){
                TApplication.activeList = new ArrayList<Active>();
            }
            for(int i=0; i<20; i++){
                Active active = new Active();
                TApplication.activeList.add(active);
            }
            setListView();
        }else {
            Handler.Callback callback = new ActiveCallback();
            Handler handler = new Handler(callback);
            ActiveBiz.getInstance().selectAll(handler);
        }

        ImageView imgAdd = (ImageView)findViewById(R.id.img_add);
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ActiveActivity.this,ActiveAddActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setListView(){
        ListView listView = (ListView)findViewById(R.id.list);
        MyAdapter adapter = new MyAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(ActiveActivity.this,ActiveInfoActivity.class);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });
    }

    class MyAdapter extends BaseAdapter {

        private LayoutInflater inflater;

        public MyAdapter() {
            super();
            inflater = LayoutInflater.from(ActiveActivity.this);
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
                convertView = inflater.inflate(R.layout.item_active, parent, false);
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

    class ActiveCallback implements Handler.Callback{
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what){
                case Const.MSG_SUCCESS:
                    count = TApplication.activeList.size();
                    setListView();
                    break;
                case Const.MSG_FAILURE:
                    break;
            }
            return true;
        }
    }

}
