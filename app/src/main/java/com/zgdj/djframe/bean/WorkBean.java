package com.zgdj.djframe.bean;

import java.io.Serializable;

/**
 * description: 工作页面recycle bean
 * author: Created by ShuaiQi_Zhang on 2018/5/30
 * version: 1.0
 */
public class WorkBean implements Serializable {
    private String name;
    private int resId;

    public WorkBean(String name, int resId) {
        this.name = name;
        this.resId = resId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }
}
