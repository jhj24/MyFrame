package com.zgdj.djframe.bean;

import java.io.Serializable;
import java.util.List;

/**
 * description:
 * author: Created by ShuaiQi_Zhang on 2018/4/20
 * version:
 */
public class CommListData implements Serializable {

    private String count;//总条数
    private List<CommunityBean> list;//列表


    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<CommunityBean> getList() {
        return list;
    }

    public void setList(List<CommunityBean> list) {
        this.list = list;
    }
}
