package com.taopao.tphulk.mvvmdemo;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.view.View;
import com.taopao.hulkmvvm.BaseActivity;
import com.taopao.tphulk.BR;
import com.taopao.tphulk.R;
import com.taopao.tphulk.databinding.ActivityDemoMvvmBinding;

public class DemoMvvmActivity extends BaseActivity<ActivityDemoMvvmBinding, ViewModel> {
    private TestViewModel mTestViewModel;
    @Override
    public int getLayoutRes() {
        return R.layout.activity_demo_mvvm;
    }
    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.tv.setText("dsadsadsa");
        mBinding.setName("你打sad撒是as");
        mTestViewModel = new ViewModelProvider(this).get(TestViewModel.class);
        getLifecycle().addObserver(mTestViewModel);

        mBinding.setVariable(BR.viewModel,mTestViewModel);

        mBinding.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               mTestViewModel.age.set("变化了");
            }
        });
    }
}
