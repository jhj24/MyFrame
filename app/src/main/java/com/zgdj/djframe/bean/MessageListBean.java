package com.zgdj.djframe.bean;

import java.io.Serializable;
import java.util.List;

/**
 * description:消息列表bean
 * author: Created by ShuaiQi_Zhang on 2018/6/4
 * version:1.0
 */
public class MessageListBean implements Serializable {

    /**
     * code : 1
     * pageCount : 5
     * pageArray : [{"task_name":"46456","create_time":1527755897,"sender":"超级管理员","task_category":"收文","status":1,"id":52,"type":1,"uint_id":28,"form_info":{"cpr_id":2424,"CurrentStep":"1"}},{"task_name":"4141","create_time":1527819857,"sender":"超级管理员","task_category":"收文","status":1,"id":54,"type":1,"uint_id":29,"form_info":{"cpr_id":2424,"CurrentStep":"1"}},{"task_name":"sdfsdf","create_time":1528080700,"sender":"超级管理员","task_category":"收文","status":1,"id":55,"type":1,"uint_id":30,"form_info":{"cpr_id":2424,"CurrentStep":"1"}},{"task_name":"767","create_time":1528082021,"sender":"超级管理员","task_category":"收文","status":1,"id":56,"type":1,"uint_id":31,"form_info":{"cpr_id":2424,"CurrentStep":"1"}},{"task_name":"工程记录照片表","create_time":1528083272,"sender":"超级管理员","task_category":"单元质量验评","status":1,"id":57,"type":2,"uint_id":474,"form_info":{"cpr_id":2424,"CurrentStep":"1"}}]
     */

    private int code;
    private int pageCount;
    private List<PageArrayBean> pageArray;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<PageArrayBean> getPageArray() {
        return pageArray;
    }

    public void setPageArray(List<PageArrayBean> pageArray) {
        this.pageArray = pageArray;
    }

    public static class PageArrayBean {
        /**
         * task_name : 46456
         * create_time : 1527755897
         * sender : 超级管理员
         * task_category : 收文
         * status : 1
         * id : 52
         * type : 1
         * uint_id : 28
         * form_info : {"cpr_id":2424,"CurrentStep":"1"}
         */

        private String task_name;
        private String create_time;
        private String sender;
        private String task_category;
        private String status;
        private String id;
        private String type;
        private String uint_id;
        private FormInfoBean form_info;

        public String getTask_name() {
            return task_name;
        }

        public void setTask_name(String task_name) {
            this.task_name = task_name;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getSender() {
            return sender;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }

        public String getTask_category() {
            return task_category;
        }

        public void setTask_category(String task_category) {
            this.task_category = task_category;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUint_id() {
            return uint_id;
        }

        public void setUint_id(String uint_id) {
            this.uint_id = uint_id;
        }

        public FormInfoBean getForm_info() {
            return form_info;
        }

        public void setForm_info(FormInfoBean form_info) {
            this.form_info = form_info;
        }

        public static class FormInfoBean {
            /**
             * cpr_id : 2424
             * CurrentStep : 1
             */

            private String cpr_id;
            private String CurrentStep;
            private String id;
            private String form_name;
            private boolean supervisor;

            public String getCpr_id() {
                return cpr_id;
            }

            public void setCpr_id(String cpr_id) {
                this.cpr_id = cpr_id;
            }

            public String getCurrentStep() {
                return CurrentStep;
            }

            public void setCurrentStep(String CurrentStep) {
                this.CurrentStep = CurrentStep;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getForm_name() {
                return form_name;
            }

            public void setForm_name(String form_name) {
                this.form_name = form_name;
            }

            public boolean isSupervisor() {
                return supervisor;
            }

            public void setSupervisor(boolean supervisor) {
                this.supervisor = supervisor;
            }
        }
    }
}
