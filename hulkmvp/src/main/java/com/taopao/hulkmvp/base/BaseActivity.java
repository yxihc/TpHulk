package com.taopao.hulkmvp.base;

import android.os.Bundle;
import android.view.InflateException;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.viewbinding.ViewBinding;

import com.taopao.hulkmvp.mvp.IPresenter;

public  abstract class BaseActivity<P extends IPresenter> extends AppCompatActivity implements IActivity<P>  {
    protected P mPresenter = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            initParam(savedInstanceState);
        } else if (getIntent() != null && getIntent().getExtras() != null) {
            initParam(getIntent().getExtras());
        }

        try {
//          两个都有的话优先使用viewbinding
            ViewBinding viewBinding = obtainViewBinding(getLayoutInflater());
            if (viewBinding!=null){
                setContentView(viewBinding.getRoot());
            }else{
                int layoutResID = getLayoutRes();
                //如果getLayoutRes返回0,框架则不会调用setContentView()
                if (layoutResID != 0) {
                    setContentView(layoutResID);
                }
            }
        } catch (Exception e) {
            if (e instanceof InflateException) throw e;
            e.printStackTrace();
        }

        //  初始presenter
        setPresenter(obtainPresenter());

        //将 LifecycleObserver 注册给 LifecycleOwner 后 @OnLifecycleEvent 才可以正常使用
        if (this instanceof LifecycleOwner
                && mPresenter != null && mPresenter instanceof LifecycleObserver) {
            getLifecycle().addObserver((LifecycleObserver) mPresenter);
        }

        initView(savedInstanceState);
        initData(savedInstanceState);
        initListener(savedInstanceState);
    }

    @Override
    public void setPresenter(P presenter) {
        this.mPresenter = presenter;
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (mPresenter == null) mPresenter = obtainPresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();//释放资源
        }
        this.mPresenter = null;
    }
}
