package com.zgdj.djframe.bean.treebean;

import com.zgdj.djframe.R;
import com.zgdj.djframe.view.treeview.LayoutItem;

/**
 * LeafNode
 *
 * @author 林zero
 * @date 2018/1/14
 */

public class LeafNode implements LayoutItem {
    private String name;
    private String id;
    private String type;

    public LeafNode(String name) {
        this.name = name;
    }

    public LeafNode(String name, String id, String type) {
        this.name = name;
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_tree_view_leaf;
    }

    @Override
    public int getToggleId() {
        return 0;
    }

    @Override
    public int getCheckedId() {
        return R.id.ivCheck;
    }

    @Override
    public int getClickId() {
        return R.id.tvName;
    }
}
