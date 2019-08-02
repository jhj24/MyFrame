package com.zgdj.djframe.bean;

import java.io.Serializable;
import java.util.List;

/**
 * description: 扫描件回传
 * author: Created by ShuaiQi_Zhang on 2018/5/31
 * version: 1.0
 */
public class QualityEvaluationDetailScanBean implements Serializable {

    /**
     * recordsTotal : 1
     * recordsFiltered : 1
     * data : [{"id":382,"nickname":"超级管理员","currentname":null,"approvestatus":0,"create_time":"1527328291","CurrentApproverId":null,"CurrentStep":"0","user_id":1}]
     * herf : http://192.168.1.73/quality/matchform/matchform?cpr_id=2254
     */

    private int recordsTotal;
    private int recordsFiltered;
    private String herf;
    private List<DataBean> data;

    public int getCpr_id() {
        return cpr_id;
    }

    public void setCpr_id(int cpr_id) {
        this.cpr_id = cpr_id;
    }

    private int cpr_id;

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

    public String getHerf() {
        return herf;
    }

    public void setHerf(String herf) {
        this.herf = herf;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 382
         * nickname : 超级管理员
         * currentname : null
         * approvestatus : 0
         * create_time : 1527328291
         * CurrentApproverId : null
         * CurrentStep : 0
         * user_id : 1
         */

        private String id;
        private String nickname;
        private String currentname;
        private String approvestatus;
        private long create_time;
        private String CurrentApproverId;
        private String CurrentStep;
        private String user_id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getCurrentname() {
            return currentname;
        }

        public void setCurrentname(String currentname) {
            this.currentname = currentname;
        }

        public String getApprovestatus() {
            return approvestatus;
        }

        public void setApprovestatus(String approvestatus) {
            this.approvestatus = approvestatus;
        }

        public long getCreate_time() {
            return create_time;
        }

        public void setCreate_time(long create_time) {
            this.create_time = create_time;
        }

        public Object getCurrentApproverId() {
            return CurrentApproverId;
        }

        public void setCurrentApproverId(String CurrentApproverId) {
            this.CurrentApproverId = CurrentApproverId;
        }

        public String getCurrentStep() {
            return CurrentStep;
        }

        public void setCurrentStep(String CurrentStep) {
            this.CurrentStep = CurrentStep;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }
    }
}
