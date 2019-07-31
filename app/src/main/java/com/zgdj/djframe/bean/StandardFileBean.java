package com.zgdj.djframe.bean;

import java.util.List;

public class StandardFileBean {

    /**
     * code : 1
     * totalnumber : 21
     * totalpage : 3
     * data : [{"id":513,"standard_number":"88888","standard_name":"37373","material_date":"2019-07-29","alternate_standard":"7887373","remark":"7837837378373","file_id":4792,"path":"/uploads/norm/norm_file/20190729/4d63c800cde15b6c57f046e849970dda.docx"},{"id":512,"standard_number":"tttt00-werwer","standard_name":"aaaaaaa","material_date":"2019-07-29","alternate_standard":"23424","remark":"这是一条备注22222222222222","file_id":4791,"path":"/uploads/norm/norm_file/20190729/958d9347571415961a41356fe4c937ca.docx"},{"id":505,"standard_number":"testdownload","standard_name":"testdownload","material_date":"2018-06-05","alternate_standard":"","remark":"qweqweqwe","file_id":"","path":""},{"id":504,"standard_number":"testdownload","standard_name":"testdownload","material_date":"2018-06-05","alternate_standard":"","remark":"","file_id":"","path":""},{"id":257,"standard_number":"NB/T 35012-2013","standard_name":"水电工程对外交通专用公路设计规范","material_date":"2013/10/1","alternate_standard":"DL/T 5086-1999","remark":"","file_id":"","path":""},{"id":256,"standard_number":"NB/T 35011-2013","standard_name":"水电站厂房设计规范","material_date":"2013/10/1","alternate_standard":"SD 335-1989","remark":"","file_id":"","path":""},{"id":255,"standard_number":"NB/T 35010-2013","standard_name":"水力发电厂继电保护设计规范","material_date":"2013/10/1","alternate_standard":"DL/T 5177-2003","remark":"","file_id":"","path":""},{"id":254,"standard_number":"NB/T 35009-2013","standard_name":"抽水蓄能电站选点规划编制规范","material_date":"2013/10/1","alternate_standard":"DL/T 5172-2003","remark":"","file_id":"","path":""},{"id":253,"standard_number":"NB/T 35008-2013","standard_name":"水力发电厂照明设计规范","material_date":"2013/10/1","alternate_standard":"DL/T 5140-2001","remark":"","file_id":"","path":""},{"id":252,"standard_number":"NB/T 35007-2013","standard_name":"水电工程施工地质规程","material_date":"2013/10/1","alternate_standard":"DL/T 5109-1999","remark":"","file_id":"","path":""}]
     */

    private int code;
    private int totalnumber;
    private int totalpage;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getTotalnumber() {
        return totalnumber;
    }

    public void setTotalnumber(int totalnumber) {
        this.totalnumber = totalnumber;
    }

    public int getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(int totalpage) {
        this.totalpage = totalpage;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 513
         * standard_number : 88888
         * standard_name : 37373
         * material_date : 2019-07-29
         * alternate_standard : 7887373
         * remark : 7837837378373
         * file_id : 4792
         * path : /uploads/norm/norm_file/20190729/4d63c800cde15b6c57f046e849970dda.docx
         */

        private int id;
        private String standard_number;
        private String standard_name;
        private String material_date;
        private String alternate_standard;
        private String remark;
        private String file_id;
        private String path;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getStandard_number() {
            return standard_number;
        }

        public void setStandard_number(String standard_number) {
            this.standard_number = standard_number;
        }

        public String getStandard_name() {
            return standard_name;
        }

        public void setStandard_name(String standard_name) {
            this.standard_name = standard_name;
        }

        public String getMaterial_date() {
            return material_date;
        }

        public void setMaterial_date(String material_date) {
            this.material_date = material_date;
        }

        public String getAlternate_standard() {
            return alternate_standard;
        }

        public void setAlternate_standard(String alternate_standard) {
            this.alternate_standard = alternate_standard;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getFile_id() {
            return file_id;
        }

        public void setFile_id(String file_id) {
            this.file_id = file_id;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}
