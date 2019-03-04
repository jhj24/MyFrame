package com.zgdj.djframe.bean;

import java.io.Serializable;

/**
 * description: 个人中心信息
 * author: Created by ShuaiQi_Zhang on 2018/6/7
 * version: 1.0
 */
public class PersonalInfoBean implements Serializable {


    /**
     * code : 1
     * admin_info : {"id":1,"nickname":"超级管理员","thumb":"192.168.1.59:82\\uploads\\admin\\admin_thumb\\20180607\\e86bef18f5e4439ea45e2015dc40adb2.jpg","position":"总管理员","gender":"男","wechat":"888888888fsdfsdfsdfsdffffffsddssddddddddddddddddddddddddddd","mobile":"","tele":"010-88888888","mail":"888888888@qq.ggg"}
     */

    private int code;
    private AdminInfoBean admin_info;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public AdminInfoBean getAdmin_info() {
        return admin_info;
    }

    public void setAdmin_info(AdminInfoBean admin_info) {
        this.admin_info = admin_info;
    }

    public static class AdminInfoBean {
        /**
         * id : 1
         * nickname : 超级管理员
         * thumb : 192.168.1.59:82\ uploads\admin\admin_thumb\20180607\e86bef18f5e4439ea45e2015dc40adb2.jpg
         * position : 总管理员
         * gender : 男
         * wechat : 888888888fsdfsdfsdfsdffffffsddssddddddddddddddddddddddddddd
         * mobile :
         * tele : 010-88888888
         * mail : 888888888@qq.ggg
         */

        private int id;
        private String nickname;
        private String thumb;
        private String position;
        private String gender;
        private String wechat;
        private String mobile;
        private String tele;
        private String mail;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getWechat() {
            return wechat;
        }

        public void setWechat(String wechat) {
            this.wechat = wechat;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getTele() {
            return tele;
        }

        public void setTele(String tele) {
            this.tele = tele;
        }

        public String getMail() {
            return mail;
        }

        public void setMail(String mail) {
            this.mail = mail;
        }
    }
}
