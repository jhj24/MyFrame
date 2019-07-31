package com.zgdj.djframe.bean;

import java.util.List;

public class DocumentFileBean {

    /**
     * code : 1
     * totalnumber : 3
     * totalpage : 1
     * data : [{"id":15,"pid":0,"cate_number":2,"picture_number":"BJ68S-H5-1-1X1~2X1","picture_name":"厂区建筑物布置修改图","picture_papaer_num":2,"a1_picture":"2","design_name":"李刚","check_name":"张建富","examination_name":"杜晓京","completion_time":"2018-04","section":"FNP/C3、EM1","paper_category":"","owner":"超级管理员","create_time":"2018-06-06","children":[]},{"id":16,"pid":0,"cate_number":3,"picture_number":"BJ68S-H5-1-3X1","picture_name":"地下洞室群平面布置修改图","picture_papaer_num":1,"a1_picture":"1.5","design_name":"李刚","check_name":"张建富","examination_name":"杜晓京","completion_time":"2018-04","section":"FNP2/C2","paper_category":"A","owner":"超级管理员","create_time":"2018-06-06","children":[{"id":484,"pid":16,"cate_number":1,"picture_number":"BJ68S-H5-1-3X1-其味无穷","picture_name":"Asdasd","picture_papaer_num":1,"a1_picture":null,"design_name":null,"check_name":null,"examination_name":null,"completion_time":null,"section":null,"paper_category":"A","owner":"余晶","create_time":"2019-07-31"},{"id":476,"pid":16,"cate_number":1,"picture_number":"BJ68S-H5-1-3X1-55555","picture_name":"测试用的图纸文档","picture_papaer_num":1,"a1_picture":null,"design_name":null,"check_name":null,"examination_name":null,"completion_time":null,"section":null,"paper_category":"","owner":"余晶","create_time":"2019-07-29"},{"id":477,"pid":16,"cate_number":1,"picture_number":"BJ68S-H5-1-3X1-66666","picture_name":"测试用的图片","picture_papaer_num":1,"a1_picture":null,"design_name":null,"check_name":null,"examination_name":null,"completion_time":null,"section":null,"paper_category":"","owner":"余晶","create_time":"2019-07-29"},{"id":483,"pid":16,"cate_number":1,"picture_number":"BJ68S-H5-1-3X1-22222","picture_name":"234","picture_papaer_num":1,"a1_picture":null,"design_name":null,"check_name":null,"examination_name":null,"completion_time":null,"section":null,"paper_category":"A","owner":"余晶","create_time":"2019-07-31"}]},{"id":17,"pid":0,"cate_number":4,"picture_number":"BJ68S-H5-1-4X1","picture_name":"地下洞室群三维轴测修改图","picture_papaer_num":1,"a1_picture":"1","design_name":"魏金帅","check_name":"李刚","examination_name":"杜晓京","completion_time":"2018-04","section":"FNP/C3、EM1","paper_category":"","owner":"超级管理员","create_time":"2018-06-06","children":[]}]
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
         * id : 15
         * pid : 0
         * cate_number : 2
         * picture_number : BJ68S-H5-1-1X1~2X1
         * picture_name : 厂区建筑物布置修改图
         * picture_papaer_num : 2
         * a1_picture : 2
         * design_name : 李刚
         * check_name : 张建富
         * examination_name : 杜晓京
         * completion_time : 2018-04
         * section : FNP/C3、EM1
         * paper_category :
         * owner : 超级管理员
         * create_time : 2018-06-06
         * children : []
         */

        private int id;
        private int pid;
        private int cate_number;
        private String picture_number;
        private String picture_name;
        private int picture_papaer_num;
        private String a1_picture;
        private String design_name;
        private String check_name;
        private String examination_name;
        private String completion_time;
        private String section;
        private String paper_category;
        private String owner;
        private String create_time;
        private List<?> children;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public int getCate_number() {
            return cate_number;
        }

        public void setCate_number(int cate_number) {
            this.cate_number = cate_number;
        }

        public String getPicture_number() {
            return picture_number;
        }

        public void setPicture_number(String picture_number) {
            this.picture_number = picture_number;
        }

        public String getPicture_name() {
            return picture_name;
        }

        public void setPicture_name(String picture_name) {
            this.picture_name = picture_name;
        }

        public int getPicture_papaer_num() {
            return picture_papaer_num;
        }

        public void setPicture_papaer_num(int picture_papaer_num) {
            this.picture_papaer_num = picture_papaer_num;
        }

        public String getA1_picture() {
            return a1_picture;
        }

        public void setA1_picture(String a1_picture) {
            this.a1_picture = a1_picture;
        }

        public String getDesign_name() {
            return design_name;
        }

        public void setDesign_name(String design_name) {
            this.design_name = design_name;
        }

        public String getCheck_name() {
            return check_name;
        }

        public void setCheck_name(String check_name) {
            this.check_name = check_name;
        }

        public String getExamination_name() {
            return examination_name;
        }

        public void setExamination_name(String examination_name) {
            this.examination_name = examination_name;
        }

        public String getCompletion_time() {
            return completion_time;
        }

        public void setCompletion_time(String completion_time) {
            this.completion_time = completion_time;
        }

        public String getSection() {
            return section;
        }

        public void setSection(String section) {
            this.section = section;
        }

        public String getPaper_category() {
            return paper_category;
        }

        public void setPaper_category(String paper_category) {
            this.paper_category = paper_category;
        }

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public List<?> getChildren() {
            return children;
        }

        public void setChildren(List<?> children) {
            this.children = children;
        }
    }
}
