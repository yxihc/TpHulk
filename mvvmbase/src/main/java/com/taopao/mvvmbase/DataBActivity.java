package com.taopao.mvvmbase;


import com.taopao.mvvmbase.base.BaseActivity;
import com.taopao.mvvmbase.databinding.ActivityDataBBinding;

public class DataBActivity extends BaseActivity<ActivityDataBBinding> {

    @Override
    protected int getContentView() {
        return R.layout.activity_data_b;
    }

    @Override
    protected int initVariableId() {
        return 0;
    }
}
