package com.taopao.mvvmbase;

import android.content.Context;
import android.databinding.Observable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableShort;
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

/**
 * @Author： 淘跑
 * @Date: 2018/7/9 15:22
 * @Use：
 */

public class LoginViewModel extends BaseViewModel {
    public LoginViewModel(Context context) {
        super(context);
    }

    /**
     * ViewStyle
     * collection of view style
     */
    public class ViewStyle {
        public final ObservableBoolean isRefreshing = new ObservableBoolean(true);
        public final ObservableBoolean progressRefreshing = new ObservableBoolean(true);
    }

    //data
    public final ObservableField<String> imageUrl = new ObservableField<>();
    public final ObservableField<String> title = new ObservableField<>();

    public ViewStyle mViewStyle = new ViewStyle();

    public final ObservableArrayList<User> mUsers = new ObservableArrayList<>();


    public User user = new User("张三");

    public final ObservableField<User> mUser = new ObservableField<>();


    @Override
    public void onCreate() {
        User sssadasdsa = new User("sssadasdsa");
        mUser.set(sssadasdsa);
    }

    public BindingCommand netWorkClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
//            Toast.makeText(mContext, "666", Toast.LENGTH_SHORT).show();
//            Log.d("LoginViewModel", "call: " + "111111111");
//            throw new NullPointerException("不可以为null");
            title.set("666");
            imageUrl.set("https://upload.jianshu.io/users/upload_avatars/10151120/48708602-542d-40cd-91f4-897d127dcc4a.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/114/h/114");


            User sssadasdsa = new User("2311322132132132132");
            mUser.set(sssadasdsa);

            user.setName("666");

            mUsers.add(new User("我是战士"));
            mUsers.add(new User("我是战士"));


            mPage = 3;

            CheckUpPage(mViewStyle.isRefreshing.get(), mUsers);

            Toast.makeText(BaseApplication.getInstance().getApplicationContext(), mPage + "", Toast.LENGTH_SHORT).show();
        }
    });


    public BindingCommand netWorkClick1 = new BindingCommand(new BindingAction() {
        @Override
        public void call() {

            mUsers.add(new User("吾问无为谓"));
            mUsers.add(new User("我是as撒所多撒多飒飒战士"));

            mUsers.set(0, new User("吾问无为谓"));

            RxBus.getDefault().post("1");

        }
    });


    public BindingCommand<Integer> f = new BindingCommand<Integer>(new BindingAction() {
        @Override
        public void call() {

            mUsers.add(new User("11111"));
            mUsers.add(new User("22222"));
            mUsers.add(new User("33333"));
            mUsers.add(new User("44444"));

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
