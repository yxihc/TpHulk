package com.taopao.baseapp;

import com.taopao.baseapp.databinding.ActivityMainBinding;
import com.taopao.mvvmbase.base.BaseActivity;

public class MainActivity extends BaseActivity<ActivityMainBinding, LoginViewModel> {
    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected LoginViewModel initViewModel() {
        return new LoginViewModel(this);
    }
}
