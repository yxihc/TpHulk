package com.taopao.mvvmbase;

import android.databinding.ObservableList;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.taopao.mvvmbase.base.BaseActivity;
import com.taopao.mvvmbase.bus.RxBus;
import com.taopao.mvvmbase.databinding.ActivityMainBinding;

import java.util.List;

import io.reactivex.functions.Consumer;

public class MainActivity extends BaseActivity<ActivityMainBinding, LoginViewModel> {

    private MyAdapter mAdapter;

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
        mViewModel.mUser.set(new User("sadsadssa"));
//        mBinding.setUser();
    }

    @Override
    public void initData() {
        mAdapter = new MyAdapter(mViewModel.mUsers);
        mBinding.rv.setAdapter(mAdapter);
        RxBus.getDefault().toObservable(String.class).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
//                mAdapter.notifyDataSetChanged();
            }
        });

        mViewModel.mUsers.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<User>>() {
            @Override
            public void onChanged(ObservableList<User> users) {
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(ObservableList<User> users, int i, int i1) {

            }

            @Override
            public void onItemRangeInserted(ObservableList<User> users, int i, int i1) {
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onItemRangeMoved(ObservableList<User> users, int i, int i1, int i2) {

            }

            @Override
            public void onItemRangeRemoved(ObservableList<User> users, int i, int i1) {

            }
        });

    }

    class MyAdapter extends BaseQuickAdapter<User, BaseViewHolder> {
        public MyAdapter(@Nullable List<User> data) {
            super(R.layout.activity_ta, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, User item) {
            helper.setText(R.id.tv, item.getName());
        }
    }

    @Override
    protected LoginViewModel initViewModel() {
        return new LoginViewModel(this);
    }
}
