package com.zgdj.djframe.utils;

import android.content.Context;
import android.os.Environment;

import org.xutils.common.util.LogUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by LiXiaoSong on 2017/2/16.
 *
 * @Describe 下载文件处理工具
 * <p>
 * 从网络下载中获取到流文件
 */

public class DownloadUtil {
    public static File createFile(Context context, String fileName) {
        File file = null;
        String state = Environment.getExternalStorageState();

        if (state.equals(Environment.MEDIA_MOUNTED)) {
            file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + fileName);
        } else {
            file = new File(context.getCacheDir().getAbsolutePath() + File.separator + fileName);
        }
        String path = file.getAbsolutePath();
        if (file.exists()) {
            file.delete();
        }
        file = new File(path);
        LogUtil.v("即将下载到的位置 " + file.getAbsolutePath());

        return file;

    }

    public static String writeFile2Disk(InputStream is, File file) throws IOException {
        long currentLength = 0;
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            int len;
            byte[] buff = new byte[1024 * 100];//每次读100K
            while ((len = is.read(buff)) != -1) {
                os.write(buff, 0, len);
                currentLength += len;
                LogUtil.v("当前读取进度:" + currentLength);
            }
            return file.getAbsolutePath();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
