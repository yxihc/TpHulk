package com.taopao.mvvmbase;


import com.taopao.mvvmbase.http.BaseResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @Author: 淘跑
 * @Data: 2018/4/28 10:32
 * @Use: 接口地址
 */
public interface Api {

    //测试地址
//    public static final String BASE_URL = "http://116.62.161.77/yjy/";

    //测试地址1
    public static final String BASE_URL = "http://47.97.214.237/yjy/";

    //正式地址
//    public static final String BASE_URL = "http://www.sanqianxuan.com/yjy/";

    //获取app版本
    @POST("api/common/appVerion")
    @FormUrlEncoded
    Observable<BaseResponse<AppVersionResponse>> getAppVersion(@Field("deviceType") int type);

    //广告图
//    @POST("api/common/loadingImg")
//    Observable<BaseResponse<SplashImageResponse>> getSplashImage();
    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<启动页<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

}
