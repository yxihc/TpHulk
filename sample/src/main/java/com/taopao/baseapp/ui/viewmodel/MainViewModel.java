package com.taopao.baseapp.ui.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.widget.Toast;

import com.taopao.baseapp.R;
import com.taopao.baseapp.http.Api;
import com.taopao.baseapp.http.RetrofitProvider;
import com.taopao.baseapp.http.RxSubscriber;
import com.taopao.baseapp.model.BaseResponse;
import com.taopao.baseapp.model.ImgListInfo;
import com.taopao.baseapp.model.WanAndroidResponse;
import com.taopao.baseapp.ui.activity.FragmentTestActivity;
import com.taopao.baseapp.ui.activity.RefreshActivity;
import com.taopao.mvvmbase.base.BaseBindingRvAdapter;
import com.taopao.mvvmbase.base.BaseMVVMActivity;
import com.taopao.mvvmbase.base.BaseMVVMViewModel;
import com.taopao.mvvmbase.base.ViewState;
import com.taopao.mvvmbase.binding.command.BindingAction;
import com.taopao.mvvmbase.binding.command.BindingCommand;
import com.taopao.mvvmbase.utils.RxUtils;

import java.util.List;
import java.util.Random;

/**
 * @Author： 淘跑
 * @Date: 2018/7/17 14:29
 * @Use：
 */
public class MainViewModel extends BaseMVVMViewModel {
    public MainViewModel(BaseMVVMActivity activity) {
        super(activity);
    }

    public ObservableList<WanAndroidResponse.DatasBean> mWanAndroid = new ObservableArrayList<>();

    public BaseBindingRvAdapter<WanAndroidResponse.DatasBean> mWanAndroidAdapter = new BaseBindingRvAdapter<WanAndroidResponse.DatasBean>(R.layout.recycle_item_wanandroid, mWanAndroid, mContext) {
        @Override
        protected void convert(BindingViewHolder helper, ViewDataBinding binding, WanAndroidResponse.DatasBean item) {
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        getWanAndroid();
    }

    public void getWanAndroid() {
        RetrofitProvider.getInstance(Api.API_WAN_ANDROID)
                .create(Api.class)
                .getHomeList(mPage, null)
                .compose(RxUtils.<BaseResponse<WanAndroidResponse>>bindToLifecycle(mContext))
                .compose(RxUtils.<BaseResponse<WanAndroidResponse>>schedulersTransformer())
                .subscribe(new RxSubscriber<BaseResponse<WanAndroidResponse>>(mViewState, mEvent, hideDialog) {
                    @Override
                    public void onResult(BaseResponse<WanAndroidResponse> wanAndroidResponseBaseResponse) {
                        if (mPage == 1) {
                            mWanAndroid.clear();
                        }
                        List<WanAndroidResponse.DatasBean> datas = wanAndroidResponseBaseResponse.getData().getDatas();
//
                        mWanAndroid.addAll(datas);
                        CheckUpPageOrAdapter(datas, mWanAndroidAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mWanAndroidAdapter.loadMoreFail();
                    }
                });
    }


    public BindingCommand onLoadMore = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            getWanAndroid();
        }
    });

    public BindingCommand onRefresh = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            mPage = 1;
            getWanAndroid();
        }
    });

    public BindingCommand fragment1 = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(FragmentTestActivity.class);
        }
    });

    public BindingCommand sub = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //模拟加载失败
            RetrofitProvider.getInstance(Api.API_WAN_ANDROID)
                    .create(Api.class)
                    .getHomeListError("是的撒啊是的", null)
                    .compose(RxUtils.<BaseResponse<WanAndroidResponse>>bindToLifecycle(mContext))
                    .compose(RxUtils.<BaseResponse<WanAndroidResponse>>schedulersTransformer())
                    .subscribe(new RxSubscriber<BaseResponse<WanAndroidResponse>>(mViewState, mEvent, hideDialog, true) {
                        @Override
                        public void onResult(BaseResponse<WanAndroidResponse> wanAndroidResponseBaseResponse) {
                            if (mPage == 1) {
                                mWanAndroid.clear();
                            }
                            List<WanAndroidResponse.DatasBean> datas = wanAndroidResponseBaseResponse.getData().getDatas();
                            mWanAndroid.addAll(datas);
                        }

                        @Override
                        public void onError(Throwable e) {
                            super.onError(e);
                            mWanAndroidAdapter.loadMoreFail();
                        }
                    });
        }
    });

    public BindingCommand empty = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            mViewState.set(ViewState.Empty_view);
        }
    });

    public BindingCommand refresh = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(RefreshActivity.class);
        }
    });

}




