package com.zgdj.djframe.model;

public class FileModel {


    /**
     * code : 1
     * data : {"filename":"4.jpg","path":"/uploads/atlas/atlas_thumb/file/20190729/d0096ec6c83575373e3a21d129ff8fef.jpg"}
     */

    private int code;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * filename : 4.jpg
         * path : /uploads/atlas/atlas_thumb/file/20190729/d0096ec6c83575373e3a21d129ff8fef.jpg
         */

        private String filename;
        private String path;

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}
