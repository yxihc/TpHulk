package com.taopao.mvvmbase;

import com.taopao.mvvmbase.base.BaseActivity;
import com.taopao.mvvmbase.databinding.ActivityMainBinding;


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
