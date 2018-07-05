package com.taopao.mvvmbase.http.interceptor.logging;

import okhttp3.internal.platform.Platform;
/**
 * @Author： 淘跑
 * @Date: 2018/7/5 11:43
 * @Use：
 */

@SuppressWarnings({"WeakerAccess", "unused"})
public interface Logger {
    void log(int level, String tag, String msg);

    Logger DEFAULT = new Logger() {
        @Override
        public void log(int level, String tag, String message) {
            Platform.get().log(level, message, null);
        }
    };
}
