package com.taopao.mvvmbase;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.taopao.mvvmbase.base.BaseActivity;
import com.taopao.mvvmbase.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity<ActivityMainBinding, LoginViewModel> {
    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected int initVariableId() {
        return com.taopao.mvvmbase.BR.viewModel;
    }

    @Override
    public void initView() {
        mViewModel.title.set("1411125241");
        mViewModel.mUser.set(new User("sadsadssa"));

//        mBinding.setUser();


        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            strings.add("1111");
        }

        mBinding.rv.setAdapter(new MyAdapter(strings));
    }


    class MyAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public MyAdapter(@Nullable List<String> data) {
            super(R.layout.activity_ta, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {

        }
    }


    @Override
    protected LoginViewModel initViewModel() {
        return new LoginViewModel(this);
    }
}
