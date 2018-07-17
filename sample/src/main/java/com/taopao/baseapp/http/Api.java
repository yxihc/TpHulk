package com.taopao.baseapp.http;


import com.taopao.baseapp.model.AppVersionResponse;
import com.taopao.baseapp.model.BaseResponse;
import com.taopao.baseapp.model.ImgListInfo;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * @Author：淘跑
 * @Date: 2018/7/16 20:17
 * @Use：
 */
public interface Api {

    public static final String BASE_URL = "http://47.97.214.237/yjy/";


    //获取app版本
    @POST("api/common/appVerion")
    @FormUrlEncoded
    Observable<BaseResponse<AppVersionResponse>> getAppVersion(@Field("deviceType") int type);


    //获取app版本
    @POST("api/common/appVerion")
    @FormUrlEncoded
    Observable<BaseResponse<AppVersionResponse>> getAppVersion1(@FieldMap HashMap<String, Object> hashMap);

/////////////////////////////////////////////////////////////////////////////////////////////////////

    public static final String IMGLIST = "http://gank.io/api/data/福利/10/1";


    public static final String IMGLIST_PAGE = "http://gank.io/api/data/福利/10/";

    //直接请求一个完整后面带占位符
    @GET(IMGLIST_PAGE + "{page}")
    Observable<ImgListInfo> getImgListPage(@Path("page") String page);

}
