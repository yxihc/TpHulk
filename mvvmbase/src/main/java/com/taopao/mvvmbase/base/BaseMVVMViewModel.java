package com.taopao.mvvmbase.base;

import android.app.Activity;
import android.app.ActivityOptions;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;
import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.PropertyChangeRegistry;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import java.util.List;

/**
 * @Author：淘跑
 * @Date: 2018/7/7 12:42
 * @Use：
 */

public class BaseMVVMViewModel extends ViewModel implements Observable, IBaseViewModel {

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>常用的界面显示>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    public ObservableInt mViewState = new ObservableInt(ViewState.Normal_view);//界面的显示状态
    public ObservableBoolean hideDialog = new ObservableBoolean(false);//关闭加载中动画
    public final ObservableBoolean finishRefresh = new ObservableBoolean(false);//关闭刷新动画
    public ObservableField<EventData> mEvent = new ObservableField<>(new EventData());//服务器常见的错误码
    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<常用的界面显示<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>通知刷新>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    private PropertyChangeRegistry callbacks = new PropertyChangeRegistry();

    public void notifyChange() {
        callbacks.notifyCallbacks(this, 0, null);
    }

    public void notifyPropertyChanged(int fieldId) {
        callbacks.notifyCallbacks(this, fieldId, null);
    }

    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback onPropertyChangedCallback) {
        callbacks.add(onPropertyChangedCallback);
    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback onPropertyChangedCallback) {
        callbacks.remove(onPropertyChangedCallback);
    }
    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<通知刷新<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    public int mPage = 1;
    public Context mContext;
    public BaseMVVMActivity mActivity;
    public BaseMVVMFragment mFragment;

    public BaseMVVMViewModel(BaseMVVMActivity activity) {
        mActivity = activity;
        mContext = activity;
    }

    public BaseMVVMViewModel(BaseMVVMFragment fragment) {
        mFragment = fragment;
        mContext = fragment.getContext();
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

    @Override
    public void initParam(Bundle bundle) {

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


    //************************************** Activity跳转(兼容4.4) **************************************//

    /**
     * Activity跳转
     *
     * @param clz 要跳转的Activity的类名
     */
    public void startActivity(Class<?> clz) {
        mContext.startActivity(new Intent(mContext, clz));
    }


    /**
     * Activity携带数据的跳转
     *
     * @param clz    要跳转的Activity的类名
     * @param bundle bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(mContext, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        mContext.startActivity(intent);
    }

    /**
     * Activity跳转(带动画)
     *
     * @param clz 要跳转的Activity的类名
     */
    public void startActivityAnimation(Class<?> clz) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mContext.startActivity(new Intent(mContext, clz), ActivityOptions.makeSceneTransitionAnimation((Activity) mContext).toBundle());
        } else {
            startActivity(clz);
        }
    }

    /**
     * Activity跳转(共享元素动画)
     *
     * @param clz 要跳转的Activity的类名
     */
    public void startActivityAnimation(Class<?> clz, View view, String shareView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mContext.startActivity(new Intent(mContext, clz), ActivityOptions.makeSceneTransitionAnimation((Activity) mContext, view, shareView).toBundle());
        } else {
            startActivity(clz);
        }
    }

    /**
     * Activity跳转(共享元素动画,带Bundle数据)
     *
     * @param clz 要跳转的Activity的类名
     */
    public void startActivityAnimation(Class<?> clz, View view, String shareView, Bundle bundle) {
        Intent intent = new Intent(mContext, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mContext.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) mContext, view, shareView).toBundle());
        } else {
            mContext.startActivity(intent);
        }
    }

    /**
     * Activity跳转(带动画,带Bundle数据)
     *
     * @param clz 要跳转的Activity的类名
     */
    public void startActivityAnimation(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(mContext, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mContext.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) mContext).toBundle());
        } else {
            mContext.startActivity(intent);
        }
    }

    /**
     * 有动画的Finish掉界面
     */
    public void AnimationFinish() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ((Activity) mContext).finishAfterTransition();
        } else {
            ((Activity) mContext).finish();
        }
    }


    //************************************** Activity跳转 **************************************//


}
