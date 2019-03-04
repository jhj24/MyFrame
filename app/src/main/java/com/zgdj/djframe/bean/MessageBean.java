package com.zgdj.djframe.bean;

import java.io.Serializable;

/**
 * description: 消息列表Bean
 * author: Created by ShuaiQi_Zhang on 2018/5/2
 * version:
 */
public class MessageBean implements Serializable {

    private int type;//类型
    private String title;//标题
    private String content;//内容
    private boolean isNew;// 是否新的
    private String time;//时间

    public MessageBean(int type, String title, String content, boolean isNew, String time) {
        this.type = type;
        this.title = title;
        this.content = content;
        this.isNew = isNew;
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
