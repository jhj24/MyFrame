package com.zgdj.djframe.bean;

import java.io.Serializable;

/**
 * description:
 * author: Created by ShuaiQi_Zhang on 2018/4/27
 * version:
 */
public class DynamicBean implements Serializable {
    private String title;//标题
    private String flag;//进度标示
    private String date;//日期

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
