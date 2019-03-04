package com.zgdj.djframe.net.retrofit;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by jh on 2017/4/5.
 */

public class MultipartPicHelp {
    /***
     * 通过List<MultipartBody.Part>这种形式上传多张图片
     * @param files
     * @return
     */
    public static List<MultipartBody.Part> filesToMultipartBodyPart2(List<File> files) {
        List<MultipartBody.Part> parts = new ArrayList<>(files.size());
        for (File file : files) {
            // TODO: 16-4-2  这里为了简单起见，没有判断file的类型
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("uploadpics", file.getName(), requestBody);
            parts.add(part);
        }
        return parts;
    }

    /***
     * 通过List<MultipartBody.Part>这种形式上传多张图片
     * @param filesPaths
     * @return
     */
    public static List<MultipartBody.Part> filesToMultipartBodyPart(List<String> filesPaths) {
        List<MultipartBody.Part> parts = new ArrayList<>();
        for (String path : filesPaths) {
            // TODO: 16-4-2  这里为了简单起见，没有判断file的类型
            File file = new File(path);
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("uploadpics", file.getName(), requestBody);
            parts.add(part);
        }
        return parts;
    }

    /**
     * 通过MultipartBody上传图片
     *
     * @param files
     * @return
     */
    public static MultipartBody filesToMultipartBody(List<File> files) {
        MultipartBody.Builder builder = new MultipartBody.Builder();

        for (File file : files) {
            // TODO: 16-4-2  这里为了简单起见，没有判断file的类型
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
            builder.addFormDataPart("uploadpics", file.getName());
        }

        builder.setType(MultipartBody.FORM);
        MultipartBody multipartBody = builder.build();
        return multipartBody;
    }

    /**
     * 上传一张图片
     *
     * @param file
     * @return
     */
    public static MultipartBody.Part filesToMultipartBodyPart(File file) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
        MultipartBody.Part mp = MultipartBody.Part.createFormData("uploadpic", file.getName(), requestBody);
        return mp;
    }

}
