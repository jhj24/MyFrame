package com.zgdj.djframe.bean;

import java.io.Serializable;

/**
 * description: 质量验评第一部分数据
 * author: Created by ShuaiQi_Zhang on 2018/5/28
 * version:1.0
 */
public class QualityEvaluationList1Bean implements Serializable {


    /**
     * id : 1
     * code : C3
     * name : 测试数据
     * shortName : 2
     * builderId : 2
     * supervisorId : 3
     * constructorId : 8
     * designerId : 13
     * otherId : 0
     * contractId : 35
     * money : 2
     * remark : null
     * eveluateResult : null
     * eveluateDate : null
     * eveluateUserId : null
     */

    private String id;
    private String code;
    private String name;
    private String shortName;
    private String builderId;
    private String supervisorId;
    private String constructorId;
    private String designerId;
    private String otherId;
    private String contractId;
    private String money;
    private String remark;
    private String eveluateResult;
    private String eveluateDate;
    private String eveluateUserId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getBuilderId() {
        return builderId;
    }

    public void setBuilderId(String builderId) {
        this.builderId = builderId;
    }

    public String getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(String supervisorId) {
        this.supervisorId = supervisorId;
    }

    public String getConstructorId() {
        return constructorId;
    }

    public void setConstructorId(String constructorId) {
        this.constructorId = constructorId;
    }

    public String getDesignerId() {
        return designerId;
    }

    public void setDesignerId(String designerId) {
        this.designerId = designerId;
    }

    public String getOtherId() {
        return otherId;
    }

    public void setOtherId(String otherId) {
        this.otherId = otherId;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getEveluateResult() {
        return eveluateResult;
    }

    public void setEveluateResult(String eveluateResult) {
        this.eveluateResult = eveluateResult;
    }

    public String getEveluateDate() {
        return eveluateDate;
    }

    public void setEveluateDate(String eveluateDate) {
        this.eveluateDate = eveluateDate;
    }

    public String getEveluateUserId() {
        return eveluateUserId;
    }

    public void setEveluateUserId(String eveluateUserId) {
        this.eveluateUserId = eveluateUserId;
    }
}
