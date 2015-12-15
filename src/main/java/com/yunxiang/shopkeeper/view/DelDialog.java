package com.yunxiang.shopkeeper.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.yunxiang.shopkeeper.R;


/**
 * Created by yunxiang on 2015/12/2.
 */
public class DelDialog extends Dialog implements View.OnClickListener {
    Context context;
    IOnClickListener iOnClickListener;

    public DelDialog(Context context, int theme) {
        super(context, theme);
        this.context=context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.del_dialog);
        ImageView img = (ImageView) findViewById(R.id.iv_del);
        img.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        iOnClickListener= (IOnClickListener) context;
        iOnClickListener.dialogOnClick();
    }

    public interface IOnClickListener {
        public void dialogOnClick();
    }
}
