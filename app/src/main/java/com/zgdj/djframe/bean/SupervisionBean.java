package com.zgdj.djframe.bean;

import java.io.Serializable;
import java.util.List;

/**
 * description: 监理日志列表Bean
 * author: Created by ShuaiQi_Zhang on 2018/5/4
 * version:1.0
 */
public class SupervisionBean implements Serializable {

    /**
     * status : 200
     * msg : OK
     * message : [{"id":1,"name":"丰宁抽水蓄能电站","pid":0,"date":null,"year":"","month":"","day":""},{"id":282,"name":"2018年","pid":1,"date":null,"year":"2018","month":"","day":""},{"id":283,"name":"04月","pid":282,"date":null,"year":"2018","month":"04","day":""},{"id":285,"name":"23日","pid":283,"date":"2018-04-23 10:21:32","year":"2018","month":"04","day":"23"},{"id":286,"name":"25日","pid":283,"date":"2018-04-25 17:49:24","year":"2018","month":"04","day":"25"},{"id":289,"name":"26日","pid":283,"date":"2018-04-26 17:48:57","year":"2018","month":"04","day":"26"},{"id":290,"name":"27日","pid":283,"date":"2018-04-27 13:49:22","year":"2018","month":"04","day":"27"},{"id":291,"name":"05月","pid":282,"date":null,"year":"2018","month":"05","day":""},{"id":293,"name":"04日","pid":291,"date":"2018-05-04 09:56:08","year":"2018","month":"05","day":"04"}]
     */

    private int status;
    private String msg;
    private List<MessageBean> message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<MessageBean> getMessage() {
        return message;
    }

    public void setMessage(List<MessageBean> message) {
        this.message = message;
    }

    public static class MessageBean {
        /**
         * id : 1
         * name : 丰宁抽水蓄能电站
         * pid : 0
         * date : null
         * year :
         * month :
         * day :
         */

        private int id;
        private String name;
        private int pid;
        private String date;
        private String year;
        private String month;
        private String day;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
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

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }
    }
}
