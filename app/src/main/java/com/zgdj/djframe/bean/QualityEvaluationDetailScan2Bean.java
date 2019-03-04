package com.zgdj.djframe.bean;

import java.io.Serializable;
import java.util.List;

/**
 * description: 扫描件回传
 * author: Created by ShuaiQi_Zhang on 2018/5/31
 * version: 1.0
 */
public class QualityEvaluationDetailScan2Bean implements Serializable {


    /**
     * recordsTotal : 4
     * recordsFiltered : 1
     * data : [{"id":1901,"contr_relation_id":6269,"attachment_id":1901,"type":1,"data_name":"1.xls","module":"quality","
     * name":"1.xls","filename":"8535145e129f0f5f553b3a3b673e5d92.xls","filepath":"C:\\phpStudy\\WWW\\xin\\public\\
     * uploads\\quality\\element\\20180529\\8535145e129f0f5f553b3a3b673e5d92.xls","filesize":19456,"fileext":"xls",
     * "user_id":1,"uploadip":"127.0.0.1","status":0,"create_time":1527561200,"admin_id":0,"audit_time":0,"use":"element","download":0}]
     */

    private int recordsTotal;
    private int recordsFiltered;
    private List<DataBean> data;

    public int getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(int recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public int getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(int recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1901
         * contr_relation_id : 6269
         * attachment_id : 1901
         * type : 1
         * data_name : 1.xls
         * module : quality
         * name : 1.xls
         * filename : 8535145e129f0f5f553b3a3b673e5d92.xls
         * filepath : C:\phpStudy\WWW\xin\public\ uploads\quality\element\20180529\8535145e129f0f5f553b3a3b673e5d92.xls
         * filesize : 19456
         * fileext : xls
         * user_id : 1
         * uploadip : 127.0.0.1
         * status : 0
         * create_time : 1527561200
         * admin_id : 0
         * audit_time : 0
         * use : element
         * download : 0
         */

        private int id;
        private int contr_relation_id;
        private int attachment_id;
        private int type;
        private String data_name;
        private String module;
        private String name;
        private String filename;
        private String filepath;
        private int filesize;
        private String fileext;
        private int user_id;
        private String uploadip;
        private int status;
        private int create_time;
        private int admin_id;
        private int audit_time;
        private String use;
        private int download;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getContr_relation_id() {
            return contr_relation_id;
        }

        public void setContr_relation_id(int contr_relation_id) {
            this.contr_relation_id = contr_relation_id;
        }

        public int getAttachment_id() {
            return attachment_id;
        }

        public void setAttachment_id(int attachment_id) {
            this.attachment_id = attachment_id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getData_name() {
            return data_name;
        }

        public void setData_name(String data_name) {
            this.data_name = data_name;
        }

        public String getModule() {
            return module;
        }

        public void setModule(String module) {
            this.module = module;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public String getFilepath() {
            return filepath;
        }

        public void setFilepath(String filepath) {
            this.filepath = filepath;
        }

        public int getFilesize() {
            return filesize;
        }

        public void setFilesize(int filesize) {
            this.filesize = filesize;
        }

        public String getFileext() {
            return fileext;
        }

        public void setFileext(String fileext) {
            this.fileext = fileext;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getUploadip() {
            return uploadip;
        }

        public void setUploadip(String uploadip) {
            this.uploadip = uploadip;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public int getAdmin_id() {
            return admin_id;
        }

        public void setAdmin_id(int admin_id) {
            this.admin_id = admin_id;
        }

        public int getAudit_time() {
            return audit_time;
        }

        public void setAudit_time(int audit_time) {
            this.audit_time = audit_time;
        }

        public String getUse() {
            return use;
        }

        public void setUse(String use) {
            this.use = use;
        }

        public int getDownload() {
            return download;
        }

        public void setDownload(int download) {
            this.download = download;
        }
    }
}
