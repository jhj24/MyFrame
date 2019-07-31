package com.zgdj.djframe.bean;

import java.util.List;

public class StandardTreeBean extends BaseTree<StandardTreeBean> {
    /**
     * id : 1
     * pid : 0
     * name : 水利水电勘测设计及其相关行业技术标准
     * children : [{"id":2,"pid":1,"name":"水利水电勘测设计行业技术标准","children":[{"id":6,"pid":2,"name":"能源行业标准(NB、DL)"},{"id":7,"pid":2,"name":"水利行业标准（SL）"},{"id":8,"pid":2,"name":"原水电工程标准（SD、SDJ）"},{"id":9,"pid":2,"name":"中国水电工程顾问集团公司标准（Q CHECC、Q/HYDROCHINA）（参考）"}]},{"id":3,"pid":1,"name":"新能源标准","children":[{"id":10,"pid":3,"name":"新能源标准（NB、FD、GD）"}]},{"id":4,"pid":1,"name":"国家标准","children":[{"id":11,"pid":4,"name":"工程建设国家标准（GB 50000\u2026、GBJ）"},{"id":12,"pid":4,"name":"国家标准（GB）"}]},{"id":5,"pid":1,"name":"工程建设及其它相关行业技术标准","children":[{"id":13,"pid":5,"name":"中国工程建设标准化协会标准（CECS）"},{"id":14,"pid":5,"name":"城镇建设工程标准（CJJ）"},{"id":15,"pid":5,"name":"建筑工程标准（JGJ）"},{"id":16,"pid":5,"name":"交通工程标准（JTG、JTJ、JTS）"},{"id":17,"pid":5,"name":"机械工业标准（NB、JB）"},{"id":18,"pid":5,"name":"测绘标准（CH）"},{"id":19,"pid":5,"name":"环境标准（HJ）"},{"id":20,"pid":5,"name":"档案标准（DA）"},{"id":21,"pid":5,"name":"铁路运输行业标准（TB）"},{"id":22,"pid":5,"name":"土地管理行业标准（TD）"},{"id":23,"pid":5,"name":"国家计量标准（JJF）"},{"id":24,"pid":5,"name":"北京市标准(DBJ、DB11)"},{"id":25,"pid":5,"name":"国家安全生产监督管理总局标准（AQ）"},{"id":26,"pid":5,"name":"公共安全行业标准（GA）"},{"id":27,"pid":5,"name":"农业标准（NY）"},{"id":28,"pid":5,"name":"特种设备安全技术规范（TSG）"},{"id":29,"pid":5,"name":"国家职业卫生标准(GBZ)"}]}]
     */

    private int id;
    private int pid;
    private String name;
    private List<StandardTreeBean> children;


    @Override
    public String getId() {
        return id + "";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<StandardTreeBean> getChildren() {
        return children;
    }

    @Override
    public boolean isRoot() {
        return children != null && children.size() > 0;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setChildren(List<StandardTreeBean> children) {
        this.children = children;
    }
}
