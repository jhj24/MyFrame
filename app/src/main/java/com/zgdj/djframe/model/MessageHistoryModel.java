package com.zgdj.djframe.model;

import java.io.Serializable;
import java.util.List;

/**
 * description: 消息item - 查看历史model
 * author: Created by Mr.Zhang on 2018/6/26
 */
public class MessageHistoryModel implements Serializable {


    /**
     * code : 1
     * basedata : {"taskName":"工程记录照片表","dwName":"地下厂房工程P3-32","fbName":"安全监测P3-32-151","dyName":"变形监测P3-32-151-001","pileNo":"31","altitude":"EL.34EL.36"}
     * data : [{"nickname":"超级管理员","create_time":"","result":"待提交","mark":""},{"nickname":null,"create_time":1529975315,"result":"未通过","mark":""},{"nickname":"超级管理员","create_time":1529975263,"result":"通过","mark":""}]
     */

    private int code;
    private BasedataBean basedata;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public BasedataBean getBasedata() {
        return basedata;
    }

    public void setBasedata(BasedataBean basedata) {
        this.basedata = basedata;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class BasedataBean {
        /**
         * taskName : 工程记录照片表
         * dwName : 地下厂房工程P3-32
         * fbName : 安全监测P3-32-151
         * dyName : 变形监测P3-32-151-001
         * pileNo : 31
         * altitude : EL.34EL.36
         */

        private String taskName;
        private String dwName;
        private String fbName;
        private String dyName;
        private String pileNo;
        private String altitude;

        public String getTaskName() {
            return taskName;
        }

        public void setTaskName(String taskName) {
            this.taskName = taskName;
        }

        public String getDwName() {
            return dwName;
        }

        public void setDwName(String dwName) {
            this.dwName = dwName;
        }

        public String getFbName() {
            return fbName;
        }

        public void setFbName(String fbName) {
            this.fbName = fbName;
        }

        public String getDyName() {
            return dyName;
        }

        public void setDyName(String dyName) {
            this.dyName = dyName;
        }

        public String getPileNo() {
            return pileNo;
        }

        public void setPileNo(String pileNo) {
            this.pileNo = pileNo;
        }

        public String getAltitude() {
            return altitude;
        }

        public void setAltitude(String altitude) {
            this.altitude = altitude;
        }
    }

    public static class DataBean {
        /**
         * nickname : 超级管理员
         * create_time :
         * result : 待提交
         * mark :
         */

        private String nickname;
        private String create_time;
        private String result;
        private String mark;

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public String getMark() {
            return mark;
        }

        public void setMark(String mark) {
            this.mark = mark;
        }
    }
}
