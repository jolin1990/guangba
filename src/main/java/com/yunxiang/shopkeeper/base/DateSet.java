package com.yunxiang.shopkeeper.base;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.format.DateFormat;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * desc: 设置日期 2015/12/11.
 */
public class DateSet {
    private String date,time;//时间
    private boolean isDateSet = false;
    private TextView textView;
    private FragmentManager fm;

    public DateSet(FragmentManager fm, TextView textView) {
        this.fm = fm;
        this.textView = textView;
    }

    public void show(){
        SetTimeDialog std = new SetTimeDialog();
        std.show(fm, "timePicker");
        SetDateDialog sdt = new SetDateDialog();
        sdt.show(fm, "datePicker");
    }

    //创建日期选择对话框
    class SetDateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener{

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            //Calendar 是一个抽象类，是通过getInstance()来获得实例,设置成系统默认时间
            final Calendar c = Calendar.getInstance();
            //获取年，月，日
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day= c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            date = String.valueOf(year+"-"+(month+1)+"-"+day);

            if(isDateSet){
                String str = date+ " " +time;
                textView.setText(str);
                isDateSet = false;
            }
            isDateSet = true;
        }
    }
    //创建时间选择对话框
    class SetTimeDialog extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            //获取小时，分钟
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            return new TimePickerDialog(getActivity(),this,hour, minute, DateFormat.is24HourFormat(getActivity()));
        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute){
            String strMinute = String.valueOf(minute);
            if(minute < 10){
                strMinute = String.valueOf("0" + minute);
            }
            time = String.valueOf(hourOfDay + ":" + strMinute);
            if(isDateSet){
                String str = date+ " " +time;
                textView.setText(str);
                isDateSet = false;
            }
            isDateSet = true;
        }

        @Override
        public void onCancel(DialogInterface dialog) {
            super.onCancel(dialog);
        }
    }
}
