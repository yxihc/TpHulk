package com.taopao.mvvmbase.base;

import android.app.Activity;
import android.app.ActivityOptions;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

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
