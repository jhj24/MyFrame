package com.zgdj.djframe.bean;

import java.io.Serializable;

/**
 * description:
 * author: Created by ShuaiQi_Zhang on 2018/5/9
 * version:
 */
public class ImageBean implements Serializable {

    private String imgId;//图片id
    private int resId;
    private String url;
    private String uri; // URI 不能序列化
    private int type; // 1 : id； 2 : url ; 3 : uri ; 4 : file

    public ImageBean(int resId, int type) {
        this.resId = resId;
        this.type = type;
    }

    public ImageBean(String url, int type) {
        this.url = url;
        this.type = type;
    }

    public ImageBean(int type, String uri) {
        this.uri = uri;
        this.type = type;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getImgId() {
        return imgId;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }
}
