package com.taopao.baseapp.ui.activity;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.taopao.baseapp.R;
import com.taopao.baseapp.databinding.ActivityMainBinding;
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

    @Override
    public void initView() {
        super.initView();
//        mBinding.srl.finishLoadMoreWithNoMoreData();

        mViewModel.mGrilsAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
    }


    @Override
    public void RefreshView() {
        super.RefreshView();
        mViewModel.getGrils();
    }
}
