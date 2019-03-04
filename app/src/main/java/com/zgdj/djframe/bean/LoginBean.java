package com.zgdj.djframe.bean;

/**
 * description: 登录实体
 * author: Created by ShuaiQi_Zhang on 2018/5/3
 * version: 1.0
 */
public class LoginBean {
    private String name;
    private String password;
    private String captcha;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
}
