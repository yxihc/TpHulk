package com.taopao.mvvmbase;

import android.app.Application;

import com.taopao.mvvmbase.crash.CaocConfig;

/**
 * @Author： 淘跑
 * @Date: 2018/7/5 14:59
 * @Use：
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CaocConfig.Builder.create()
                .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT) //背景模式,开启沉浸式
                .enabled(true) //是否启动全局异常捕获
                .showErrorDetails(true) //是否显示错误详细信息
                .showRestartButton(true) //是否显示重启按钮
                .trackActivities(true) //是否跟踪Activity
                .minTimeBetweenCrashesMs(2000) //崩溃的间隔时间(毫秒)
                .errorDrawable(R.drawable.customactivityoncrash_error_image) //错误图标
                .restartActivity(MainActivity.class) //重新启动后的activity
//                .errorActivity(YourCustomErrorActivity.class) //崩溃后的错误activity
//                .eventListener(new YourCustomEventListener()) //崩溃后的错误监听
                .apply();
    }
}
