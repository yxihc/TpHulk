package com.taopao.baseapp.base;

import android.databinding.ViewDataBinding;

import com.taopao.mvvmbase.base.BaseMVVMActivity;
import com.taopao.mvvmbase.base.BaseMVVMViewModel;

/**
 * @Author： 淘跑
 * @Date: 2018/7/17 11:02
 * @Use：
 */

public abstract class BaseActivity<V extends ViewDataBinding, VM extends BaseViewModel> extends BaseMVVMActivity {
    @Override
    protected VM initMVVMViewModel() {
        return (VM) (mViewModel = initViewModel());
    }

    protected abstract VM initViewModel();
}
