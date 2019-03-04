package com.zgdj.djframe.bean;

import android.support.annotation.DrawableRes;

import java.io.Serializable;
import java.util.List;

/**
 * description:
 * author: Created by ShuaiQi_Zhang on 2018/5/23
 * version:
 */
public class ModelRealBean implements Serializable {
    private String title; //标题
    private List<ChildrenBean> list;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ChildrenBean> getList() {
        return list;
    }

    public void setList(List<ChildrenBean> list) {
        this.list = list;
    }

    // 子类bean
    public static class ChildrenBean {
        private String date;
        private String area;
        private String url;
        private int resId;

        public ChildrenBean(String date, String area, String url, @DrawableRes int resId) {
            this.date = date;
            this.area = area;
            this.url = url;
            this.resId = resId;
        }

        public int getResId() {
            return resId;
        }

        public void setResId(@DrawableRes int resId) {
            this.resId = resId;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
