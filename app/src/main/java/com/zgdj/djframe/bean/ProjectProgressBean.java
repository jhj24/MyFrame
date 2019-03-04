package com.zgdj.djframe.bean;

import java.io.Serializable;

/**
 * description:  首页 --> 工程进度 Tab
 * author: Created by ShuaiQi_Zhang on 2018/4/28
 * version: 1.0
 */
public class ProjectProgressBean implements Serializable {

    public ProjectProgressBean(String title, boolean isChose) {
        this.Title = title;
        this.isChose = isChose;
    }

    private String Title;
    private boolean isChose;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public boolean isChose() {
        return isChose;
    }

    public void setChose(boolean chose) {
        isChose = chose;
    }
}
