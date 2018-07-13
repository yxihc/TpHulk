package com.taopao.mvvmbase.base;

import android.arch.lifecycle.ViewModel;
import android.content.Context;

import java.util.List;

/**
 * @Author：淘跑
 * @Date: 2018/7/7 12:42
 * @Use：
 */

public class BaseViewModel extends ViewModel implements IBaseViewModel {

    public int mPage = 1;
    public Context mContext;

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


    /**
     * 增加页数
     */
    public void addPage() {
        mPage++;
    }


    /**
     * 检查当前的页数
     *
     * @param isRefresh 是否是刷新
     * @param list      请求到的list数据
     */
    public void CheckUpPage(boolean isRefresh, List list) {
        if (isRefresh) {
            //先判断是否是刷新
            mPage = 1;
        } else {
            //如果不是刷新
            if (list == null || list.size() < 1) {
                //判断list是否是空的 如果不是空的判断长度是否大于0
                if (mPage > 1) {
                    mPage -= 1;
                } else {
                    mPage = 1;
                }
            } else {
                //如果list非空,并且长度大于0
                mPage++;
            }
        }
    }

}
