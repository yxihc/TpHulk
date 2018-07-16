package com.taopao.mvvmbase;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;

import com.taopao.mvvmbase.base.BaseActivity;
import com.taopao.mvvmbase.base.BaseBindingRvAdapter;
import com.taopao.mvvmbase.base.BaseViewModel;
import com.taopao.mvvmbase.binding.command.BindingAction;
import com.taopao.mvvmbase.binding.command.BindingCommand;
import com.taopao.mvvmbase.databinding.ItemBinding;

import java.util.List;

/**
 * @Author：淘跑
 * @Date: 2018/7/14 17:50
 * @Use：
 */

public class TextBViewModel extends BaseViewModel {

    public ObservableList<User> mUsers = new ObservableArrayList<>();


    public ViewStyle mViewStyle = new ViewStyle();

    public TextBViewModel(BaseActivity activity) {
        super(activity);
    }

    public class ViewStyle {
        public final ObservableBoolean isRefreshing = new ObservableBoolean(false);
        public final ObservableBoolean progressRefreshing = new ObservableBoolean(true);
    }


    public class MyAdapter extends BaseBindingRvAdapter<User> {
        @Override
        protected void convert(BindingViewHolder helper, ViewDataBinding binding, User item) {
            ItemBinding itemBinding = (ItemBinding) binding;
        }

        public MyAdapter(@Nullable List<User> data) {
            super(R.layout.item, data);
        }

    }

    public MyAdapter mMyAdapter = new MyAdapter(mUsers);

    public BindingCommand<Integer> f = new BindingCommand<Integer>(new BindingAction() {
        @Override
        public void call() {

            mUsers.add(1, new User("去问问企鹅委屈去问王企鹅", "8888"));
            new android.os.Handler() {
                @Override
                public void handleMessage(Message msg) {
                    mViewStyle.isRefreshing.set(!mViewStyle.isRefreshing.get());
                }
            }.sendEmptyMessageDelayed(1, 2000);
        }
    });

    @Override
    public void onCreate() {
        super.onCreate();
        mUsers.add(new User("11111"));
        mUsers.add(new User("22222"));
        mUsers.add(new User("33333"));
        mUsers.add(new User("44444"));


    }
}
