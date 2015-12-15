package com.yunxiang.shopkeeper.base;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.yunxiang.shopkeeper.R;
import com.yunxiang.shopkeeper.TApplication;

/**
 * @desc: Created by jiely on 2015/10/29.
 */
public class BaseDialog extends Dialog{

    private Context context;

    public BaseDialog(Context context) {
        super(context);
        this.context = context;
    }

    public BaseDialog(Context context, int theme) {
        super(context, theme);
    }

    protected BaseDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    private void init(){
        View mView = LayoutInflater.from(context).inflate(R.layout.dialog_edit_menu, null, true);
        Window dialogWindow = this.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        this.setContentView(mView);

        lp.width = (int) (300 * TApplication.displayMetrics.scaledDensity);
        lp.height = (int) (200 * TApplication.displayMetrics.scaledDensity);
        TextView txtView = (TextView) mView.findViewById(R.id.txt_click);
        txtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }


        });
    }
}
