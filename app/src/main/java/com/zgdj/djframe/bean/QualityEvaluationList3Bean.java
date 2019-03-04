package com.zgdj.djframe.bean;

import java.io.Serializable;

/**
 * description: 质量验评第 链接 树形最内层 部分数据
 * author: Created by ShuaiQi_Zhang on 2018/5/28
 * version:1.0
 */
public class QualityEvaluationList3Bean implements Serializable {

    /**
     * id : 89
     * division_id : 99
     * serial_number : P2-31-11-001-11
     * site : 单元工程名称及部位
     * coding : P2-31-11-001-001
     * ma_bases : 191
     * su_basis :
     * hinge : 1
     * en_type : 15
     * el_start : EL.100
     * el_cease : EL.200
     * quantities : 213
     * pile_number : 56556
     * start_date : 2018-05-28
     * completion_date : 2018-05-31
     * create_time : 2018-05-28 15:50:10
     * update_time : 2018-05-28 16:51:04
     * EvaluateResult : 3
     * EvaluateDate : 1527436800
     */

    private int id;
    private int division_id;
    private String serial_number;
    private String site;
    private String coding;
    private String ma_bases;
    private String su_basis;
    private int hinge;
    private String en_type;
    private String el_start;
    private String el_cease;
    private String quantities;
    private String pile_number;
    private String start_date;
    private String completion_date;
    private String create_time;
    private String update_time;
    private int EvaluateResult;
    private int EvaluateDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDivision_id() {
        return division_id;
    }

    public void setDivision_id(int division_id) {
        this.division_id = division_id;
    }

    public String getSerial_number() {
        return serial_number;
    }

    public void setSerial_number(String serial_number) {
        this.serial_number = serial_number;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getCoding() {
        return coding;
    }

    public void setCoding(String coding) {
        this.coding = coding;
    }

    public String getMa_bases() {
        return ma_bases;
    }

    public void setMa_bases(String ma_bases) {
        this.ma_bases = ma_bases;
    }

    public String getSu_basis() {
        return su_basis;
    }

    public void setSu_basis(String su_basis) {
        this.su_basis = su_basis;
    }

    public int getHinge() {
        return hinge;
    }

    public void setHinge(int hinge) {
        this.hinge = hinge;
    }

    public String getEn_type() {
        return en_type;
    }

    public void setEn_type(String en_type) {
        this.en_type = en_type;
    }

    public String getEl_start() {
        return el_start;
    }

    public void setEl_start(String el_start) {
        this.el_start = el_start;
    }

    public String getEl_cease() {
        return el_cease;
    }

    public void setEl_cease(String el_cease) {
        this.el_cease = el_cease;
    }

    public String getQuantities() {
        return quantities;
    }

    public void setQuantities(String quantities) {
        this.quantities = quantities;
    }

    public String getPile_number() {
        return pile_number;
    }

    public void setPile_number(String pile_number) {
        this.pile_number = pile_number;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getCompletion_date() {
        return completion_date;
    }

    public void setCompletion_date(String completion_date) {
        this.completion_date = completion_date;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public int getEvaluateResult() {
        return EvaluateResult;
    }

    public void setEvaluateResult(int EvaluateResult) {
        this.EvaluateResult = EvaluateResult;
    }

    public int getEvaluateDate() {
        return EvaluateDate;
    }

    public void setEvaluateDate(int EvaluateDate) {
        this.EvaluateDate = EvaluateDate;
    }
}
