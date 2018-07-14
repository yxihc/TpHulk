package com.taopao.mvvmbase.base;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.database.Observable;

import java.util.List;

/**
 * @Author：淘跑
 * @Date: 2018/7/7 12:42
 * @Use：
 */
public class BaseItemViewModel extends ViewModel implements IBaseViewModel {

    public Context mContext;
    public Object mData;

    public BaseItemViewModel(Context context) {
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

    public void setData(Object data) {
        mData = data;
    }
}
