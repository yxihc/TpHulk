package com.taopao.mvvmbase.base;

import android.app.ActivityOptions;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.taopao.mvvmbase.BR;
import com.taopao.mvvmbase.R;
import com.taopao.mvvmbase.databinding.ActivityBaseBinding;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;


import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @Author： 淘跑
 * @Date: 2018/7/5 15:09
 * @Use： Activity基类
 */
public abstract class BaseMVVMActivity<V extends ViewDataBinding, VM extends BaseMVVMViewModel> extends RxAppCompatActivity implements IBaseActivity, BaseView {
    public V mBinding;
    public VM mViewModel;
    public ActivityBaseBinding mBaseBinding;
    private CompositeDisposable mCompositeDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>页面间传值>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        if (savedInstanceState != null) {
            initParam(savedInstanceState);
        } else if (getIntent() != null && getIntent().getExtras() != null) {
            initParam(getIntent().getExtras());
        }
        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<页面间传值<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        //注入绑定
        initViewDataBinding();
        //防止空指针异常
        if (mViewModel != null) {
            if (savedInstanceState != null) {
                mViewModel.initParam(savedInstanceState);
            } else if (getIntent() != null && getIntent().getExtras() != null) {
                mViewModel.initParam(getIntent().getExtras());
            }
            mViewModel.onCreate();
            mViewModel.registerRxBus();

        }
        initView();
        initData();
        if (mViewModel != null) {
            initViewObservable();
        }

    }

    /**
     * 注入绑定
     */
    private void initViewDataBinding() {

        //初始化跟布局的显示
        mBaseBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_base, null, false);

        //DataBindingUtil类需要在project的build中配置 dataBinding {enabled true }, 同步后会自动关联android.databinding包

        mBinding = DataBindingUtil.inflate(getLayoutInflater(), getContentResId(), null, false);

        // content
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mBinding.getRoot().setLayoutParams(params);
        mBaseBinding.container.addView(mBinding.getRoot());

        mViewModel = initMVVMViewModel();
        if (mViewModel != null) {
            mBinding.setVariable(initVariableId(), mViewModel);
        }

        if (getTopResId(getLayoutInflater(), null) != -1) {
            mBaseBinding.toolBar.setVisibility(View.GONE);
            //初始化布局
            ViewDataBinding mTopBinding = DataBindingUtil.inflate(getLayoutInflater(), getTopResId(getLayoutInflater(), null), null, false);
            mBaseBinding.flContentTop.addView(mTopBinding.getRoot());
        } else {
            if (getTopView(getLayoutInflater(), null) != null) {
                mBaseBinding.toolBar.setVisibility(View.GONE);
                mBaseBinding.flContentTop.addView(getTopView(getLayoutInflater(), null));
            }
        }

        setContentView(mBaseBinding.getRoot());
    }

    /**
     * (借鉴Adapter的getView()方法)初始化头布局view
     * (适用于使用自定义的子布局,需要使用自定义布局里的控制)
     * 设置你的ViewDataBinding
     * 设置你的ViewModel
     * <p>
     * DataBindingUtil.inflate(inflater, 你的layoutid, container, false);
     * <p>
     *
     * @return 布局的ViewDataBinding.getRootView();
     */
    public View getTopView(LayoutInflater inflater, @Nullable ViewGroup container) {
        //设置你的ViewDataBinding
        //设置你的ViewModel
        return null;
    }

    /**
     * 初始化头布局的id
     * (适用于只需要自定义顶部界面 ,但并不需要使用界面的的空间呢)
     *
     * @return 布局的id
     */
    public int getTopResId(LayoutInflater inflater, @Nullable ViewGroup container) {
        return -1;
    }


    /**
     * 初始化布局的id
     *
     * @return 布局的id
     */
    protected abstract int getContentResId();

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
//    protected abstract int initVariableId();

    /**
     * 初始化ViewModel
     *
     * @return 继承BaseViewModel的ViewModel
     */
    protected abstract VM initMVVMViewModel();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //防止空指针异常
        if (mViewModel != null) {
            mViewModel.removeRxBus();
            mViewModel.onDestroy();
            mViewModel = null;
        }
        if (mBinding != null) {
            mBinding.unbind();
        }
        unsubscribe();

    }


    /**
     * 添加activity里的订阅者 对订阅者统一管理
     *
     * @param disposable
     */
    public void addRxDisposable(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    /**
     * 解绑
     */
    public void unsubscribe() {
        if (this.mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
            this.mCompositeDisposable.clear();
            mCompositeDisposable = null;
        }
    }


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>IBaseActivity接口方法重写:需要时重写>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @Override
    public void refreshLayout() {
        if (mViewModel != null && mBinding != null) {
            mBinding.setVariable(initVariableId(), mViewModel);
        }
    }

    /**
     * 布局文件里的ViewModel默命名viewModel
     * 需要自定义可重写次方法
     *
     * @return
     */
    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void setTitle(CharSequence text) {
        mBaseBinding.toolBar.setTitle(text);
    }

    @Override
    public void setToolBar() {
        setSupportActionBar(mBaseBinding.toolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //去除默认Title显示
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mBaseBinding.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    @Override
    public void initParam(Bundle bundle) {
        if (mViewModel != null) {
            mViewModel.initParam(bundle);
        }
    }

    @Override
    public void initView() {

        //第一次加载界面显示加载中动画
//        showLoadingView();

        //默认显示正常的界面
        showNormalView();

        //设置沉浸式状态栏
        setStatusBar();
        //设置toolbar
        setToolBar();
        mBaseBinding.errorRefreshView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoadingView();
                refreshView();
            }
        });

        mBaseBinding.emptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoadingView();
                refreshView();
            }
        });

    }

    @Override
    public void initData() {

    }

    @Override
    public void setStatusBar() {

    }

    @Override
    public void initViewObservable() {
        //界面的几种布局显示
        mViewModel.mViewState.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                switch (mViewModel.mViewState.get()) {
                    case ViewState.Error_view:
                        showErrorView();
                        break;
                    case ViewState.Normal_view:
                        showNormalView();
                        break;
                    case ViewState.Loading_view:
                        showLoadingView();
                        break;
                    case ViewState.Empty_view:
                        showEmptyView();
                        break;
                }
            }
        });
        //服务器返回普通错误
        mViewModel.mEvent.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                EventData eventData = mViewModel.mEvent.get();
                if (eventData.getState() == 0) {
                    //服务器返回普通错误
                    onErrorMsg(eventData.getErrorCode(), eventData.getErrorMessage());
                } else if (eventData.getState() == 1) {
                    showLoginDialog(eventData.getErrorCode(), eventData.getErrorMessage());
                }
            }
        });
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<IBaseActivity接口方法重写:需要时重写<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>BaseView中的方法:需要时重写>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    @Override
    public void showLoadingView() {
        setViewState(View.VISIBLE, View.GONE, View.GONE, View.GONE);
    }

    @Override
    public void showErrorView() {
        setViewState(View.GONE, View.GONE, View.VISIBLE, View.GONE);
    }

    @Override
    public void showNormalView() {
        setViewState(View.GONE, View.VISIBLE, View.GONE, View.GONE);
    }

    @Override
    public void showEmptyView() {
        setViewState(View.GONE, View.GONE, View.GONE, View.VISIBLE);
    }

    /**
     * 设置 加载中 正常显示 和加载失败的三种布局的显示状态
     *
     * @param loadingView 加载中
     * @param normalView  正常显示
     * @param errorView   加载失败
     * @param emptyView   加载成功:但是数据为空
     */
    private void setViewState(int loadingView, int normalView, int errorView, int emptyView) {
        //正在加载中的控制
        if (mBaseBinding.loadingView != null && mBaseBinding.loadingView.shimmmer.getVisibility() != loadingView) {
            mBaseBinding.loadingView.shimmmer.setVisibility(loadingView);
            if (loadingView == View.VISIBLE) {
                mBaseBinding.loadingView.shimmmer.startShimmer();
            } else {
                mBaseBinding.loadingView.shimmmer.stopShimmer();
            }
        }
        //加载失败的布局控制
        if (mBaseBinding.errorRefreshView != null && mBaseBinding.errorRefreshView.getVisibility() != errorView) {
            mBaseBinding.errorRefreshView.setVisibility(errorView);
        }

        //加载为空的布局控制
        if (mBaseBinding.emptyView != null && mBaseBinding.emptyView.getVisibility() != emptyView) {
            mBaseBinding.emptyView.setVisibility(emptyView);
        }

        //正常显示的布局控制
        if (mBinding.getRoot() != null && mBinding.getRoot().getVisibility() != normalView) {
            mBinding.getRoot().setVisibility(normalView);
        }
    }

    /**
     * 失败后点击刷新  需要时重写
     */
    @Override
    public void refreshView() {

    }

    @Override
    public void showLoginDialog(int errorCode, String msg) {

    }

    @Override
    public void showLoadingDialog() {
    }


    @Override
    public void onErrorMsg(int errorCode, String errorMsg) {

    }

    @Override
    public void hideLoadingDialog() {
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<BaseView中的方法:需要时重写<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    //************************************** Activity跳转(兼容4.4) **************************************//

    /**
     * Activity跳转
     *
     * @param clz 要跳转的Activity的类名
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(this, clz));
    }


    /**
     * Activity携带数据的跳转
     *
     * @param clz    要跳转的Activity的类名
     * @param bundle bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * Activity跳转(带动画)
     *
     * @param clz 要跳转的Activity的类名
     */
    public void startActivityAnimation(Class<?> clz) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(new Intent(this, clz), ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
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
            startActivity(new Intent(this, clz), ActivityOptions.makeSceneTransitionAnimation(this, view, shareView).toBundle());
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
        Intent intent = new Intent(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this, view, shareView).toBundle());
        } else {
            startActivity(intent);
        }
    }

    /**
     * Activity跳转(带动画,带Bundle数据)
     *
     * @param clz 要跳转的Activity的类名
     */
    public void startActivityAnimation(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        } else {
            startActivity(intent);
        }
    }


    /**
     * 通过Class打开编辑界面
     *
     * @param cls
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(new Intent(this, cls), requestCode);
    }

    /**
     * 含有Bundle通过Class打开编辑界面
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();

        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 有动画的Finish掉界面
     */
    public void AnimationFinish() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else {
            finish();
        }
    }

    //************************************** Activity跳转 **************************************//

}
