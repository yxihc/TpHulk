package com.taopao.mvvmbase.http;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.taopao.mvvmbase.base.MVVMBase;
import com.taopao.mvvmbase.http.cookie.CookieJarImpl;
import com.taopao.mvvmbase.http.cookie.store.PersistentCookieStore;
import com.taopao.mvvmbase.http.interceptor.BaseInterceptor;
import com.taopao.mvvmbase.http.interceptor.CacheInterceptor;
import com.taopao.mvvmbase.http.interceptor.LoggingInterceptor;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitProvider {

    private static Context mContext = MVVMBase.getContext();
    private static Retrofit retrofit;
    private Cache cache = null;
    private static OkHttpClient okHttpClient;
    //超时时间
    private static final int DEFAULT_TIMEOUT = 20;
    //缓存时间
    private static final int CACHE_TIMEOUT = 10 * 1024 * 1024;

    //服务端根路径
    public static String baseUrl = "http://www.oschina.net/";


    private File httpCacheDirectory;

    private static class SingletonHolder {
        private static RetrofitProvider INSTANCE = new RetrofitProvider();
    }

    public static RetrofitProvider getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public static RetrofitProvider getInstance(String url) {
        return new RetrofitProvider(url);
    }

    public static RetrofitProvider getInstance(String url, Map<String, String> headers) {
        return new RetrofitProvider(url, headers);
    }

    private RetrofitProvider() {
        this(baseUrl, null);
    }

    private RetrofitProvider(String url) {
        this(url, null);
    }

    private RetrofitProvider(String url, Map<String, String> headers) {
        if (TextUtils.isEmpty(url)) {
            url = baseUrl;
        }
        if (httpCacheDirectory == null) {
            httpCacheDirectory = new File(mContext.getCacheDir(), "goldze_cache");
        }

        try {
            if (cache == null) {
                cache = new Cache(httpCacheDirectory, CACHE_TIMEOUT);
            }
        } catch (Exception e) {
            Log.e(this.getClass().getName(), "Could not create http cache");
        }
        okHttpClient = new OkHttpClient.Builder()
                .cookieJar(new CookieJarImpl(new PersistentCookieStore(mContext)))
                .cache(cache)
                .addInterceptor(new BaseInterceptor(headers))
                .addInterceptor(new CacheInterceptor(mContext))
                .addInterceptor(new LoggingInterceptor())
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(8, 15, TimeUnit.SECONDS))
                // 这里你可以根据自己的机型设置同时连接的个数和时间，我这里8个，和每个保持时间为10s
                .build();
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(url)
                .build();

    }

    /**
     * create you ApiService
     * Create an implementation of the API endpoints defined by the {@code service} interface.
     */
    public <T> T create(final Class<T> service) {
        if (service == null) {
            throw new RuntimeException("Api service is null!");
        }
        return retrofit.create(service);
    }

    /**
     * /**
     * execute your customer API
     * For example:
     * MyApiService service =
     * RetrofitClient.getInstance(MainActivity.this).create(MyApiService.class);
     * <p>
     * RetrofitClient.getInstance(MainActivity.this)
     * .execute(service.lgon("name", "password"), subscriber)
     * * @param subscriber
     */

    public static <T> T execute(Observable<T> observable, Observer<T> subscriber) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

        return null;
    }


}
