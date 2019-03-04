package com.zgdj.djframe.model;

import java.io.Serializable;
import java.util.List;

public class MessageTotalModel implements Serializable {


    /**
     * code : 4
     * data : {"section_name":"二期地下厂房及尾水系统土建及金属结构安装工程","user_name":"超级管理员","date":"2018年08月08日","lag_data":[{"name":"<新增任务>","percent_complete":"20","delayed_day":1,"start":"2018-08-09","finish":"2018-08-09","actual_start":"2018-08-10","edc":"2018-08-11"},{"name":"<新增任务>","percent_complete":"0","delayed_day":1,"start":"2018-08-09","finish":"2018-08-09","actual_start":"2018-08-10","edc":"2018-08-11"}]}
     * msg : 标段名称,滞后数据
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
         * section_name : 二期地下厂房及尾水系统土建及金属结构安装工程
         * user_name : 超级管理员
         * date : 2018年08月08日
         * lag_data : [{"name":"<新增任务>","percent_complete":"20","delayed_day":1,"start":"2018-08-09","finish":"2018-08-09","actual_start":"2018-08-10","edc":"2018-08-11"},{"name":"<新增任务>","percent_complete":"0","delayed_day":1,"start":"2018-08-09","finish":"2018-08-09","actual_start":"2018-08-10","edc":"2018-08-11"}]
         */

        private String section_name;
        private String user_name;
        private String date;
        private List<LagDataBean> lag_data;

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

        public List<LagDataBean> getLag_data() {
            return lag_data;
        }

        public void setLag_data(List<LagDataBean> lag_data) {
            this.lag_data = lag_data;
        }

        public static class LagDataBean {
            /**
             * name : <新增任务>
             * percent_complete : 20
             * delayed_day : 1
             * start : 2018-08-09
             * finish : 2018-08-09
             * actual_start : 2018-08-10
             * edc : 2018-08-11
             */

            private String name;
            private String percent_complete;
            private int delayed_day;
            private String start;
            private String finish;
            private String actual_start;
            private String edc;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPercent_complete() {
                return percent_complete;
            }

            public void setPercent_complete(String percent_complete) {
                this.percent_complete = percent_complete;
            }

            public int getDelayed_day() {
                return delayed_day;
            }

            public void setDelayed_day(int delayed_day) {
                this.delayed_day = delayed_day;
            }

            public String getStart() {
                return start;
            }

            public void setStart(String start) {
                this.start = start;
            }

            public String getFinish() {
                return finish;
            }

            public void setFinish(String finish) {
                this.finish = finish;
            }

            public String getActual_start() {
                return actual_start;
            }

            public void setActual_start(String actual_start) {
                this.actual_start = actual_start;
            }

            public String getEdc() {
                return edc;
            }

            public void setEdc(String edc) {
                this.edc = edc;
            }
        }
    }
}
