package com.taopao.mvvmbase.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.airbnb.lottie.utils.Utils;
import com.taopao.mvvmbase.R;
import com.taopao.mvvmbase.crash.CaocConfig;

/**
 * @Author： 淘跑
 * @Date: 2018/7/5 14:59
 * @Use：
 */

public class BaseApplication extends Application {
    private static BaseApplication sInstance;
    //以下属性应用于整个应用程序，合理利用资源，减少资源浪费
    private static Context sContext;


    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        sContext = getApplicationContext();
        //初始化工具
        MVVMBase.init(this);
        //注册监听每个activity的生命周期,便于堆栈式管理
        registerActivityLifecycleCallbacks(mCallbacks);
    }

    /**
     * 获得当前app运行的Application
     */
    public static BaseApplication getInstance() {
        return sInstance;
    }


    /**
     * 获得当前app运行Context
     *
     * @return Context
     */
    public static Context getContext() {
        return sContext;
    }

    private ActivityLifecycleCallbacks mCallbacks = new ActivityLifecycleCallbacks() {

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            AppManager.getInstance().addActivity(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {
        }

        @Override
        public void onActivityResumed(Activity activity) {
        }

        @Override
        public void onActivityPaused(Activity activity) {
        }

        @Override
        public void onActivityStopped(Activity activity) {
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            AppManager.getInstance().removeActivity(activity);
        }
    };


}
