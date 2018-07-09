package com.taopao.mvvmbase;

import com.taopao.mvvmbase.base.BaseActivity;
import com.taopao.mvvmbase.base.BaseViewModel;
import com.taopao.mvvmbase.databinding.ActivityTaBinding;

public class TAActivity extends BaseActivity<ActivityTaBinding, BaseViewModel> {


    @Override
    protected int getContentView() {
        return R.layout.activity_ta;
    }

    @Override
    protected int initVariableId() {
        return BR.viewModel;
    }

    @Override
    protected BaseViewModel initViewModel() {
        return null;
    }
}
