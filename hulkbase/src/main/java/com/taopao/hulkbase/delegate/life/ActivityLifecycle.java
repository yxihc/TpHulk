/**
 * Copyright (C), 2017-2020
 * Author: taopao
 * Date: 2020/5/22 下午3:52
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
package com.taopao.hulkbase.delegate.life;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.taopao.hulkbase.config.ConfigModule;
import com.taopao.hulkbase.delegate.activity.ActivityDelegate;
import com.taopao.hulkbase.delegate.activity.ActivityDelegateImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @Copyright (C), 2017-2020
 * @Author: TaoPao
 * @Date: 2020/5/22 下午3:52
 * @Description: java类作用描述
 */
public class ActivityLifecycle implements Application.ActivityLifecycleCallbacks {
    private List<ConfigModule> mModules;
    Application mApplication;
    FragmentManager.FragmentLifecycleCallbacks mFragmentLifecycle;
    List<FragmentManager.FragmentLifecycleCallbacks> mFragmentLifecycles = new ArrayList<>();
    private ActivityDelegateImpl mActivityDelegate;

    public ActivityLifecycle(Application application, List<ConfigModule> configModules) {
        this.mModules = configModules;
        this.mApplication = application;
        this.mFragmentLifecycle = new FragmentLifecycle();
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        //配置ActivityDelegate
        ActivityDelegate activityDelegate = fetchActivityDelegate(activity);
        if (activityDelegate != null) {
            activityDelegate.onCreate(savedInstanceState);
        }
        registerFragmentCallbacks(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        ActivityDelegate activityDelegate = fetchActivityDelegate(activity);
        if (activityDelegate != null) {
            activityDelegate.onStart();
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        ActivityDelegate activityDelegate = fetchActivityDelegate(activity);
        if (activityDelegate != null) {
            activityDelegate.onResume();
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        ActivityDelegate activityDelegate = fetchActivityDelegate(activity);
        if (activityDelegate != null) {
            activityDelegate.onPause();
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {
        ActivityDelegate activityDelegate = fetchActivityDelegate(activity);
        if (activityDelegate != null) {
            activityDelegate.onStop();
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        ActivityDelegate activityDelegate = fetchActivityDelegate(activity);
        if (activityDelegate != null) {
            activityDelegate.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        ActivityDelegate activityDelegate = fetchActivityDelegate(activity);
        if (activityDelegate != null) {
            activityDelegate.onDestroy();
            mActivityDelegate = null;
        }
    }

    private ActivityDelegate fetchActivityDelegate(Activity activity) {
        if (mActivityDelegate == null) {
            mActivityDelegate = new ActivityDelegateImpl(activity);
        }
        return mActivityDelegate;
    }

    private void registerFragmentCallbacks(Activity activity) {
        for (ConfigModule module : mModules) {
            module.injectFragmentLifecycle(activity, mFragmentLifecycles);
        }
        if (activity instanceof FragmentActivity) {
            //mFragmentLifecycle 为 Fragment 生命周期实现类, 用于框架内部对每个 Fragment 的必要操作, 如给每个 Fragment 配置 FragmentDelegate
            //注册框架内部已实现的 Fragment 生命周期逻辑
            ((FragmentActivity) activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(mFragmentLifecycle, true);
            Log.e("===", "registerFragmentCallbacks: " );
            //注册框架外部, 开发者扩展的 Fragment 生命周期逻辑
            for (FragmentManager.FragmentLifecycleCallbacks fragmentLifecycle : mFragmentLifecycles) {
                ((FragmentActivity) activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(fragmentLifecycle, true);
            }
        }
        this.mModules = null;
    }

}