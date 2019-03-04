package com.zgdj.djframe.bean.treebean;

import com.zgdj.djframe.R;
import com.zgdj.djframe.view.treeview.LayoutItem;

/**
 * BranchNode
 *
 * @author 林zero
 * @date 2018/1/14
 */

public class BranchNode implements LayoutItem {
    private String name;
    private String sectionId;

    public BranchNode(String name) {
        this.name = name;
    }

    public BranchNode(String name, String sectionId) {
        this.name = name;
        this.sectionId = sectionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_tree_view_branch;
    }

    @Override
    public int getToggleId() {
        return R.id.ivNode;
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
