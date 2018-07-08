package com.taopao.mvvmbase.base;

import android.arch.lifecycle.ViewModel;
import android.content.Context;

/**
 * @Author：淘跑
 * @Date: 2018/7/7 12:42
 * @Use：
 */

public class BaseViewModel extends ViewModel implements IBaseViewModel {
    public Context mContext;

    public BaseViewModel() {
    }

    public BaseViewModel(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void registerRxBus() {

    }

    @Override
    public void removeRxBus() {

    }
}
