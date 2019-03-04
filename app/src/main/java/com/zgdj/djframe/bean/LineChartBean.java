package com.zgdj.djframe.bean;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * description: 折线图bean
 * author: Created by ShuaiQi_Zhang on 2018/5/23
 * version:
 */
public class LineChartBean implements Serializable {

    private LinkedList<Double> data;
    private String name;
    private int color;

    public LineChartBean(LinkedList<Double> data, String name, int color) {
        this.data = data;
        this.name = name;
        this.color = color;
    }

    public LinkedList<Double> getData() {
        return data;
    }

    public void setData(LinkedList<Double> data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
