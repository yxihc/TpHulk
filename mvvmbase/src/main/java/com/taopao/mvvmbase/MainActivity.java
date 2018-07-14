package com.taopao.mvvmbase;

import android.databinding.ObservableList;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.taopao.mvvmbase.base.BaseActivity;
import com.taopao.mvvmbase.databinding.ActivityMainBinding;


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
    }

    @Override
    public void initData() {
        mAdapter = new MyAdapter(mViewModel.mUsers);
        mBinding.rv.setAdapter(mAdapter);

    }

    public static class MyAdapter extends BaseQuickAdapter<User, BaseViewHolder> {
        public ObservableList<User> data;

        public MyAdapter(@Nullable final ObservableList<User> data) {
            super(R.layout.activity_ta, data);
            this.data = data;
            data.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<User>>() {
                @Override
                public void onChanged(ObservableList<User> users) {
                    notifyDataSetChanged();
                }

                @Override
                public void onItemRangeChanged(ObservableList<User> users, int i, int i1) {
                    notifyItemRangeChanged(i, i1);
                }

                @Override
                public void onItemRangeInserted(ObservableList<User> users, int positionStart, int itemCount) {
                    notifyItemRangeInserted(positionStart, itemCount);
                }

                @Override
                public void onItemRangeMoved(ObservableList<User> users, int fromPosition, int toPosition, int itemCount) {
                    for (int i = 0; i < itemCount; i++) {
                        notifyItemMoved(fromPosition + i, toPosition + i);
                    }

                }

                @Override
                public void onItemRangeRemoved(ObservableList<User> users, int positionStart, int itemCount) {
                    notifyItemRangeRemoved(positionStart, itemCount);
                }
            });
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
