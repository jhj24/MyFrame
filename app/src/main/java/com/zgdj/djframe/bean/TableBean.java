package com.zgdj.djframe.bean;

import android.support.annotation.IdRes;

import java.io.Serializable;

/**
 * description: table 内容赋值
 * author: Created by ShuaiQi_Zhang on 2018/5/23
 * version: 1.0
 */
public class TableBean implements Serializable {

    private int resId;
    private String values;

    public TableBean(int resId, String values) {
        this.resId = resId;
        this.values = values;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(@IdRes int resId) {
        this.resId = resId;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }
}
