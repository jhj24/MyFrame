package com.zgdj.djframe.bean;

import java.io.Serializable;
import java.util.List;

/**
 * description: 折线图信息
 * author: Created by ShuaiQi_Zhang on 2018/6/4
 * version: 1.0
 */
public class LineChartDatasBean implements Serializable {


    /**
     * code : 1
     * data : {"section":["22","一期引水","一期厂房","二期引水","二期厂房"],"form_result_result":[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]}
     */

    private int code;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<String> section;
        private List<Integer> form_result_result;

        public List<String> getSection() {
            return section;
        }

        public void setSection(List<String> section) {
            this.section = section;
        }

        public List<Integer> getForm_result_result() {
            return form_result_result;
        }

        public void setForm_result_result(List<Integer> form_result_result) {
            this.form_result_result = form_result_result;
        }
    }
}
