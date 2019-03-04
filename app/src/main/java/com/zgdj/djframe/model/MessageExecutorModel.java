package com.zgdj.djframe.model;

import java.io.Serializable;
import java.util.List;

/**
 * description: 流程审批 -- 选择执行人
 * author: Created by Mr.Zhang on 2018/6/27
 */
public class MessageExecutorModel implements Serializable {


    /**
     * code : 1
     * totalpage : 2
     * totalnum : 8
     * data : [{"id":1,"nickname":"超级管理员","name":"丰宁抽水蓄能电站","p_name":null},{"id":89,"nickname":"孙钰杰施工","name":"北京院BIM中心","p_name":"北京院BIM中心"},{"id":98,"nickname":"费万堂","name":"公司领导","p_name":"河北丰宁抽水蓄能有限公司"},{"id":154,"nickname":"张宁","name":"项目领导","p_name":"浙江华东工程咨询有限公司丰宁抽水蓄能电站工程建设监理中心"},{"id":232,"nickname":"牛帅","name":"信息与数字工程中心","p_name":"北京院设计"}]
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

    public static class DataBean {
        /**
         * id : 1
         * nickname : 超级管理员
         * name : 丰宁抽水蓄能电站
         * p_name : null
         */

        private String id;
        private String nickname;
        private String name;
        private String p_name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getP_name() {
            return p_name;
        }

        public void setP_name(String p_name) {
            this.p_name = p_name;
        }
    }
}
