package com.zgdj.djframe.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.widget.Button;

import com.zgdj.djframe.R;
import com.zgdj.djframe.view.CustomerDialog;

import java.io.File;

import static com.zgdj.djframe.constant.Constant.CHOICE_FROM_ALBUM_REQUEST_CODE;
import static com.zgdj.djframe.constant.Constant.TAKE_PHOTO_REQUEST_CODE;

/**
 * description: 相机 + 相册 相关工具 7.0很操蛋！！！
 * <p>
 * author: Created by ShuaiQi_Zhang on 2018/4/26
 * version:
 */
public class CameraUtils {

    private Activity activity;
    //    private Fragment context;
//    private Context mContext;
    private String tempName = "";//照相时的临时文件名
    private Uri photoUri = null;
    private CustomerDialog dialog; //弹框
    private File photoFile = null;//照片file


//    public CameraUtils(Fragment fragment) {
//        this.context = fragment;
//        mContext = fragment.getContext();
//    }

    public CameraUtils(Activity activity) {
        this.activity = activity;
    }

    // 检查是否有相机--- 暂时无用
    private void checkCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {//判断是否有相机应用
//            startActivityForResult(takePictureIntent, REQ_THUMB);
        }
    }


    //显示Dialog
    public void showDialog() {
        if (dialog == null) {
            dialog = new CustomerDialog(activity, R.layout.dialog_camera);
            dialog.setDlgIfClick(true);
            dialog.setOnCustomerViewCreated((window, dlg) -> {
                Button camera = window.findViewById(R.id.dialog_camera_photograph);//拍照
                Button album = window.findViewById(R.id.dialog_camera_album);//相册

                camera.setOnClickListener(v -> { //调用系统相机
                    dialog.dismissDlg();
                    getPhotoByTakePicture(TAKE_PHOTO_REQUEST_CODE);

                });
                album.setOnClickListener(v -> { //调用系统相册
                    dialog.dismissDlg();
                    getPhotoFromGallery(CHOICE_FROM_ALBUM_REQUEST_CODE);

                });
            });
        }
        dialog.showDlg();
    }

    //显示Dialog
    public void showDialog(int photoCode, int albumCode) {
        if (dialog == null) {
            dialog = new CustomerDialog(activity, R.layout.dialog_camera);
            dialog.setDlgIfClick(true);
            dialog.setOnCustomerViewCreated((window, dlg) -> {
                Button camera = window.findViewById(R.id.dialog_camera_photograph);//拍照
                Button album = window.findViewById(R.id.dialog_camera_album);//相册

                camera.setOnClickListener(v -> { //调用系统相机
                    dialog.dismissDlg();
                    getPhotoByTakePicture(photoCode);

                });
                album.setOnClickListener(v -> { //调用系统相册
                    dialog.dismissDlg();
                    getPhotoFromGallery(albumCode);

                });
            });
        }
        dialog.showDlg();
    }

    /**
     * 获取拍照Uri
     *
     * @return
     */
    public Uri getPhotoUri() {
        return photoUri;
    }

    /**
     * 获取拍照File
     *
     * @return
     */
    public File getPhotoFile() {
        return photoFile;
    }

    /**
     * 拍照获取图片
     *
     * @author: LiXiaosong
     * @date:2014-10-8
     */
    private void getPhotoByTakePicture(int requestCode) {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            tempName = System.currentTimeMillis() + ".jpg";
            File file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DCIM).getAbsolutePath()
                    + File.separator + tempName);
            /**
             * 因 Android 7.0 开始，不能使用 file:// 类型的 Uri 访问跨应用文件，否则报异常，
             * 因此我们这里需要使用内容提供器，FileProvider 是 ContentProvider 的一个子类，
             * 我们可以轻松的使用 FileProvider 来在不同程序之间分享数据(相对于 ContentProvider 来说)
             */
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                photoUri = FileProvider.getUriForFile(activity,
                        activity.getPackageName() + ".provider", file);
            } else {
                photoUri = Uri.fromFile(file); // Android 7.0 以前使用原来的方法来获取文件的 Uri
            }
            photoFile = file;
            tempName = file.toString();
            Intent getImageByCamera = new Intent(
                    "android.media.action.IMAGE_CAPTURE");
            getImageByCamera.putExtra(MediaStore.Images.ImageColumns.ORIENTATION, 0);
            getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            activity.startActivityForResult(getImageByCamera, requestCode);
        } else {
            ToastUtils.showShort("请确认已经插入SD卡");
        }
    }

    /**
     * 从图库获取图片
     *
     * @param requestCode
     * @author: LiXiaosong
     * @date:2014-10-8
     */
    private void getPhotoFromGallery(int requestCode) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        activity.startActivityForResult(intent, requestCode);
    }
}
