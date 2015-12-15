package com.yunxiang.shopkeeper.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunxiang.shopkeeper.R;


/**
 * Created by yunxiang on 2015/12/8.
 */
public class RechargeDialog extends Dialog implements View.OnClickListener {
   private String  account,money,category;
    private Button btCommit;
    private ImageView ivClose;
    private RechargeDialogCallback rechargeDialogCallback;
    private Context context;

    public RechargeDialog(Context context, int theme,String account,String money,String category) {
        super(context, theme);
        this.context=context;
        this.account=account;
        this.money=money;
        this.category=category;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recharge_dialog);
        initView();
    }

    private void initView() {
        TextView   txt_account= (TextView) findViewById(R.id.txt_account);
        TextView   txt_category= (TextView) findViewById(R.id.txt_category);
        TextView   txt_money= (TextView) findViewById(R.id.txt_money);
        txt_account.setText(account);
        txt_category.setText(category);
        txt_money.setText(money);


        btCommit= (Button) findViewById(R.id.bt_commit);
        ivClose= (ImageView) findViewById(R.id.iv_close);


        btCommit.setOnClickListener(this);
        ivClose.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
        rechargeDialogCallback= (RechargeDialogCallback) context;
        if (v.getId()== R.id.iv_close){
            rechargeDialogCallback.rechargeDialogCallbackClose();
        }else if(v.getId()== R.id.bt_commit){
            rechargeDialogCallback.rechargeDialogCallbackButton();
        }


    }

    public interface RechargeDialogCallback {
         void rechargeDialogCallbackClose();
        void rechargeDialogCallbackButton();

    }
}
