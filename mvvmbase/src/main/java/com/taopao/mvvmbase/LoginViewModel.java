package com.taopao.mvvmbase;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.taopao.mvvmbase.base.BaseViewModel;
import com.taopao.mvvmbase.binding.command.BindingAction;
import com.taopao.mvvmbase.binding.command.BindingCommand;

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
        }
    });

}
