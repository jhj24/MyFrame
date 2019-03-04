package com.zgdj.djframe.bean;

import java.io.Serializable;
import java.util.List;

/**
 * description: 质量管控内容列表bean
 * author: Created by ShuaiQi_Zhang on 2018/5/29
 * version: 1.0
 */
public class QualityEvaluationControlContentBean implements Serializable {


    /**
     * recordsTotal : 1
     * recordsFiltered : 2
     * data : [{"id":9725,"procedureid":67,"code":"02.04.03","name":"工程记录照片表","remark":"","qualitytemplateid":121,"isimportant":0,"isneedtemplate":1,"TemplateId":null,"type":1,"division_id":66,"ma_division_id":67,"control_id":79,"status":0,"update_time":null,"checked":0},{"id":9726,"procedureid":67,"code":"02.04.04","name":"工程建设标准强制性条文执行记录表","remark":"","qualitytemplateid":122,"isimportant":0,"isneedtemplate":1,"TemplateId":null,"type":1,"division_id":66,"ma_division_id":67,"control_id":81,"status":0,"update_time":null,"checked":0}]
     */

    private int code;
    private int recordsTotal;
    private int recordsFiltered;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

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
         * id : 9725
         * procedureid : 67
         * code : 02.04.03
         * name : 工程记录照片表
         * remark :
         * qualitytemplateid : 121
         * isimportant : 0
         * isneedtemplate : 1
         * TemplateId : null
         * type : 1
         * division_id : 66
         * ma_division_id : 67
         * control_id : 79
         * status : 0
         * update_time : null
         * checked : 0
         */

        private int id;
        private int procedureid;
        private String code;
        private String name;
        private String remark;
        private int qualitytemplateid;
        private int isimportant;
        private int isneedtemplate;
        private String TemplateId;
        private int type;
        private int division_id;
        private int ma_division_id;
        private int control_id;
        private int status;
        private String update_time;
        private int checked;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getProcedureid() {
            return procedureid;
        }

        public void setProcedureid(int procedureid) {
            this.procedureid = procedureid;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getQualitytemplateid() {
            return qualitytemplateid;
        }

        public void setQualitytemplateid(int qualitytemplateid) {
            this.qualitytemplateid = qualitytemplateid;
        }

        public int getIsimportant() {
            return isimportant;
        }

        public void setIsimportant(int isimportant) {
            this.isimportant = isimportant;
        }

        public int getIsneedtemplate() {
            return isneedtemplate;
        }

        public void setIsneedtemplate(int isneedtemplate) {
            this.isneedtemplate = isneedtemplate;
        }

        public String getTemplateId() {
            return TemplateId;
        }

        public void setTemplateId(String TemplateId) {
            this.TemplateId = TemplateId;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getDivision_id() {
            return division_id;
        }

        public void setDivision_id(int division_id) {
            this.division_id = division_id;
        }

        public int getMa_division_id() {
            return ma_division_id;
        }

        public void setMa_division_id(int ma_division_id) {
            this.ma_division_id = ma_division_id;
        }

        public int getControl_id() {
            return control_id;
        }

        public void setControl_id(int control_id) {
            this.control_id = control_id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public int getChecked() {
            return checked;
        }

        public void setChecked(int checked) {
            this.checked = checked;
        }
    }
}
