package com.zgdj.djframe.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * description: 日期工具类
 * author: Created by Mr.Zhang on 2018/6/27
 */
public class DateUtil {

    /**
     * 获取当前时间 ：格式 yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getCurrentDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

}
