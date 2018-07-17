package com.taopao.baseapp.ui.activity;

import com.taopao.baseapp.R;
import com.taopao.baseapp.base.BaseActivity;
import com.taopao.baseapp.databinding.ActivityMainBinding;
import com.taopao.baseapp.ui.viewmodel.MainViewModel;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {
    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected MainViewModel initViewModel() {
        return new MainViewModel(this);
    }
}
