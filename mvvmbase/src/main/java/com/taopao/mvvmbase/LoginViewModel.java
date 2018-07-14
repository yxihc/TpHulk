package com.taopao.mvvmbase;

import android.content.Context;
import android.content.Intent;
import android.databinding.Observable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableShort;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Adapter;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.taopao.mvvmbase.base.BaseApplication;
import com.taopao.mvvmbase.base.BaseViewModel;
import com.taopao.mvvmbase.binding.command.BindingAction;
import com.taopao.mvvmbase.binding.command.BindingCommand;
import com.taopao.mvvmbase.bus.RxBus;

import java.util.List;
import java.util.logging.Handler;

/**
 * @Author： 淘跑
 * @Date: 2018/7/9 15:22
 * @Use：
 */

public class LoginViewModel extends BaseViewModel {
    public LoginViewModel(Context context) {
        super(context);
    }

    public ViewStyle mViewStyle = new ViewStyle();

    public class ViewStyle {
        public final ObservableBoolean isRefreshing = new ObservableBoolean(false);
        public final ObservableBoolean progressRefreshing = new ObservableBoolean(true);
    }

    //data
    public final ObservableField<String> imageUrl = new ObservableField<>();
    public final ObservableField<String> title = new ObservableField<>("厉害啊");


    public final ObservableArrayList<User> mUsers = new ObservableArrayList<>();

    @Override
    public void onCreate() {
    }

    public BindingCommand netWorkClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
//            Toast.makeText(mContext, "666", Toast.LENGTH_SHORT).show();
//            Log.d("LoginViewModel", "call: " + "111111111");
//            throw new NullPointerException("不可以为null");
            title.set("666");
            imageUrl.set("https://upload.jianshu.io/users/upload_avatars/10151120/48708602-542d-40cd-91f4-897d127dcc4a.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/114/h/114");


            mUsers.add(new User("我是战士"));
            mUsers.add(new User("我是战士"));

            mPage = 3;
            CheckUpPage(false, mUsers);

            Toast.makeText(BaseApplication.getInstance().getApplicationContext(), mPage + "", Toast.LENGTH_SHORT).show();
        }
    });


    public BindingCommand netWorkClick1 = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            mUsers.add(new User("吾问无为谓"));
            mUsers.add(new User("我是as撒所多撒多飒飒战士"));
        }
    });


    public BindingCommand<Integer> f = new BindingCommand<Integer>(new BindingAction() {
        @Override
        public void call() {
//            mUsers.add(new User("11111"));
//            mUsers.add(new User("22222"));
//            mUsers.add(new User("33333"));
//            mUsers.add(new User("44444"));


            mUsers.add(1, new User("44444"));
            new android.os.Handler() {
                @Override
                public void handleMessage(Message msg) {
                    mViewStyle.isRefreshing.set(!mViewStyle.isRefreshing.get());
                }
            }.sendEmptyMessageDelayed(1, 2000);


            mContext.startActivity(new Intent(mContext, TextBActivity.class));

        }
    });


    public BindingCommand<Integer> loadMore = new BindingCommand<Integer>(new BindingAction() {
        @Override
        public void call() {
            Toast.makeText(BaseApplication.getInstance().getApplicationContext(), "萨达", Toast.LENGTH_SHORT).show();
            Log.i("TAG", "222222222222222222222222222222222222");
        }
    });


}
