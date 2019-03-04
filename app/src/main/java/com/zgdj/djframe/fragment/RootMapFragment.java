package com.zgdj.djframe.fragment;

import android.Manifest;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.zgdj.djframe.R;
import com.zgdj.djframe.base.BaseFragment;
import com.zgdj.djframe.utils.FragmentUtils;
import com.zgdj.djframe.utils.Logs;
import com.zgdj.djframe.utils.MPermissionUtils;

import static android.content.Context.SENSOR_SERVICE;


/**
 * description: map Fragment
 * author: Created by ShuaiQi_Zhang on 2018/4/19
 * version: 1.0
 */
public class RootMapFragment extends BaseFragment implements FragmentUtils.OnBackClickListener, SensorEventListener {

    // 定位相关
    private LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private MyLocationConfiguration.LocationMode mCurrentMode;
    private SensorManager mSensorManager;
    private Double lastX = 0.0;
    private int mCurrentDirection = 0;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private float mCurrentAccracy;

    private MapView mMapView;
    private BaiduMap mBaiduMap;

    // UI相关
    boolean isFirstLoc = true; // 是否首次定位
    private MyLocationData locData;

    public static RootMapFragment newInstance() {
        Bundle args = new Bundle();
        RootMapFragment fragment = new RootMapFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_root_1;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        mMapView = view.findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();// 地图初始化
        mSensorManager = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);//获取传感器管理服务
    }

    /**
     * //SDK在Android 6.0下需要进行运行检测的权限如下：
     * Manifest.permission.ACCESS_COARSE_LOCATION,
     * Manifest.permission.ACCESS_FINE_LOCATION,
     * Manifest.permission.WRITE_EXTERNAL_STORAGE,
     * Manifest.permission.READ_EXTERNAL_STORAGE,
     * Manifest.permission.READ_PHONE_STATE
     */

    @Override
    public void doBusiness() {
        checkPermission();
    }

    // 初始化地图相关配置
    private void initBMap() {
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(getActivity());
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();

        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
                mCurrentMode, true, null));
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.overlook(0);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser) {
            //不可见时 ，取消注册传感器监听
            mSensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onWidgetClick(View view) {

    }

    @Override
    public boolean onBackClick() {
        return false;
    }

    @Override
    public void onSensorChanged(SensorEvent event) { //方位改变监听
        double x = event.values[SensorManager.DATA_X];
        if (Math.abs(x - lastX) > 1.0) {
            mCurrentDirection = (int) x;
            locData = new MyLocationData.Builder()
                    .accuracy(mCurrentAccracy)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(mCurrentLat)
                    .longitude(mCurrentLon).build();
            mBaiduMap.setMyLocationData(locData);
        }
        lastX = x;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /**
     * 定位SDK监听函数
     */
    private class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            mCurrentLat = location.getLatitude();
            mCurrentLon = location.getLongitude();
            mCurrentAccracy = location.getRadius();
            locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

    //动态权限检测并获取
    private void checkPermission() {
        //权限判断
        String[] permissions = new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE};

        MPermissionUtils.requestPermissionsResult(RootMapFragment.this, 1, permissions,
                new MPermissionUtils.OnPermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        initBMap();
                    }

                    @Override
                    public void onPermissionDenied() {
                        Logs.toast(getActivity(), "权限关闭，该功能不可用！");
//                        MPermissionUtils.showTipsDialog(getActivity());
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        MPermissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onResume() {
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
        super.onResume();
        //为系统的方向传感器注册监听器
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
        super.onPause();
        Logs.debug("mMapView --- onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Logs.debug("mMapView --- onStop");
    }

    @Override
    public void onDestroy() {
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
        super.onDestroy();
    }
}
