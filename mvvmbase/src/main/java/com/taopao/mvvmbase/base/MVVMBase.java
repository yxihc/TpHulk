package com.taopao.mvvmbase.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;

/**
 * @Author： 淘跑
 * @Date: 2018/7/5 19:23
 * @Use： (必须)再 Application 的 onCreate方法里注册 MVVMBase.init(this);
 */

public class MVVMBase {
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    private MVVMBase() {
        throw new UnsupportedOperationException("you can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(@NonNull final Context context) {
        MVVMBase.context = context.getApplicationContext();
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (context != null) {
            return context;
        }
        throw new NullPointerException("should be initialized in application: MVVMBase.init(this);");
    }
}
