package com.zgdj.djframe.net.retrofit;


import com.zgdj.djframe.bean.CommListData;
import com.zgdj.djframe.model.TestModel;

import java.util.ArrayList;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * description: 接口定义
 * author: Created by ShuaiQi_Zhang on 2018/4/20
 * version: 1.0
 */
public interface RequestInterface {

    /**
     * 下载文件示例
     *
     * @return
     */
    @GET("http://app.huijinmoshou.com/android/51ms/moshou/HuiJin_Hjms_v2.4.0.apk")
    Flowable<ResponseBody> downloadFile();


    /**
     * 小区列表
     *
     * @param page          页码
     * @param pcount        数量/页
     * @param qyid          区域id
     * @param sqid          商圈id
     * @param price         小区均价
     * @param distance      x和y坐标
     * @param psort         排序
     * @param buildage      年代范围
     * @param communityid   小区id
     * @param communitytype 小区类型
     * @param keywords      关键字
     * @return
     */
    @FormUrlEncoded()
    @POST("community/{city}/{version}/list")
    Flowable<HttpResult<CommListData>>
    getCommList(@Path("city") String city, @Path("version") String version,
                @Field("page") String page, @Field("pcount") String pcount,
                @Field("qyid") String qyid, @Field("sqid") String sqid,
                @Field("price") String price, @Field("distance") String distance,
                @Field("psort") String psort, @Field("buildage") String buildage,
                @Field("communityid") String communityid, @Field("communitytype") String
                        communitytype,
                @Field("keywords") String keywords);


    @GET("mytest/index/")
    Flowable<HttpResult<ArrayList<TestModel>>> getTask();


    //@Field("possword") String possword
//    @FormUrlEncoded
    @GET("admin/common/getapi/")
    Call<String>
    login(@Query("interfacePassword") String interfacePassword);


}
