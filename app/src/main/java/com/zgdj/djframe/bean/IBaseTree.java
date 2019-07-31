package com.zgdj.djframe.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 树型数据通用接口
 * <p>
 * Created by jhj on 17-9-8.
 */

public interface IBaseTree<T extends IBaseTree<T>> extends Serializable {


    /**
     * 唯一标示，在一些判断中用到
     *
     * @return String
     */
    String getId();

    /**
     * 获取用于排序的字符串
     *
     * @return String
     */
    String getName();

    /**
     * 获取所有子类
     *
     * @return list
     */
    List<T> getChildren();//

    /**
     * 是否是根节点
     *
     * @return boolean
     */
    boolean isRoot();

    /**
     * 是否选中
     *
     * @return boolean
     */
    boolean isChecked();


    /**
     * 设置是否选中
     *
     * @param isChecked boolean
     */
    void setChecked(boolean isChecked);

    /**
     * 获取item所在级别，从０开始
     *
     * @return int
     */
    int getItemLevels();

    /**
     * 设置item级别
     *
     * @param levels int
     */
    void setItemLevels(int levels);

    /**
     * 有子类时，之类是否显示
     *
     * @return boolean
     */
    boolean isShowChildren();

    /**
     * 设置子类是否显示
     *
     * @param isShow boolean
     */
    void setShowChildren(boolean isShow);

    /**
     * 获取第一个字符的全拼
     *
     * @return String
     */
    String getFirstLetterSpelling();

    /**
     * 设置第一个字符的全拼
     *
     * @param spelling String
     */
    void setFirstLetterSpelling(String spelling);

    /**
     * 获取所有字符的全拼，以数组的形式
     *
     * @return string
     */
    String[] getArraySpelling();

    /**
     * 设置所有字符的全拼，以数组的形式
     *
     * @param array array
     */
    void setArraySpelling(String[] array);


}
