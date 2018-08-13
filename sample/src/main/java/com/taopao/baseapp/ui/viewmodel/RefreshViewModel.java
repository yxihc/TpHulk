package com.taopao.baseapp.ui.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.widget.Toast;

import com.taopao.baseapp.R;
import com.taopao.baseapp.http.Api;
import com.taopao.baseapp.http.RetrofitProvider;
import com.taopao.baseapp.http.RxSubscriber;
import com.taopao.baseapp.model.BaseResponse;
import com.taopao.baseapp.model.WanAndroidResponse;
import com.taopao.baseapp.ui.activity.FragmentTestActivity;
import com.taopao.baseapp.ui.activity.MainActivity;
import com.taopao.mvvmbase.base.BaseBindingRvAdapter;
import com.taopao.mvvmbase.base.BaseMVVMActivity;
import com.taopao.mvvmbase.base.BaseMVVMViewModel;
import com.taopao.mvvmbase.base.ViewState;
import com.taopao.mvvmbase.binding.command.BindingAction;
import com.taopao.mvvmbase.binding.command.BindingCommand;
import com.taopao.mvvmbase.binding.command.BindingConsumer;
import com.taopao.mvvmbase.utils.RxUtils;

import java.util.List;

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
    public BaseBindingRvAdapter<WanAndroidResponse.DatasBean> mWanAndroidAdapter = new BaseBindingRvAdapter<WanAndroidResponse.DatasBean>(R.layout.recycle_item_wanandroid, mWanAndroid, mContext);

    @Override
    public void onCreate() {
        super.onCreate();
        mLimit = 10;
        getWanAndroid();
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
                .subscribe(new RxSubscriber<BaseResponse<WanAndroidResponse>>(mViewState, mEvent, hideDialogAndRefresh) {
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
//                        mViewState.set(ViewState.Normal_view);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mWanAndroidAdapter.loadMoreFail();
                    }
                });
    }


    /**
     * 刷新界面
     */
    public BindingCommand onRefresh = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            mPage = 1;
            getWanAndroid();
        }
    });

    /**
     * 加载更多
     */
    public BindingCommand onLoadMore = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            getWanAndroid();
        }
    });

    /**
     * item点击事件
     */
    public BindingCommand<Integer> itemClick = new BindingCommand<Integer>(new BindingConsumer<Integer>() {
        @Override
        public void call(Integer integer) {
            Toast.makeText(mContext, "点击了:" + integer, Toast.LENGTH_SHORT).show();
        }
    });


    /**
     * 增加一条数据
     */
    public BindingCommand add = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            mWanAndroid.add(1, mWanAndroid.get(mWanAndroid.size() - 1));
        }
    });

    /**
     * 减少一条数据
     */
    public BindingCommand sub = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            mWanAndroid.remove(1);
        }
    });


    /**
     * 模拟错误界面
     */
    public BindingCommand error = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            mViewState.set(ViewState.Error_view);
        }
    });

    /**
     * 模拟空界面
     */
    public BindingCommand empty = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            mViewState.set(ViewState.Empty_view);
        }
    });

    /**
     * smartrefrush界面
     */
    public BindingCommand refresh = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(MainActivity.class);
        }
    });
    /**
     * fragment界面
     */
    public BindingCommand fragment1 = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(FragmentTestActivity.class);
        }
    });


    /**
     * 模拟崩溃
     */
    public BindingCommand ex = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            throw new NullPointerException("呦呦呦,空指针异常了哦");
        }
    });

}




