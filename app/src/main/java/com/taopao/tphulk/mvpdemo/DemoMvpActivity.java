package com.taopao.tphulk.mvpdemo;

import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import android.view.LayoutInflater;
import com.taopao.hulkmvp.base.BaseActivity;
import com.taopao.tphulk.databinding.ActivityDemoMvpBinding;

public class DemoMvpActivity extends BaseActivity {

    private ActivityDemoMvpBinding mViewBinding;
    @Override
    public ViewBinding obtainViewBinding(@Nullable LayoutInflater layoutInflater) {
        mViewBinding = ActivityDemoMvpBinding.inflate(layoutInflater);
        return mViewBinding;
    }

}
