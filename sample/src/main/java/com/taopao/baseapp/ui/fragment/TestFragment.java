package com.taopao.baseapp.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;
import com.taopao.baseapp.R;
import com.taopao.baseapp.databinding.ActivityLoginBinding;
import com.taopao.baseapp.databinding.FragmentTestBinding;
import com.taopao.baseapp.ui.activity.MainActivity;
import com.taopao.mvvmbase.base.BaseMVVMFragment;
import com.taopao.mvvmbase.base.BaseMVVMViewModel;

/**
 * @Author：淘跑
 * @Date: 2018/7/22 20:54
 * @Use：
 */
public class TestFragment extends BaseMVVMFragment<FragmentTestBinding, BaseMVVMViewModel> {
    @Override
    protected int getContentResId(LayoutInflater inflater, @Nullable ViewGroup container) {
        return R.layout.fragment_test;
    }

    ActivityLoginBinding mTopBinding;

    @Override
    public View getTopView(LayoutInflater inflater, @Nullable ViewGroup container) {
        mTopBinding = DataBindingUtil.inflate(inflater, R.layout.activity_login, container, false);
        return mTopBinding.getRoot();
    }

    @Override
    protected BaseMVVMViewModel initViewModel() {
        return new BaseMVVMViewModel(this);
    }

    private String mLife = "";

    @Override
    public void initView() {
        super.initView();
        showNormalView();
//        mBinding.tvLife.setText(mLife + "\n");


        mBinding.tvLife.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(MainActivity.class);
            }
        });


//        getTopBinding(ActivityLoginBinding.class);


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d("onCreate");
        Log.i("------------", "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Logger.d("onCreateView");
        Log.i("------------", "onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Logger.d("onStart");
        Log.i("------------", "onStart");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Logger.d("setUserVisibleHint" + getUserVisibleHint());
        Log.i("------------", "setUserVisibleHint" + getUserVisibleHint());
    }


}
