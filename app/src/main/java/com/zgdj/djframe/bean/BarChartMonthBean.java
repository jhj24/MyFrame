package com.zgdj.djframe.bean;

import java.io.Serializable;
import java.util.List;

/**
 * description: 柱状图选择时间
 * author: Created by ShuaiQi_Zhang on 2018/6/1
 * version:1.0
 */
public class BarChartMonthBean implements Serializable {


    /**
     * code : 1
     * data : ["2017-06","2017-07","2017-08","2017-09","2017-10","2017-11","2017-12","2018-01","2018-02","2018-03","2018-04","2018-05"]
     * month : ["2017.05.26-2017.06.25","2017.06.26-2017.07.25","2017.07.26-2017.08.25","2017.08.26-2017.09.25","2017.09.26-2017.10.25","2017.10.26-2017.11.25","2017.11.26-2017.12.25","2017.12.26-2018.01.25","2018.01.26-2018.02.25","2018.02.26-2018.03.25","2018.03.26-2018.04.25","2018.04.26-2018.05.25"]
     */

    private int code;
    private List<String> data;
    private List<String> month;

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

    public List<String> getMonth() {
        return month;
    }

    public void setMonth(List<String> month) {
        this.month = month;
    }
}
