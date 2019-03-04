package com.zgdj.djframe.utils;

import android.app.Activity;
import android.app.DatePickerDialog;

import java.util.Calendar;

/**
 * description: 时间 和 日期控件
 * author: Created by Mr.Zhang on 2018/7/5
 */
public class DatePickUtils {
    private DatePickerDialog datePickerDialog;
    private DateBack back;


    //新建日期控件
    public DatePickUtils createDatePick(Activity activity) {
        if (datePickerDialog == null) {
            Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);
            datePickerDialog = new DatePickerDialog(activity,
                    (view1, year, month, dayOfMonth) -> {
                        if (back != null)
                            back.result(year, month + 1, dayOfMonth);

                    }, mYear, mMonth, mDay);
        }
        return this;
    }

    //回调
    public DatePickerDialog setBack(DateBack back) {
        this.back = back;
        return datePickerDialog;
    }

    public interface DateBack {
        void result(int year, int month, int day);
    }
}
