package com.taopao.hulkmvvm;

import android.os.Bundle;
import android.view.InflateException;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;

public abstract class BaseActivity<DB extends ViewDataBinding,VM extends ViewModel> extends AppCompatActivity implements IActivity {

    public DB mBinding;
    public VM mViewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            initParam(savedInstanceState);
        } else if (getIntent() != null && getIntent().getExtras() != null) {
            initParam(getIntent().getExtras());
        }

        try {
            int layoutResID = getLayoutRes();
            //如果getLayoutRes返回0,框架则不会调用setContentView()
            if (layoutResID != 0) {
                mBinding = DataBindingUtil.setContentView(this, layoutResID);
            }
        } catch (Exception e) {
            if (e instanceof InflateException) throw e;
            e.printStackTrace();
        }

        initView(savedInstanceState);
        initData(savedInstanceState);
        initListener(savedInstanceState);
    }
}
