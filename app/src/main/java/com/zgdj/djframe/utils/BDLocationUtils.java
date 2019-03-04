package com.zgdj.djframe.utils;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import org.xutils.x;

/**
 * description:
 * author: Created by ShuaiQi_Zhang on 2018/4/24
 * version:
 */
public enum BDLocationUtils {
    INSTANCE;
    private LocationClient mLocationClient = null;
    private BDLocationListener myListener;
    private BDLocationCallBack callback;

    BDLocationUtils() {
        mLocationClient = new LocationClient(x.app());
        //声明LocationClient类
        myListener = new MyLocationListener();
        initLocation();
        mLocationClient.registerLocationListener(myListener);
    }

    /**
     * 设置定位结果回调
     *
     * @param callback
     */
    public void setCurrentListener(BDLocationCallBack callback) {
        this.callback = callback;
    }

    private BDLocationCallBack getCurrentCallBack() {
        return callback;
    }

    public static class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            //获取定位结果
            if (BDLocationUtils.INSTANCE.getCurrentCallBack() != null) {
                if (bdLocation.getLocType() == BDLocation.TypeGpsLocation) {
                    BDLocationUtils.INSTANCE.getCurrentCallBack().success(bdLocation);
                } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
                    BDLocationUtils.INSTANCE.getCurrentCallBack().success(bdLocation);
                } else if (bdLocation.getLocType() == BDLocation.TypeOffLineLocation) {
                    BDLocationUtils.INSTANCE.getCurrentCallBack().success(bdLocation);
                } else if (bdLocation.getLocType() == BDLocation.TypeServerError) {
                    BDLocationUtils.INSTANCE.getCurrentCallBack().error(bdLocation);
                } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkException) {
                    BDLocationUtils.INSTANCE.getCurrentCallBack().error(bdLocation);
                } else if (bdLocation.getLocType() == BDLocation.TypeCriteriaException) {
                    BDLocationUtils.INSTANCE.getCurrentCallBack().error(bdLocation);
                } else {
                    BDLocationUtils.INSTANCE.getCurrentCallBack().error(bdLocation);

                }
            }
            BDLocationUtils.INSTANCE.stopLocation();
        }
    }

    public interface BDLocationCallBack {
        void success(BDLocation location);

        void error(BDLocation location);
    }

    public void startLocation() {
        if (mLocationClient != null)
            mLocationClient.start();
    }

    public void stopLocation() {
        if (mLocationClient != null)
            mLocationClient.stop();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps
        option.setLocationNotify(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }
}
