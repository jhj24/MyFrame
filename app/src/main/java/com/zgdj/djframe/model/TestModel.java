package com.zgdj.djframe.model;

import java.io.Serializable;

/**
 * description:
 * author: Created by ShuaiQi_Zhang on 2018/5/3
 * version:
 */
public class TestModel implements Serializable {
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
