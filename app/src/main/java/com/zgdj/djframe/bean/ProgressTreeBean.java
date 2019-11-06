package com.zgdj.djframe.bean;

import java.util.List;

public class ProgressTreeBean extends BaseTree<ProgressTreeBean> {

    private int id;
    private int pid;
    private String d_name;
    private String d_code;
    private int section_id;
    private int type;
    private List<ProgressTreeBean> children;

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getD_name() {
        return d_name;
    }

    public void setD_name(String d_name) {
        this.d_name = d_name;
    }

    public String getD_code() {
        return d_code;
    }

    public void setD_code(String d_code) {
        this.d_code = d_code;
    }

    public int getSection_id() {
        return section_id;
    }

    public void setSection_id(int section_id) {
        this.section_id = section_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setChildren(List<ProgressTreeBean> children) {
        this.children = children;
    }

    @Override
    public String getId() {
        return id + "";
    }

    @Override
    public String getName() {
        return d_name;
    }

    @Override
    public List<ProgressTreeBean> getChildren() {
        return children;
    }

    @Override
    public boolean isRoot() {
        return type != 3;
    }
}
