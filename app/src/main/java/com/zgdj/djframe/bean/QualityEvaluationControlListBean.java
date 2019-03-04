package com.zgdj.djframe.bean;

import java.io.Serializable;

/**
 * description: 质量管控控制点列表bean
 * author: Created by ShuaiQi_Zhang on 2018/5/29
 * version: 1.0
 */
public class QualityEvaluationControlListBean implements Serializable {


    /**
     * id : 43
     * pid : 41
     * name : 爆破半孔率检查
     * code : null
     * remark : null
     * icon : null
     * type : 3
     * cat : 5
     * sort_id : 42
     */

    private String id;
    private String pid;
    private String name;
    private String code;
    private String remark;
    private String icon;
    private String type;
    private String cat;
    private String sort_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getSort_id() {
        return sort_id;
    }

    public void setSort_id(String sort_id) {
        this.sort_id = sort_id;
    }
}
