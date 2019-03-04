package com.zgdj.djframe.constant;

/**
 * description: 配置
 * author: Created by ShuaiQi_Zhang on 2018/4/20
 * version: 1.0
 */
public class Config {

    private Config() {
        throw new IllegalStateException("不允许实例化");
    }

    public static double lat;//默认经度
    public static double lon;//默认纬度
}
