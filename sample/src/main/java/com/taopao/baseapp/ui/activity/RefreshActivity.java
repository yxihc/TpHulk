package com.taopao.baseapp.ui.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.taopao.baseapp.R;
import com.taopao.baseapp.databinding.ActivityRefreshBinding;
import com.taopao.baseapp.loadmore.CustomLoadMoreView;
import com.taopao.baseapp.ui.viewmodel.RefreshViewModel;
import com.taopao.mvvmbase.base.BaseMVVMActivity;

public class RefreshActivity extends BaseMVVMActivity<ActivityRefreshBinding, RefreshViewModel> {

    @Override
    protected int getContentView() {
        return R.layout.activity_refresh;
    }

    @Override
    protected RefreshViewModel initMVVMViewModel() {
        return new RefreshViewModel(this);
    }

    @Override
    public void initView() {
        super.initView();
        mBinding.rv.setLayoutManager(new GridLayoutManager(this, 2));
        mViewModel.mGrilsAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mViewModel.getGrils1();
            }
        }, mBinding.rv);
    }
}
