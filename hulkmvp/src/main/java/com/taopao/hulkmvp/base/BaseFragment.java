package com.taopao.hulkmvp.base;

import android.content.Context;
import android.os.Bundle;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleObserver;
import androidx.viewbinding.ViewBinding;

import com.taopao.hulkmvp.mvp.IPresenter;

public abstract class BaseFragment<P extends IPresenter> extends Fragment implements IFragment<P> {
    protected final String TAG = this.getClass().getSimpleName();
    protected Context mContext;
    @Nullable
    protected P mPresenter;//如果当前页面逻辑简单, Presenter 可以为 null
    public View mRootView;

    @Override
    public void setPresenter(@Nullable P presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            initParam(savedInstanceState);
        } else if (getArguments() != null) {
            initParam(getArguments());
        }
        if (mPresenter == null) {
            mPresenter = obtainPresenter();
        }

        //将 LifecycleObserver 注册给 LifecycleOwner 后 @OnLifecycleEvent 才可以正常使用
        if (mPresenter != null && mPresenter instanceof LifecycleObserver) {
            getLifecycle().addObserver((LifecycleObserver) mPresenter);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
//          两个都有的话优先使用viewbinding
            ViewBinding viewBinding = obtainViewBinding(inflater, container);
            if (viewBinding != null) {
                mRootView = viewBinding.getRoot();
            } else {
                int layoutResID = getLayoutRes();
                //如果getLayoutRes返回0,框架则不会调用setContentView()
                if (layoutResID != 0) {
                    mRootView = inflater.inflate(getLayoutRes(), container, false);
                }
            }
        } catch (Exception e) {
            if (e instanceof InflateException) throw e;
            e.printStackTrace();
        }
        return mRootView == null ? super.onCreateView(inflater, container, savedInstanceState) : mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (mPresenter == null) {
            mPresenter = obtainPresenter();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
            mPresenter = null;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }
}
