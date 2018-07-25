package com.taopao.baseapp.ui.activity;

import android.support.v4.app.FragmentTransaction;

import com.taopao.baseapp.R;
import com.taopao.baseapp.databinding.ActivityFragmentTestBinding;
import com.taopao.baseapp.ui.fragment.TestFragment;
import com.taopao.mvvmbase.base.BaseMVVMActivity;
import com.taopao.mvvmbase.base.BaseMVVMViewModel;

public class FragmentTestActivity extends BaseMVVMActivity<ActivityFragmentTestBinding, BaseMVVMViewModel> {
    private FragmentTransaction mTransaction;

    @Override
    protected int getContentView() {
        return R.layout.activity_fragment_test;
    }

    @Override
    protected BaseMVVMViewModel initMVVMViewModel() {
        return null;
    }

    @Override
    public void initView() {
        super.initView();
        showNormalView();

        TestFragment testFragment = new TestFragment();

        mTransaction = getSupportFragmentManager().beginTransaction();
        mTransaction.add(R.id.fl_content, testFragment);
        mTransaction.commit();
        mTransaction.hide(testFragment);

        mTransaction.show(testFragment);
    }


}
