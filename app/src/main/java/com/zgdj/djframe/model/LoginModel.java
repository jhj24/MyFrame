package com.zgdj.djframe.model;

import java.io.Serializable;
import java.util.List;

/**
 * description: 登录model
 * author: Created by ShuaiQi_Zhang on 2018/5/3
 * version:
 */
public class LoginModel implements Serializable {


    /**
     * status : 200
     * msg : OK
     * message : [{"id":1,"name":"丰宁抽水蓄能电站","pid":0},{"id":282,"name":"2018年","pid":1},{"id":283,"name":"04月","pid":282},{"id":285,"name":"23日","pid":283},{"id":286,"name":"25日","pid":283},{"id":289,"name":"26日","pid":283},{"id":290,"name":"27日","pid":283},{"id":291,"name":"05月","pid":282},{"id":293,"name":"04日","pid":291}]
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
         */

        private int id;
        private String name;
        private int pid;

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
    }
}
