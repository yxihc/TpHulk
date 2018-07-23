package com.taopao.mvvmbase.base;

import android.app.ActivityOptions;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.taopao.mvvmbase.BR;
import com.taopao.mvvmbase.R;
import com.taopao.mvvmbase.databinding.FragmentBaseBinding;
import com.trello.rxlifecycle2.components.support.RxFragment;

/**
 * @Author：淘跑
 * @Date: 2018/7/15 16:49
 * @Use：
 */

public abstract class BaseMVVMFragment<V extends ViewDataBinding, VM extends BaseMVVMViewModel> extends RxFragment implements IBaseActivity, BaseView {
    protected V mBinding;
    protected VM mViewModel;
    private FragmentBaseBinding mBaseBinding;
    /**
     * Fragment是否可见状态
     */
    public boolean isFragmentVisible = false;
    /**
     * 标志位，View是否已经初始化完成。
     */
    private boolean isPrepared = false;
    /**
     * 是否第一次加载
     */
    private boolean isFirstLoad = true;


    /**
     * <pre>
     * 忽略isFirstLoad的值，强制刷新数据，但仍要Visible & Prepared
     * 一般用于PagerAdapter需要刷新各个子Fragment的场景
     * 不要new 新的 PagerAdapter 而采取reset数据的方式
     * 所以要求Fragment重新走initData方法
     * 故使用 {@link BaseMVVMFragment#setForceLoad(boolean)}来让Fragment下次执行initData
     * </pre>
     */
    private boolean forceLoad = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //页面间传值
        if (savedInstanceState != null) {
            initParam(savedInstanceState);
        } else if (getArguments() != null) {
            initParam(getArguments());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //初始化容器布局的显示
        mBaseBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_base, container, false);

        //初始化布局
        mBinding = DataBindingUtil.inflate(inflater, getContentView(inflater, container), container, false);

        // content
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mBinding.getRoot().setLayoutParams(params);
        RelativeLayout mContainer = (RelativeLayout) mBaseBinding.getRoot().findViewById(R.id.container);
        mContainer.addView(mBinding.getRoot());

        mBinding.setVariable(initVariableId(), mViewModel = initViewModel());


        // 若 viewpager 不设置 setOffscreenPageLimit 或设置数量不够
        // 销毁的Fragment onCreateView 每次都会执行(但实体类没有从内存销毁)
        isFirstLoad = true;
        return mBaseBinding.getRoot();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();

        mViewModel.onCreate();
        mViewModel.registerRxBus();


        //界面初始化完成
        isPrepared = true;
        loadData();

        initData();
        initViewObservable();
    }

    /**
     * 如果是与ViewPager一起使用，调用的是setUserVisibleHint
     * <p>
     * 这个方法执行的时候onCreateView并不一定执行(切记)
     *
     * @param isVisibleToUser 是否显示出来了
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            onVisible();
        } else {
            onInvisible();
        }
    }

    /**
     * 如果是通过FragmentTransaction的show和hide的方法来控制显示，调用的是onHiddenChanged.
     * 若是初始就show的Fragment 为了触发该事件 需要先hide再show
     *
     * @param hidden hidden True if the fragment is now hidden, false if it is not
     *               visible.
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            onVisible();
        } else {
            onInvisible();
        }
    }

    protected void onInvisible() {
        isFragmentVisible = false;

    }

    /**
     * 当界面可见的时候执行
     */
    protected void onVisible() {
        isFragmentVisible = true;
        loadData();

    }


    /**
     * 这里执行懒加载的逻辑
     * 只会执行一次(如果不想只执行一次此方法): {@link BaseMVVMFragment#setForceLoad(boolean)}
     */
    protected void lazyLoad() {

    }

    private void loadData() {
        //判断View是否已经初始化完成 并且 fragment是可见 并且是第一次加载
        if (isPrepared && isFragmentVisible && isFirstLoad) {
            if (forceLoad || isFirstLoad) {
                forceLoad = false;
                isFirstLoad = false;
                lazyLoad();
            }
        }
    }

    /**
     * @param forceLoad 设置为true  lazyLoad()方法会执行多次 否则只会执行一次
     */
    public void setForceLoad(boolean forceLoad) {
        this.forceLoad = forceLoad;
    }


    @Override
    public void onDestroy() {
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

        isPrepared = false;

    }


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>子类必须实现>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    /**
     * 初始化布局的id
     *
     * @return 布局的id
     */
    protected abstract int getContentView(LayoutInflater inflater, @Nullable ViewGroup container);

    /**
     * 初始化ViewModel
     *
     * @return 继承BaseViewModel的ViewModel
     */
    protected abstract VM initViewModel();

    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<子类必须实现<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>IBaseActivity接口方法重写:需要时重写>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    @Override
    public void initView() {

        //第一次加载界面显示加载中动画
        showLoadingView();

        mBaseBinding.errorRefreshView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoadingView();
                refreshView();
            }
        });

    }

    //刷新布局
    @Override
    public void refreshLayout() {
        if (mViewModel != null) {
            mBinding.setVariable(initVariableId(), mViewModel);
        }
    }

    @Override
    public void initParam(Bundle bundle) {
        if (mViewModel != null) {
            mViewModel.initParam(bundle);
        }
    }

    @Override
    public void setToolBar() {

    }

    /**
     * 布局文件默命名viewModel
     * 需要自定义可重写次方法
     *
     * @return
     */
    @Override
    public int initVariableId() {
        return BR.viewModel;
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
        setViewState(View.VISIBLE, View.GONE, View.GONE);
    }

    @Override
    public void showErrorView() {
        setViewState(View.GONE, View.GONE, View.VISIBLE);
    }

    @Override
    public void showNormalView() {
        setViewState(View.GONE, View.VISIBLE, View.GONE);
    }

    /**
     * 设置 加载中 正常显示 和加载失败的三种布局的显示状态
     *
     * @param loadingView 加载中
     * @param normalView  正常显示
     * @param errorView   加载失败
     */
    private void setViewState(int loadingView, int normalView, int errorView) {
        if (mBaseBinding.loadingView != null && mBaseBinding.loadingView.shimmmer.getVisibility() != loadingView) {
            mBaseBinding.loadingView.shimmmer.setVisibility(loadingView);
        }
        if (mBaseBinding.errorRefreshView != null && mBaseBinding.errorRefreshView.getVisibility() != errorView) {
            mBaseBinding.errorRefreshView.setVisibility(errorView);
        }
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
        startActivity(new Intent(getActivity(), clz));
    }


    /**
     * Activity携带数据的跳转
     *
     * @param clz    要跳转的Activity的类名
     * @param bundle bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clz);
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
            startActivity(new Intent(getActivity(), clz), ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
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
            startActivity(new Intent(getActivity(), clz), ActivityOptions.makeSceneTransitionAnimation(getActivity(), view, shareView).toBundle());
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
        Intent intent = new Intent(getActivity(), clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity(), view, shareView).toBundle());
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
        Intent intent = new Intent(getActivity(), clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
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
        startActivityForResult(new Intent(getActivity(), cls), requestCode);
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

        intent.setClass(getActivity(), cls);
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
            getActivity().finishAfterTransition();
        } else {
            getActivity().finish();
        }
    }

    //************************************** Activity跳转 **************************************//

}
