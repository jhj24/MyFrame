package com.zgdj.djframe.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * description:
 * author: Created by ShuaiQi_Zhang on 2018/4/20
 * version:
 */
public class CommunityBean implements Serializable {

    /**
     * communityid : 488
     * companysid : 1
     * address : 城南嘉园益明园
     * rentcount : 3
     * salecount : 2
     * startData : 2003-01
     * price : 81021
     * imgurl : null
     * qyname : 丰台
     * sqname : 马家堡
     * distance : null
     * x : 116.36798
     * y : 39.83515
     * ratio : ↑11.8%
     * monthCjCount : null
     * sectionname : null
     * communitytype : null
     * gpAvgPrice : [72626,72626,72626,72626,72626,72626,72626,72626,72626,72626,66839,72463]
     */

    private int communityid; //小区id
    private int companysid; //城市id
    private String address;//小区名称 -- 这。。。。
    private int rentcount; //小区租房数
    private int salecount; //小区二手房数
    private String startData;//小区年代
    private String price;//小区均价
    private String imgurl;//小区图片
    private String qyname;//小区所属区域
    private String sqname;//小区所属商圈
    private String distance;//距离
    private String x;//小区X(经度)
    private String y;//小区Y(维度)
    private String ratio;//小区环比上涨百分比
    private String monthCjCount;//30天内成交套数
    private String sectionname;//别名
    private String communitytype;//类型
    private ArrayList<Integer> gpAvgPrice;//小区每月挂牌均价

    public int getCommunityid() {
        return communityid;
    }

    public void setCommunityid(int communityid) {
        this.communityid = communityid;
    }

    public int getCompanysid() {
        return companysid;
    }

    public void setCompanysid(int companysid) {
        this.companysid = companysid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRentcount() {
        return rentcount;
    }

    public void setRentcount(int rentcount) {
        this.rentcount = rentcount;
    }

    public int getSalecount() {
        return salecount;
    }

    public void setSalecount(int salecount) {
        this.salecount = salecount;
    }

    public String getStartData() {
        return startData;
    }

    public void setStartData(String startData) {
        this.startData = startData;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getQyname() {
        return qyname;
    }

    public void setQyname(String qyname) {
        this.qyname = qyname;
    }

    public String getSqname() {
        return sqname;
    }

    public void setSqname(String sqname) {
        this.sqname = sqname;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }

    public String getMonthCjCount() {
        return monthCjCount;
    }

    public void setMonthCjCount(String monthCjCount) {
        this.monthCjCount = monthCjCount;
    }

    public String getSectionname() {
        return sectionname;
    }

    public void setSectionname(String sectionname) {
        this.sectionname = sectionname;
    }

    public String getCommunitytype() {
        return communitytype;
    }

    public void setCommunitytype(String communitytype) {
        this.communitytype = communitytype;
    }

    public ArrayList<Integer> getGpAvgPrice() {
        return gpAvgPrice;
    }

    public void setGpAvgPrice(ArrayList<Integer> gpAvgPrice) {
        this.gpAvgPrice = gpAvgPrice;
    }
}
