package com.zgdj.djframe.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.zgdj.djframe.bean.BaseTree;

import java.util.List;

public class DocumentUserModel {


    /**
     * code : 1
     * data : [{"id":510,"name":"余晶","admin_group_id":19},{"id":1,"name":"超级管理员","admin_group_id":1}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean extends BaseTree<DataBean> implements Parcelable {
        /**
         * id : 510
         * name : 余晶
         * admin_group_id : 19
         */

        private int id;
        private String name;
        private int pid;
        private int admin_group_id;
        private List<DataBean> children;

        public DataBean(int id, String name, int pid, int admin_group_id, List<DataBean> children) {
            this.id = id;
            this.name = name;
            this.pid = pid;
            this.admin_group_id = admin_group_id;
            this.children = children;
        }

        @Override
        public String getId() {
            return id + "";
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public List<DataBean> getChildren() {
            return children;
        }

        @Override
        public boolean isRoot() {
            return children != null && children.size() > 0;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public int getAdmin_group_id() {
            return admin_group_id;
        }

        public void setAdmin_group_id(int admin_group_id) {
            this.admin_group_id = admin_group_id;
        }

        public void setChildren(List<DataBean> children) {
            this.children = children;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.name);
            dest.writeInt(this.pid);
            dest.writeInt(this.admin_group_id);
            dest.writeTypedList(this.children);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.id = in.readInt();
            this.name = in.readString();
            this.pid = in.readInt();
            this.admin_group_id = in.readInt();
            this.children = in.createTypedArrayList(DataBean.CREATOR);
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }
}
