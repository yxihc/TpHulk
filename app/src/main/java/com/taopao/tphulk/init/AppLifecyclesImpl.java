package com.taopao.tphulk.init;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import com.taopao.hulkbase.delegate.app.AppLifecycles;

public class AppLifecyclesImpl implements AppLifecycles {

    @Override
    public void attachBaseContext(@NonNull Context base) {
    }
    @Override
    public void onCreate(@NonNull Application application) {
        //冷启动优化
        Intent intent = new Intent(application, InitService.class);
        application.startService(intent);
    }

    @Override
    public void onTerminate(@NonNull Application application) {

    }

}
