package com.taopao.baseapp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Author: 淘跑
 * @Data: 2018/4/28 21:32
 * @Use: 配置Retrofit（配置网络缓存cache、配置持久cookie免登录）
 */

public class ApiRetrofit {
    private static ApiRetrofit mApiRetrofit;
    private static Api mApi;
    private static OkHttpClient mClient;

    public ApiRetrofit() {
        mApi = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .client(getClient())
                .addConverterFactory(buildGsonConverterFactory())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(Api.class);
    }

    /**
     * 得到实例化的api
     *
     * @return Api
     */
    public Api getApi() {
        return mApi;
    }

    public static Api getInstance() {
        //单利模式获取retrofit
        if (mApiRetrofit == null) {
            synchronized (ApiRetrofit.class) {
                mApiRetrofit = new ApiRetrofit();
            }
        }
        return mApiRetrofit.getApi();
    }

    /**
     * 实例化OkHttpClient
     *
     * @return OkHttpClient
     */
    public static OkHttpClient getClient() {
        mClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true) // 自动重连
                .connectTimeout(20, TimeUnit.SECONDS) // 20秒连接超时
                .readTimeout(20, TimeUnit.SECONDS)    // 20秒读取超时
                .writeTimeout(20, TimeUnit.SECONDS)   // 20秒写入超时
                .build();
        return mClient;
    }


    /**
     * 构建GSON转换器
     *
     * @return GsonConverterFactory
     */
    private static GsonConverterFactory buildGsonConverterFactory() {
        GsonBuilder builder = new GsonBuilder();
        builder.setLenient();
        Gson gson = builder.create();
        return GsonConverterFactory.create(gson);
    }

    /**
     * 构建请求头拦截器
     *
     * @return Interceptor
     */
    private static Interceptor buildHeaderInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .addHeader("Content-Type", "application/json")
//                .addHeader("Content-Type", "application/json; charset=utf-8")
//                .addHeader("Accept-Encoding", "gzip, deflate")
//                .addHeader("Connection", "keep-alive")
//                .addHeader("Accept", "*/*")
//                .addHeader("Cookie", "add cookies here")
                        .build();
                return chain.proceed(request);
            }
        };
    }

    /**
     * 构建log拦截器
     *
     * @return Interceptor
     */
    private static Interceptor buildLoggingInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {


                //--------------------------------------第一种log输出方式------------------------------------//

//                //这个chain里面包含了request和response，所以你要什么都可以从这里拿
//                Request request = chain.request();
//                long t1 = System.nanoTime();//请求发起的时间
//                LogUtils.f("-----------------------------------Start-----------------------------------");
//                LogUtils.f(String.format("发送请求  %s %n%s%n%s",
//                        request.url(), "connection: " + chain.connection(), "headers: " + request.headers()));
//                Response response = chain.proceed(request);
//                long t2 = System.nanoTime();//收到响应的时间
//                //这里不能直接使用response.body().string()的方式输出日志
//                //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
//                //个新的response给应用层处理
//                ResponseBody responseBody = response.peekBody(1024 * 1024);
//                LogUtils.f(String.format("接收响应: %s %n返回JSON: %s%n请求时长: %.1fms%n%s",
//                        response.request().url(),
//                        responseBody.string(),
//                        (t2 - t1) / 1e6d,
//                        response.headers()));
//                LogUtils.f("-----------------------------------End:"+(t2 - t1) / 1e6d+"毫秒--------------------------");
//                LogUtils.f(String.format("%s",responseBody.string()));


                //-------------------------------第二种log输出方式:格式化JSON 狂拽酷炫吊炸天------------------------------------//


                //这个chain里面包含了request和response，所以你要什么都可以从这里拿
                Request request = chain.request();
                long t1 = System.nanoTime();//请求发起的时间

                StringBuilder sb = new StringBuilder();
                if (request.body() instanceof FormBody) {
                    FormBody body = (FormBody) request.body();

                    if (body != null) {
                        for (int i = 0; i < body.size(); i++) {
                            if (i == body.size() - 1) {
                                sb.append(body.encodedName(i) + " = " + body.encodedValue(i));
                            } else {
                                sb.append(body.encodedName(i) + " = " + body.encodedValue(i) + ",");
                            }
                        }
                    }
                }


                Response response = chain.proceed(request);
                long t2 = System.nanoTime();//收到响应的时间
                //这里不能直接使用response.body().string()的方式输出日志
                //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
                //个新的response给应用层处理
                ResponseBody responseBody = response.peekBody(1024 * 1024);
                String json = responseBody.string();
//                LogUtils.printJson(json,response.headers().toString());
                return response;
            }
        };
    }

    /**
     * 构建缓存拦截器
     *
     * @return Interceptor
     */
    private static Interceptor buildCacheInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                //通过 CacheControl 控制缓存数据
                CacheControl.Builder cacheBuilder = new CacheControl.Builder();
                cacheBuilder.maxAge(365, TimeUnit.SECONDS);//这个是控制缓存的最大生命时间
                cacheBuilder.maxStale(365, TimeUnit.DAYS);//这个是控制缓存的过时时间
                CacheControl cacheControl = cacheBuilder.build();                // 响应内容处理
                Request request = chain.request();

                // 在线时缓存5分钟
                // 离线时缓存4周
                Response response = chain.proceed(request);
                return response;
            }


//                //通过 CacheControl 控制缓存数据
//                CacheControl.Builder cacheBuilder = new CacheControl.Builder();
//                cacheBuilder.maxAge(0, TimeUnit.SECONDS);//这个是控制缓存的最大生命时间
//                cacheBuilder.maxStale(365, TimeUnit.DAYS);//这个是控制缓存的过时时间
//                CacheControl cacheControl = cacheBuilder.build();
//
//                //设置拦截器
//                Request request = chain.request();
//                if (!NetUtils.isNetworkAvailable(BaseApp.getContext())) {
//                    request = request.newBuilder()
//                            .cacheControl(cacheControl)
//                            .build();
//                }
//
//                Response originalResponse = chain.proceed(request);
//                if (NetUtils.isNetworkAvailable(BaseApp.getContext())) {
//                    int maxAge = 0;//read from cache
//                    return originalResponse.newBuilder()
//                            .removeHeader("Pragma")
//                            .header("Cache-Control", "public ,max-age=" + maxAge)
//                            .build();
//                } else {
//                    int maxStale = 60 * 60 * 24 * 28;//tolerate 4-weeks stale
//                    return originalResponse.newBuilder()
//                            .removeHeader("Prama")
//                            .header("Cache-Control", "poublic, only-if-cached, max-stale=" + maxStale)
//                            .build();
//                }
//
//            }
        };
    }


}
