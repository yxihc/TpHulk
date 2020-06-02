package com.taopao.tphulk.mvvmdemo;

import androidx.annotation.Nullable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.taopao.hulkbase.log.HLog;
import com.taopao.hulkmvvm.BaseActivity;
import com.taopao.tphulk.BR;
import com.taopao.tphulk.R;
import com.taopao.tphulk.databinding.ActivityDemoMvvmBinding;

public class DemoMvvmActivity extends BaseActivity<ActivityDemoMvvmBinding, DemoViewModel> {
    @Override
    public int getLayoutRes() {
        return R.layout.activity_demo_mvvm;
    }
    @Override
    public int variableId() {
        return BR.viewModel;
    }
    @Override
    public void initListener(@Nullable Bundle savedInstanceState) {
        mBinding.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.age.set("24");
            }
        });
        mBinding.age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.name.postValue("李四");
            }
        });
    }
}
