package com.taopao.baseapp.ui.activity;

import com.taopao.baseapp.R;
import com.taopao.baseapp.databinding.ActivityRefreshBinding;
import com.taopao.baseapp.ui.viewmodel.RefreshViewModel;
import com.taopao.mvvmbase.base.BaseMVVMActivity;


public class RefreshActivity extends BaseMVVMActivity<ActivityRefreshBinding, RefreshViewModel> {
    @Override
    protected int getContentResId() {
        return R.layout.activity_refresh;
    }

    @Override
    protected RefreshViewModel initMVVMViewModel() {
        return new RefreshViewModel(this);
    }

    @Override
    public void initView() {
        super.initView();
        setTitle("RecycleView的各种设置");
    }

    @Override
    public void refreshView() {
        super.refreshView();
        mViewModel.onRefresh.execute();
    }
}
