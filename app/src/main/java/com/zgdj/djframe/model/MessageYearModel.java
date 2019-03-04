package com.zgdj.djframe.model;

import java.io.Serializable;

public class MessageYearModel implements Serializable {

    /**
     * code : 1
     * data : {"section_name":"地下厂房及尾水系统土建及金属结构安装工程","user_name":"超级管理员","date":"2018年08月07日","year":"年中","url":"/progress/annualprogress/index"}
     * msg : 标段名称,日期,负责人,填报时段,跳转地址
     */

    private int code;
    private DataBean data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * section_name : 地下厂房及尾水系统土建及金属结构安装工程
         * user_name : 超级管理员
         * date : 2018年08月07日
         * year : 年中
         * url : /progress/annualprogress/index
         */

        private String section_name;
        private String user_name;
        private String date;
        private String year;
        private String url;

        public String getSection_name() {
            return section_name;
        }

        public void setSection_name(String section_name) {
            this.section_name = section_name;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
