package com.taopao.tphulk.init;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class FragmentLifecycleCallbacksImpl extends FragmentManager.FragmentLifecycleCallbacks {

    @Override
    public void onFragmentAttached(FragmentManager fm, Fragment f, Context context) {

    }

    @Override
    public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
        Log.e("====", "FragmentLifecycleCallbacksImpl:onActivityCreated: ");
        // 在配置变化的时候将这个 Fragment 保存下来,在 Activity 由于配置变化重建时重复利用已经创建的 Fragment。
        // https://developer.android.com/reference/android/app/Fragment.html?hl=zh-cn#setRetainInstance(boolean)
        // 如果在 XML 中使用 <Fragment/> 标签,的方式创建 Fragment 请务必在标签中加上 android:id 或者 android:tag 属性,否则 setRetainInstance(true) 无效
        // 在 Activity 中绑定少量的 Fragment 建议这样做,如果需要绑定较多的 Fragment 不建议设置此参数,如 ViewPager 需要展示较多 Fragment
        f.setRetainInstance(true);
    }

    @Override
    public void onFragmentViewCreated(FragmentManager fm, Fragment f, View v, Bundle savedInstanceState) {
    }

    @Override
    public void onFragmentActivityCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
    }

    @Override
    public void onFragmentStarted(FragmentManager fm, Fragment f) {
    }

    @Override
    public void onFragmentResumed(FragmentManager fm, Fragment f) {
    }

    @Override
    public void onFragmentPaused(FragmentManager fm, Fragment f) {
    }

    @Override
    public void onFragmentStopped(FragmentManager fm, Fragment f) {
    }

    @Override
    public void onFragmentSaveInstanceState(FragmentManager fm, Fragment f, Bundle outState) {
    }

    @Override
    public void onFragmentViewDestroyed(FragmentManager fm, Fragment f) {
    }

    @Override
    public void onFragmentDestroyed(FragmentManager fm, Fragment f) {
    }

    @Override
    public void onFragmentDetached(FragmentManager fm, Fragment f) {
    }
}
