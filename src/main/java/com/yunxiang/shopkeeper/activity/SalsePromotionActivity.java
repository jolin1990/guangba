package com.yunxiang.shopkeeper.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunxiang.shopkeeper.R;
import com.yunxiang.shopkeeper.TApplication;
import com.yunxiang.shopkeeper.base.BaseTitle;
import com.yunxiang.shopkeeper.biz.ActiveBiz;
import com.yunxiang.shopkeeper.model.Active;
import com.yunxiang.shopkeeper.utils.Const;

import java.util.List;

public class SalsePromotionActivity extends Activity{
    private ListView listView;
    private RelativeLayout rlAdd;
    private List<Active> actives;
    private LvAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salse_promotion);
        if(TApplication.versionType == Const.TEST_VERTION){

        }
        addTitle();
        addView();
        addData();
    }

    private void addData() {
        ShopActivityCallback callback=new ShopActivityCallback();
        Handler handler=new Handler(callback);
        ActiveBiz.getInstance().selectAll(handler);
    }

    private void addView(){
        rlAdd= (RelativeLayout) findViewById(R.id.rl_salse);
        rlAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //添加活动
            }
        });
        listView= (ListView) findViewById(R.id.listView);
        adapter=new LvAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(SalsePromotionActivity.this, ActiveInfoActivity.class);
               //int actiovityId = actives.get(i).getActivityId();
               //intent.putExtra("actiovityId", actiovityId);
                startActivity(intent);
            }
        });
    }

    private void addTitle() {
        BaseTitle.getInstance().setTitle(this, "促销活动");
    }



    class LvAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            Log.d("TAG","*********"+(actives!=null?actives.size():0));
            return actives!=null?actives.size():0;
        }

        @Override
        public Object getItem(int i) {
            return actives.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            //判断活动是否过期
            boolean isOverdue=actives.get(i).isOverdate();
            Holder holder;
            if (view==null){
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                view = inflater.inflate(R.layout.shop_info_item_layout,viewGroup,false);
                 holder=new Holder();
                holder.txtContent= (TextView) view.findViewById(R.id.event_txt);
                holder.bnt= (Button) view.findViewById(R.id.event_bnt);
                holder.img= (ImageView) view.findViewById(R.id.event_img);
                view.setTag(holder);


            }else{
                holder= (Holder) view.getTag();
            }
            holder.txtContent.setText(actives.get(i).getActiveName());

            if (isOverdue){

                holder.bnt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                holder.bnt.setVisibility(View.VISIBLE);
                holder.img.setVisibility(View.GONE);
            }else{
                holder.bnt.setVisibility(View.GONE);
                holder.img.setVisibility(View.VISIBLE);
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SalsePromotionActivity.this, ActiveInfoActivity.class);
                    long actiovityId = actives.get(i).getActiveId();
                    intent.putExtra("actiovityId", actiovityId);
                    startActivity(intent);
                }
            });
            return view;
        }
        class Holder{
            private TextView txtContent;
            private Button bnt;
            private ImageView img;
        }
    }
    class ShopActivityCallback implements Handler.Callback{

        @Override
        public boolean handleMessage(Message message) {
            if (message.what== Const.MSG_SUCCESS){
                actives= (List<Active>) message.obj;

                adapter.notifyDataSetChanged();
            }
            return true;
        }
    }
}
