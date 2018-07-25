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
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.taopao.mvvmbase.utils.MaterialDialogUtils;

import java.util.List;

/**
 * @Author：淘跑
 * @Date: 2018/7/7 12:42
 * @Use：
 */

public class BaseMVVMViewModel extends ViewModel implements Observable, IBaseViewModel {

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>常用的界面显示>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    public ObservableInt mViewState = new ObservableInt(ViewState.Loading_view);//界面的显示状态(默认是加载中)
    public ObservableBoolean hideDialog = new ObservableBoolean(false);//关闭加载中动画
    public final ObservableBoolean finishRefresh = new ObservableBoolean(false);//关闭刷新动画
    public ObservableField<EventData> mEvent = new ObservableField<>(new EventData());//服务器常见的错误码
    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<常用的界面显示<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>通知刷新>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    private PropertyChangeRegistry callbacks = new PropertyChangeRegistry();
    private MaterialDialog mMaterialDialog;

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
    public int mLimit = 20;
    public Context mContext;
    public BaseMVVMActivity mActivity;
    public BaseMVVMFragment mFragment;

    public BaseMVVMViewModel(BaseMVVMActivity activity) {
        mActivity = activity;
        mContext = activity;
    }

    public BaseMVVMViewModel(BaseMVVMFragment fragment) {
        mFragment = fragment;
        mContext = fragment.getActivity();
    }

    @Override
    public void onCreate() {
        hideDialog.addOnPropertyChangedCallback(new OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                HideDialog();
                finishRefresh.set(!finishRefresh.get());
            }
        });

    }

    /**
     * 显示dialog
     *
     * @param msg
     */
    public void showDialog(String msg) {
        if (mMaterialDialog != null) {
            mMaterialDialog.show();
        } else {
            MaterialDialog.Builder builder = MaterialDialogUtils.showIndeterminateProgressDialog(mContext, msg, true);
            mMaterialDialog = builder.show();
        }
    }

    /**
     * 隐藏dialog
     */
    public void HideDialog() {
        if (mMaterialDialog != null && mMaterialDialog.isShowing()) {
            mMaterialDialog.dismiss();
        }
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
     * 显示加载数据成功但是数据为空的情况 按需使用
     */
    public void showEmptyView() {
        mViewState.set(ViewState.Empty_view);
    }

    /**
     * 检查当前的页数计算出下次应该请求的页数(开启上拉加载和判断当前是否是最后一页)
     * 理论上讲,如果失败的话页数应该是不变的(所以不考虑请求时失败的情况,在请求成功的时候检查一下即可)
     *
     * @param list    请求到的list数据
     * @param adapter 基于 BaseQuickAdapter
     */
    public void CheckUpPageOrAdapter(List list, BaseQuickAdapter adapter) {
        if (adapter != null) {
            //开启上拉加载
            adapter.setEnableLoadMore(true);
            if (list == null || list.size() < mLimit) {
                //加载完成没有更多数据了
                adapter.loadMoreEnd();
            } else {
                //加载完成还有更多数据
                adapter.loadMoreComplete();
            }
        }

        //如果不是刷新
        if (list == null || list.size() < 1) {
            //如果请求到的数据是空的,或者请求到数据长度为0,那么页数就不应该再改变
            //这里提示没有更多数据了
//            Toast.makeText(mContext, "没有更多数据了", Toast.LENGTH_SHORT).show();
        } else {
            //如果list非空,并且长度大于0
            mPage++;
        }
    }


    /**
     * 检查当前的页数计算出下次应该请求的页数
     * 理论上讲,如果失败的话页数应该是不变的(所以不考虑请求时失败的情况,在请求成功的时候检查一下即可)
     *
     * @param list 请求到的list数据
     */
    public void CheckUpPage(List list) {
        //如果不是刷新
        if (list == null || list.size() < 1) {
            //如果请求到的数据是空的,或者请求到数据长度为0,那么页数就不应该再改变
            //这里提示没有更多数据了
//            Toast.makeText(mContext, "没有更多数据了", Toast.LENGTH_SHORT).show();
        } else {
            //如果list非空,并且长度大于0
            mPage++;
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
