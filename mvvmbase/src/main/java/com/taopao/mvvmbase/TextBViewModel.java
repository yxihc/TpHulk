package com.taopao.mvvmbase;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.taopao.mvvmbase.base.BaseBindingRvAdapter;
import com.taopao.mvvmbase.base.BaseViewModel;

/**
 * @Author：淘跑
 * @Date: 2018/7/14 17:50
 * @Use：
 */

public class TextBViewModel extends BaseViewModel {
    public TextBViewModel(Context context) {
        super(context);
    }

    public ObservableList<User> mUsers = new ObservableArrayList<>();


    public BaseBindingRvAdapter mAdapter = new BaseBindingRvAdapter<User, ItemViewModel>(R.layout.activity_tb, mUsers) {
        @Override
        protected int initVariableId() {
            return BR.viewModel;
        }
        @Override
        protected ItemViewModel initViewModel() {
            return new ItemViewModel(mContext);
        }
    };





    @Override
    public void onCreate() {
        super.onCreate();
        mUsers.add(new User("11111"));
        mUsers.add(new User("22222"));
        mUsers.add(new User("33333"));
        mUsers.add(new User("44444"));


    }
}
