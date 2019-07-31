package com.zgdj.djframe.bean;

/**
 * 树型数据中的通用数据，不想实现接口中的所有方法时，可以实现该抽象类
 * <p>
 * Created by jhj on 17-9-8.
 */

public abstract class BaseTree<T extends IBaseTree<T>> implements IBaseTree<T> {

    private boolean checked;
    private int itemLevels;
    private boolean showChildren;
    private String firstLetterSpelling;
    private String[] arraySpelling;

    @Override
    public boolean isChecked() {
        return checked;
    }

    @Override
    public void setChecked(boolean isChecked) {
        this.checked = isChecked;
    }

    @Override
    public int getItemLevels() {
        return itemLevels;
    }

    @Override
    public void setItemLevels(int levels) {
        this.itemLevels = levels;
    }

    @Override
    public boolean isShowChildren() {
        return showChildren;
    }

    @Override
    public void setShowChildren(boolean isShow) {
        this.showChildren = isShow;
    }

    @Override
    public String getFirstLetterSpelling() {
        return firstLetterSpelling;
    }

    @Override
    public void setFirstLetterSpelling(String spelling) {
        this.firstLetterSpelling = spelling;
    }

    @Override
    public String[] getArraySpelling() {
        return arraySpelling;
    }

    @Override
    public void setArraySpelling(String[] array) {
        this.arraySpelling = array;
    }

}
