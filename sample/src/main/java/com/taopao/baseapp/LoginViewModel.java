package com.taopao.baseapp;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.taopao.mvvmbase.base.BaseActivity;
import com.taopao.mvvmbase.base.BaseViewModel;
import com.taopao.mvvmbase.base.EventData;
import com.taopao.mvvmbase.binding.command.BindingAction;
import com.taopao.mvvmbase.binding.command.BindingCommand;
import com.taopao.mvvmbase.http.BaseResponse;
import com.taopao.mvvmbase.http.BaseSubscriber;
import com.taopao.mvvmbase.http.RetrofitProvider;
import com.taopao.mvvmbase.http.RxTransformer;

import java.util.HashMap;

/**
 * @Author： 淘跑
 * @Date: 2018/7/9 15:22
 * @Use：
 */

public class LoginViewModel extends BaseViewModel {

    public ViewStyle mViewStyle = new ViewStyle();

    public LoginViewModel(BaseActivity activity) {
        super(activity);
    }

    public class ViewStyle {
        public final ObservableBoolean isRefreshing = new ObservableBoolean(false);
        public final ObservableBoolean progressRefreshing = new ObservableBoolean(true);
    }

    //data
    public final ObservableField<String> imageUrl = new ObservableField<>();
    public final ObservableField<String> title = new ObservableField<>("厉害啊");

    public final ObservableArrayList<User> mUsers = new ObservableArrayList<>();

    public final ObservableField<EventData> mEvent = new ObservableField<>(new EventData());

    @Override
    public void onCreate() {

    }

    public BindingCommand netWorkClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            title.set("666");
            imageUrl.set("https://upload.jianshu.io/users/upload_avatars/10151120/48708602-542d-40cd-91f4-897d127dcc4a.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/114/h/114");

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("22222", "wocao");
            hashMap.put("deviceType", "周年我国");
            hashMap.put("22222s", 1111);
            RetrofitProvider.getInstance(Api.BASE_URL)
                    .create(Api.class)
                    .getAppVersion(1)
                    .compose(mActivity.<BaseResponse<AppVersionResponse>>bindToLifecycle())
                    .compose(RxTransformer.<BaseResponse<AppVersionResponse>>switchSchedulers())
                    .subscribe(new BaseSubscriber<BaseResponse<AppVersionResponse>>(mContext, mViewState, mEvent, showLoadingDialog) {
                        @Override
                        public void onResult(BaseResponse<AppVersionResponse> appVersionResponseBaseResponse) {
                            Toast.makeText(mContext, appVersionResponseBaseResponse.getData().getUrl(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    });


    public BindingCommand netWorkClick1 = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            mUsers.add(new User("吾问无为谓"));
            mUsers.add(new User("我是as撒所多撒多飒飒战士"));
        }
    });


    public BindingCommand nonetErrorView = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            mEvent.set(new EventData(11, "萨达", 0));
        }
    });

    public BindingCommand normalView = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Log.i("TAG", "111111111111111111111111111111");

        }
    });

    public BindingCommand errorView = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
        }
    });
    public BindingCommand loginView = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
//            ApiRetrofit.getInstance()
//                    .getAppVersion(1)
//                    .compose(mActivity.<BaseResponse<AppVersionResponse>>bindToLifecycle())
//                    .compose(RxTransformer.<BaseResponse<AppVersionResponse>>switchSchedulers())
//                    .subscribe(new BaseSubscriber<BaseResponse<AppVersionResponse>>(mContext, mViewState, mEvent, showLoadingDialog) {
//                        @Override
//                        public void onResult(BaseResponse<AppVersionResponse> appVersionResponseBaseResponse) {
//                            Toast.makeText(mContext, appVersionResponseBaseResponse.getData().getUrl(), Toast.LENGTH_SHORT).show();
//                        }
//                    });

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("22222", "wocao");
            hashMap.put("deviceType", "周年我国");
            hashMap.put("22222s", 1111);
            RetrofitProvider.getInstance(Api.BASE_URL)
                    .create(Api.class)
                    .getAppVersion1(hashMap)
                    .compose(mActivity.<BaseResponse<AppVersionResponse>>bindToLifecycle())
                    .compose(RxTransformer.<BaseResponse<AppVersionResponse>>switchSchedulers())
                    .subscribe(new BaseSubscriber<BaseResponse<AppVersionResponse>>(mContext, mViewState, mEvent, showLoadingDialog) {
                        @Override
                        public void onResult(BaseResponse<AppVersionResponse> appVersionResponseBaseResponse) {
                            Toast.makeText(mContext, appVersionResponseBaseResponse.getData().getUrl(), Toast.LENGTH_SHORT).show();
                        }
                    });

        }
    });

    public BindingCommand<Integer> f = new BindingCommand<Integer>(new BindingAction() {
        @Override
        public void call() {
            mUsers.add(new User("11111"));
            mUsers.add(new User("22222"));

            mUsers.add(1, new User("44444"));
            new android.os.Handler() {
                @Override
                public void handleMessage(Message msg) {
                    mViewStyle.isRefreshing.set(!mViewStyle.isRefreshing.get());
                }
            }.sendEmptyMessageDelayed(1, 2000);
        }
    });


}
