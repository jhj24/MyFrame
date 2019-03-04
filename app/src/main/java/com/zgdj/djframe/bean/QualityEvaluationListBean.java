package com.zgdj.djframe.bean;

import java.io.Serializable;

/**
 * description: 质量验评列表实体
 * author: Created by ShuaiQi_Zhang on 2018/5/28
 * version:1.0
 */
public class QualityEvaluationListBean implements Serializable {


    /**
     * id : -1
     * pId : 0
     * name : 丰宁抽水蓄能电站
     * open : true
     * code : C3
     * section_id : 1
     * add_id : 1
     * d_code : P2-42
     * edit_id : 1
     * type : 1
     * en_type :
     */

    private String id;
    private String pId;
    private String name;
    private String open;
    private String code;
    private String section_id;
    private String add_id;
    private String d_code;
    private String edit_id;
    private String type;
    private String en_type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPId() {
        return pId;
    }

    public void setPId(String pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSection_id() {
        return section_id;
    }

    public void setSection_id(String section_id) {
        this.section_id = section_id;
    }

    public String getAdd_id() {
        return add_id;
    }

    public void setAdd_id(String add_id) {
        this.add_id = add_id;
    }

    public String getD_code() {
        return d_code;
    }

    public void setD_code(String d_code) {
        this.d_code = d_code;
    }

    public String getEdit_id() {
        return edit_id;
    }

    public void setEdit_id(String edit_id) {
        this.edit_id = edit_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEn_type() {
        return en_type;
    }

    public void setEn_type(String en_type) {
        this.en_type = en_type;
    }
}
