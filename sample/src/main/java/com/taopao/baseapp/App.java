package com.taopao.baseapp;

import com.taopao.mvvmbase.base.BaseApplication;
import com.taopao.mvvmbase.crash.CaocConfig;

/**
 * @Author：淘跑
 * @Date: 2018/7/16 22:10
 * @Use：
 */
public class App extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        //崩溃检测
        initCrash();
    }

    /**
     * 初始化崩溃检测
     */
    private void initCrash() {
        CaocConfig.Builder.create()
                .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT) //背景模式,开启沉浸式
                .enabled(true) //是否启动全局异常捕获
                .showErrorDetails(true) //是否显示错误详细信息
                .showRestartButton(true) //是否显示重启按钮
                .trackActivities(true) //是否跟踪Activity
                .minTimeBetweenCrashesMs(2000) //崩溃的间隔时间(毫秒)
                .errorDrawable(com.taopao.mvvmbase.R.drawable.customactivityoncrash_error_image) //错误图标
                .restartActivity(MainActivity.class) //重新启动后的activity
//                .errorActivity(YourCustomErrorActivity.class) //崩溃后的错误activity
//                .eventListener(new YourCustomEventListener()) //崩溃后的错误监听
                .apply();
    }
}
