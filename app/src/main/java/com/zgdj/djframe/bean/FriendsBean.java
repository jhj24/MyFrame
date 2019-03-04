package com.zgdj.djframe.bean;

import java.io.Serializable;

/**
 * description: 朋友圈列表bean
 * author: Created by ShuaiQi_Zhang on 2018/5/2
 * version: 1.0
 */
public class FriendsBean implements Serializable {
    private String img;//头像
    private String name;//姓名
    private String date;//日期
    private int type;//类型
    private String content;//内容

    public FriendsBean() {

    }

    public FriendsBean(String img, String name, String date, int type, String content) {
        this.img = img;
        this.name = name;
        this.date = date;
        this.type = type;
        this.content = content;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
