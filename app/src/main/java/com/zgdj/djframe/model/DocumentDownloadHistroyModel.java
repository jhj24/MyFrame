package com.zgdj.djframe.model;

import java.util.List;

public class DocumentDownloadHistroyModel {

    /**
     * code : 1
     * totalnumber : 30
     * totalpage : 3
     * data : [{"id":70,"selfid":90,"cate_id":16,"create_time":"2019-07-31 18:05:58","user_name":"余晶"},{"id":68,"selfid":90,"cate_id":16,"create_time":"2019-07-31 18:02:59","user_name":"余晶"},{"id":63,"selfid":90,"cate_id":16,"create_time":"2019-07-31 11:18:27","user_name":"余晶"},{"id":60,"selfid":90,"cate_id":16,"create_time":"2019-07-31 11:04:42","user_name":"余晶"},{"id":59,"selfid":90,"cate_id":16,"create_time":"2019-07-31 11:04:41","user_name":"余晶"},{"id":58,"selfid":90,"cate_id":16,"create_time":"2019-07-31 11:04:37","user_name":"余晶"},{"id":57,"selfid":90,"cate_id":16,"create_time":"2019-07-31 11:03:18","user_name":"余晶"},{"id":52,"selfid":90,"cate_id":16,"create_time":"2019-07-29 11:48:11","user_name":"余晶"},{"id":51,"selfid":90,"cate_id":16,"create_time":"2019-07-29 09:22:48","user_name":"余晶"},{"id":50,"selfid":90,"cate_id":16,"create_time":"2019-07-29 09:21:48","user_name":"余晶"}]
     */

    private int code;
    private int totalnumber;
    private int totalpage;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getTotalnumber() {
        return totalnumber;
    }

    public void setTotalnumber(int totalnumber) {
        this.totalnumber = totalnumber;
    }

    public int getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(int totalpage) {
        this.totalpage = totalpage;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 70
         * selfid : 90
         * cate_id : 16
         * create_time : 2019-07-31 18:05:58
         * user_name : 余晶
         */

        private int id;
        private int selfid;
        private int cate_id;
        private String create_time;
        private String user_name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getSelfid() {
            return selfid;
        }

        public void setSelfid(int selfid) {
            this.selfid = selfid;
        }

        public int getCate_id() {
            return cate_id;
        }

        public void setCate_id(int cate_id) {
            this.cate_id = cate_id;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }
    }
}
