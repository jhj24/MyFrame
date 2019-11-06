package com.zgdj.djframe.model;

import java.io.Serializable;
import java.util.List;

/**
 * description: 实时进度列表
 * author: Created by Mr.Zhang on 2018/7/25
 */
public class ProgressListModel implements Serializable {


    /**
     * code : 1
     * totalpage : 1
     * totalnum : 4
     * data : [{"section_name":"引水系统土建及金属结构安装工程","actual_date":"2018-07-04","user_name":"超级管理员","remark":"","id":5,"relevance":"否"},{"section_name":"引水系统土建及金属结构安装工程","actual_date":"2018-07-10","user_name":"超级管理员","remark":"","id":4,"relevance":"否"},{"section_name":"引水系统土建及金属结构安装工程","actual_date":"2018-07-06","user_name":"超级管理员","remark":"2018-07-06第二次填报","id":2,"relevance":"否"},{"section_name":"引水系统土建及金属结构安装工程","actual_date":"2018-07-06","user_name":"超级管理员","remark":"2018-07-06的填报备注","id":1,"relevance":"否"}]
     */

    private int code;
    private int totalpage;
    private int totalnum;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(int totalpage) {
        this.totalpage = totalpage;
    }

    public int getTotalnum() {
        return totalnum;
    }

    public void setTotalnum(int totalnum) {
        this.totalnum = totalnum;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * section_name : 引水系统土建及金属结构安装工程
         * actual_date : 2018-07-04
         * user_name : 超级管理员
         * remark :
         * id : 5
         * relevance : 否
         */

        private String section_name;
        private String actual_date;
        private String user_name;
        private String remark;
        private int id;
        private List<FileBean> path;

        public String getSection_name() {
            return section_name;
        }

        public void setSection_name(String section_name) {
            this.section_name = section_name;
        }

        public String getActual_date() {
            return actual_date;
        }

        public void setActual_date(String actual_date) {
            this.actual_date = actual_date;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public List<FileBean> getPath() {
            return path;
        }

        public void setPath(List<FileBean> path) {
            this.path = path;
        }
    }

    public static class FileBean implements Serializable {
        private int id;
        private String name;
        private String filepath;

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

        public String getFilepath() {
            return filepath;
        }

        public void setFilepath(String filepath) {
            this.filepath = filepath;
        }
    }

}
