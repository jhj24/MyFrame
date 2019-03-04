package com.zgdj.djframe.bean;

import java.io.Serializable;

/**
 * description: 质量验评第2,3,4,5部分数据
 * author: Created by ShuaiQi_Zhang on 2018/5/28
 * version:1.0
 */
public class QualityEvaluationList2Bean implements Serializable {


    /**
     * id : 1
     * d_name : 尾水系统工程
     * d_code : P2-42
     * pid : 0
     * type : 1
     * en_type : null
     * section_id : 1
     * primary : null
     * remark : null
     * create_time : 1527146517
     * update_time : 1527477012
     * filepath : ./uploads/quality/division/import/20180524/5e586e873c61557f838ee48b93d22ab0.xlsx
     * evaluation_results : 1
     * evaluation_time : 1527436800
     */

    private String id;
    private String d_name;
    private String d_code;
    private String pid;
    private int type;
    private String en_type;
    private int section_id;
    private String primary;
    private String remark;
    private int create_time;
    private int update_time;
    private String filepath;
    private int evaluation_results;
    private int evaluation_time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getD_name() {
        return d_name;
    }

    public void setD_name(String d_name) {
        this.d_name = d_name;
    }

    public String getD_code() {
        return d_code;
    }

    public void setD_code(String d_code) {
        this.d_code = d_code;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getEn_type() {
        return en_type;
    }

    public void setEn_type(String en_type) {
        this.en_type = en_type;
    }

    public int getSection_id() {
        return section_id;
    }

    public void setSection_id(int section_id) {
        this.section_id = section_id;
    }

    public String getPrimary() {
        return primary;
    }

    public void setPrimary(String primary) {
        this.primary = primary;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public int getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(int update_time) {
        this.update_time = update_time;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public int getEvaluation_results() {
        return evaluation_results;
    }

    public void setEvaluation_results(int evaluation_results) {
        this.evaluation_results = evaluation_results;
    }

    public int getEvaluation_time() {
        return evaluation_time;
    }

    public void setEvaluation_time(int evaluation_time) {
        this.evaluation_time = evaluation_time;
    }
}
