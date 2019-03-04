package com.zgdj.djframe.bean;

import java.io.Serializable;
import java.util.List;

/**
 * description: 折线图 -- 获取年数据
 * author: Created by ShuaiQi_Zhang on 2018/6/4
 * version:1.0
 */
public class LineChartYearBean implements Serializable {


    /**
     * code : 1
     * data : ["2017","2018"]
     */

    private int code;
    private List<String> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
