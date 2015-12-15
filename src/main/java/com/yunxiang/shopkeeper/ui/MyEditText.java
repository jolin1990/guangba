package com.yunxiang.shopkeeper.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * @desc: Created by jiely on 2015/10/17.
 */
public class MyEditText extends EditText {

    private String left;
    //private String right;

    public MyEditText(Context context) {
        super(context);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(left != null){
            Paint paint = new Paint();
            paint.setTextSize(20);
            paint.setColor(Color.GRAY);
            canvas.drawText(left, 10, getHeight() / 2 + 5, paint);
        }

        super.onDraw(canvas);
    }

    public void setDisplayText(String text){
        left = text;
    }
}
