package com.taopao.hulkbase.app;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

/**
 * @Copyright (C), 2017-2020
 * @Author: TaoPao
 * @Date: 2020/5/22 下午2:59
 * @Description: 用于代理 {@link Application} 的生命周期
 */
public interface AppLifecycles {

    void attachBaseContext(@NonNull Context base);

    void onCreate(@NonNull Application application);

    void onTerminate(@NonNull Application application);
}
