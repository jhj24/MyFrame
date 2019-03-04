package com.zgdj.djframe.bean;

import java.io.Serializable;
import java.util.List;

/**
 * description: 柱状图与表格信息bean
 * author: Created by ShuaiQi_Zhang on 2018/6/4
 * version:1.0
 */
public class BarChartBean implements Serializable {


    /**
     * code : 1
     * data : {"section":["22","一期引水","一期厂房","二期引水","二期厂房"],"form_result_result":[{"section_rate_number":{"excellent_number":1,"qualified_number":0,"total":1},"excellent":100},{"section_rate_number":{"excellent_number":0,"qualified_number":0,"total":0},"excellent":0},{"section_rate_number":{"excellent_number":0,"qualified_number":0,"total":0},"excellent":0},{"section_rate_number":{"excellent_number":0,"qualified_number":0,"total":0},"excellent":0},{"section_rate_number":{"excellent_number":0,"qualified_number":0,"total":0},"excellent":0}]}
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
        private List<FormResultResultBean> form_result_result;

        public List<String> getSection() {
            return section;
        }

        public void setSection(List<String> section) {
            this.section = section;
        }

        public List<FormResultResultBean> getForm_result_result() {
            return form_result_result;
        }

        public void setForm_result_result(List<FormResultResultBean> form_result_result) {
            this.form_result_result = form_result_result;
        }

        public static class FormResultResultBean {
            /**
             * section_rate_number : {"excellent_number":1,"qualified_number":0,"total":1}
             * excellent : 100
             */

            private SectionRateNumberBean section_rate_number;
            private int excellent;

            public SectionRateNumberBean getSection_rate_number() {
                return section_rate_number;
            }

            public void setSection_rate_number(SectionRateNumberBean section_rate_number) {
                this.section_rate_number = section_rate_number;
            }

            public int getExcellent() {
                return excellent;
            }

            public void setExcellent(int excellent) {
                this.excellent = excellent;
            }

            public static class SectionRateNumberBean {
                /**
                 * excellent_number : 1
                 * qualified_number : 0
                 * total : 1
                 */

                private int excellent_number;
                private int qualified_number;
                private int total;

                public int getExcellent_number() {
                    return excellent_number;
                }

                public void setExcellent_number(int excellent_number) {
                    this.excellent_number = excellent_number;
                }

                public int getQualified_number() {
                    return qualified_number;
                }

                public void setQualified_number(int qualified_number) {
                    this.qualified_number = qualified_number;
                }

                public int getTotal() {
                    return total;
                }

                public void setTotal(int total) {
                    this.total = total;
                }
            }
        }
    }
}
