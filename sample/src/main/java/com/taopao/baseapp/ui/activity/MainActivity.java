package com.taopao.baseapp.ui.activity;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taopao.baseapp.R;
import com.taopao.baseapp.databinding.ActivityLoginBinding;
import com.taopao.baseapp.databinding.ActivityMainBinding;
import com.taopao.baseapp.databinding.LayoutCustomTopViewBinding;
import com.taopao.baseapp.ui.viewmodel.MainViewModel;
import com.taopao.mvvmbase.base.BaseMVVMActivity;

public class MainActivity extends BaseMVVMActivity<ActivityMainBinding, MainViewModel> {
    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected MainViewModel initMVVMViewModel() {
        return new MainViewModel(this);
    }

    LayoutCustomTopViewBinding mTopBinding;

    @Override
    public View getTopView(LayoutInflater inflater, @Nullable ViewGroup container) {
        mTopBinding = DataBindingUtil.inflate(inflater, R.layout.layout_custom_top_view, container, false);
        return mTopBinding.getRoot();
    }

    @Override
    public void initView() {
        super.initView();
        setTitle("网络加载方式");
    }

    @Override
    public void refreshView() {
        super.refreshView();
        mViewModel.getWanAndroid();
    }
}
