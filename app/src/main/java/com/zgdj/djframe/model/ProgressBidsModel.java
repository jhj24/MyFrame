package com.zgdj.djframe.model;

import java.io.Serializable;
import java.util.List;

/**
 * description: 实时进度 - 标段选择
 * author: Created by Mr.Zhang on 2018/7/25
 */
public class ProgressBidsModel implements Serializable {


    /**
     * code : 1
     * sectionArr : [{"name":"全部"},{"name":"引水系统土建及金属结构安装工程"},{"name":"地下厂房及尾水系统土建及金属结构安装工程"},{"name":"二期引水系统土建及金属结构安装工程"},{"name":"二期地下厂房及尾水系统土建及金属结构安装工程"}]
     * msg : 标段列表选项
     */

    private int code;
    private String msg;
    private List<SectionArrBean> sectionArr;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<SectionArrBean> getSectionArr() {
        return sectionArr;
    }

    public void setSectionArr(List<SectionArrBean> sectionArr) {
        this.sectionArr = sectionArr;
    }

    public static class SectionArrBean {
        /**
         * name : 全部
         */

        private String name;
        private int sectionId;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSectionId() {
            return sectionId;
        }

        public void setSectionId(int sectionId) {
            this.sectionId = sectionId;
        }
    }
}
