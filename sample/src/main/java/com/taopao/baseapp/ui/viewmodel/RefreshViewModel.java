package com.taopao.baseapp.ui.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.taopao.baseapp.R;
import com.taopao.baseapp.http.Api;
import com.taopao.baseapp.http.RetrofitProvider;
import com.taopao.baseapp.http.RxSubscriber;
import com.taopao.baseapp.model.BaseResponse;
import com.taopao.baseapp.model.ImgListInfo;
import com.taopao.baseapp.model.WanAndroidResponse;
import com.taopao.baseapp.ui.adapter.RvGrilsAdapter;
import com.taopao.mvvmbase.base.BaseBindingRvAdapter;
import com.taopao.mvvmbase.base.BaseMVVMActivity;
import com.taopao.mvvmbase.base.BaseMVVMViewModel;
import com.taopao.mvvmbase.binding.command.BindingAction;
import com.taopao.mvvmbase.binding.command.BindingCommand;
import com.taopao.mvvmbase.binding.command.BindingConsumer;
import com.taopao.mvvmbase.utils.RxUtils;

import java.util.List;
import java.util.Random;

/**
 * @Author： 淘跑
 * @Date: 2018/7/17 14:29
 * @Use：
 */
public class RefreshViewModel extends BaseMVVMViewModel {
    public RefreshViewModel(BaseMVVMActivity activity) {
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
        mLimit = 10;

        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                getWanAndroid();
            }
        }.sendEmptyMessageDelayed(1, 1000);


    }

    boolean is = true;
    String page = "";

    public void getWanAndroid() {
        if (mPage == 1) {
            mWanAndroidAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
        }

        if (mPage == 2) {
            if (is) {
                page = mPage + "ss";//模拟请求失败

            } else {
                page = mPage + "";
            }
            is = !is;
        } else if (mPage == 5) {
            if (is) {
                page = mPage + "ss";//模拟请求失败

            } else {
                page = mPage + "";
            }
            is = !is;
        } else {
            page = mPage + "";
        }
        RetrofitProvider.getInstance(Api.API_WAN_ANDROID)
                .create(Api.class)
                .getHomeListError(page, null)
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

                        Toast.makeText(mContext, "下一次请求的页数: " + mPage, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mWanAndroidAdapter.loadMoreFail();
                    }
                });
    }


    public BindingCommand onRefresh = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            mPage = 1;
            getWanAndroid();
        }
    });


    public BindingCommand onLoadMore = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            getWanAndroid();
        }
    });

    public BindingCommand<Integer> itemClick = new BindingCommand<Integer>(new BindingConsumer<Integer>() {
        @Override
        public void call(Integer integer) {
            Toast.makeText(mContext, "点击了:" + integer, Toast.LENGTH_SHORT).show();
        }
    });


    public BindingCommand add = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            mWanAndroid.add(1, mWanAndroid.get(mWanAndroid.size() - 1));
        }
    });

    public BindingCommand sub = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            mWanAndroid.remove(1);
        }
    });

    public BindingCommand edit = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
        }
    });

}




