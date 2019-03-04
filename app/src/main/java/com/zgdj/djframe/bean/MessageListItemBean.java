package com.zgdj.djframe.bean;

import java.io.Serializable;
import java.util.List;

/**
 * description: 消 息列表item 详情
 * author: Created by ShuaiQi_Zhang on 2018/6/5
 * version: 1.0
 */
public class MessageListItemBean implements Serializable {


    /**
     * id : 38
     * file_name : 给超级管理员发送
     * date : 2018-06-05
     * unit_name : 中国水利水电第十四工程局有限公司河北丰宁抽水蓄能电站项目部
     * send_name : 盟主
     * remark : sdfsdfsdf
     * file_ids : 2081,2082,2083
     * attachment : [{"id":2081,"name":"4.jpg","fileext":"jpg","url":"192.168.1.59:82\\uploads\\archive\\send\\20180605\\5746dbaf3b36a702920f70db8e81020c.jpg"},{"id":2082,"name":"3.jpg","fileext":"jpg","url":"192.168.1.59:82\\uploads\\archive\\send\\20180605\\ee531da0f2af10725249326b4be19c56.jpg"},{"id":2083,"name":"2.jpg","fileext":"jpg","url":"192.168.1.59:82\\uploads\\archive\\send\\20180605\\9ac80b97d5dd949284fb6b9f8a6b7fbf.jpg"}]
     */

    private int id;
    private String file_name;
    private String date;
    private String unit_name;
    private String send_name;
    private String remark;
    private String file_ids;
    private List<AttachmentBean> attachment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }

    public String getSend_name() {
        return send_name;
    }

    public void setSend_name(String send_name) {
        this.send_name = send_name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFile_ids() {
        return file_ids;
    }

    public void setFile_ids(String file_ids) {
        this.file_ids = file_ids;
    }

    public List<AttachmentBean> getAttachment() {
        return attachment;
    }

    public void setAttachment(List<AttachmentBean> attachment) {
        this.attachment = attachment;
    }

    public static class AttachmentBean {
        /**
         * id : 2081
         * name : 4.jpg
         * fileext : jpg
         * url : 192.168.1.59:82\ uploads\archive\send\20180605\5746dbaf3b36a702920f70db8e81020c.jpg
         */

        private int id;
        private String name;
        private String fileext;
        private String url;

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

        public String getFileext() {
            return fileext;
        }

        public void setFileext(String fileext) {
            this.fileext = fileext;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
