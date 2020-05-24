/**
 * Copyright (C), 2017-2020
 * Author: taopao
 * Date: 2020/5/22 下午3:02
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
package com.taopao.hulkbase.delegate.app;

import android.app.Application;
import android.content.Context;
import androidx.annotation.NonNull;
import com.taopao.hulkbase.config.ConfigModule;
import com.taopao.hulkbase.delegate.life.ActivityLifecycle;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @Copyright (C), 2017-2020
 * @Author: TaoPao
 * @Date: 2020/5/22 下午3:02
 * @Description: java类作用描述
 */
public class AppDelegate implements App,AppLifecycles{
    private Application mApplication;
    private List<ConfigModule> mModules;
    protected Application.ActivityLifecycleCallbacks mActivityLifecycle;
    private List<AppLifecycles> mAppLifecycles = new ArrayList<>();
    private List<Application.ActivityLifecycleCallbacks> mActivityLifecycles = new ArrayList<>();
    public AppDelegate(Context context, List<ConfigModule> configModules) {
        this.mModules = configModules;
        if (mModules==null){
            return;
        }
        for (ConfigModule module : mModules) {
            //将框架外部, 开发者实现的 Application 的生命周期回调 (AppLifecycles) 存入 mAppLifecycles 集合 (此时还未注册回调)
            module.injectAppLifecycle(context, mAppLifecycles);
            //将框架外部, 开发者实现的 Activity 的生命周期回调 (ActivityLifecycleCallbacks) 存入 mActivityLifecycles 集合 (此时还未注册回调)
            module.injectActivityLifecycle(context, mActivityLifecycles);
        }
    }
    @Override
    public void attachBaseContext(@NonNull Context context) {
        //遍历 mAppLifecycles, 执行所有已注册的 AppLifecycles 的 attachBaseContext() 方法 (框架外部, 开发者扩展的逻辑)
        for (AppLifecycles lifecycle : mAppLifecycles) {
            lifecycle.attachBaseContext(context);
        }

    }
    @Override
    public void onCreate(@NonNull Application application) {
        this.mApplication = application;

        mActivityLifecycle=new ActivityLifecycle(application);
        //注册框架内部已实现的 Activity 生命周期逻辑
        mApplication.registerActivityLifecycleCallbacks(mActivityLifecycle);

        //注册框架外部, 开发者扩展的 Activity 生命周期逻辑
        //每个 ConfigModule 的实现类可以声明多个 Activity 的生命周期回调
        //也可以有 N 个 ConfigModule 的实现类 (完美支持组件化项目 各个 Module 的各种独特需求)
        for (Application.ActivityLifecycleCallbacks lifecycle : mActivityLifecycles) {
            mApplication.registerActivityLifecycleCallbacks(lifecycle);
        }

        //执行框架外部, 开发者扩展的 App onCreate 逻辑
        for (AppLifecycles lifecycle : mAppLifecycles) {
            lifecycle.onCreate(mApplication);
        }
        this.mModules = null;
    }
    @Override
    public void onTerminate(@NonNull Application application) {
        if (mActivityLifecycle != null) {
            mApplication.unregisterActivityLifecycleCallbacks(mActivityLifecycle);
        }
        if (mActivityLifecycles != null && mActivityLifecycles.size() > 0) {
            for (Application.ActivityLifecycleCallbacks lifecycle : mActivityLifecycles) {
                mApplication.unregisterActivityLifecycleCallbacks(lifecycle);
            }
        }
        if (mAppLifecycles != null && mAppLifecycles.size() > 0) {
            for (AppLifecycles lifecycle : mAppLifecycles) {
                lifecycle.onTerminate(mApplication);
            }
        }
        this.mActivityLifecycle = null;
        this.mActivityLifecycles = null;
        this.mAppLifecycles = null;
        this.mApplication = null;
    }
}