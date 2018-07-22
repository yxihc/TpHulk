package com.taopao.baseapp.ui.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.taopao.baseapp.http.Api;
import com.taopao.baseapp.http.RetrofitProvider;
import com.taopao.baseapp.http.RxSubscriber;
import com.taopao.baseapp.model.ImgListInfo;
import com.taopao.baseapp.ui.adapter.RvGrilsAdapter;
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

    public ObservableList<ImgListInfo.ResultsBean> mGrils = new ObservableArrayList<>();

    public RvGrilsAdapter mGrilsAdapter = new RvGrilsAdapter(mGrils, mContext);

    @Override
    public void onCreate() {
        super.onCreate();
        mLimit = 10;

        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                getGrils();
            }
        }.sendEmptyMessageDelayed(1, 1000);


    }

    boolean is = true;
    String page = "";

    public void getGrils() {
        if (mPage == 1) {
            mGrilsAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
            showDialog("请骚等...");
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

        RetrofitProvider.getInstance().create(Api.class)
                .getImgListPage(page + "")
                .compose(RxUtils.<ImgListInfo>schedulersTransformer())
                .compose(RxUtils.<ImgListInfo>bindToLifecycle(mContext))
                .subscribe(new RxSubscriber<ImgListInfo>(mViewState, mEvent, hideDialog) {
                    @Override
                    public void onResult(final ImgListInfo imgListInfo) {
                        if (mPage == 1) {
                            mGrils.clear();
                        }
                        List<ImgListInfo.ResultsBean> results = imgListInfo.getResults();
                        mGrils.addAll(results);

                        CheckUpPageOrAdapter(imgListInfo.getResults(), mGrilsAdapter);
                        Toast.makeText(mContext, "下一次请求的页数: " + mPage, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mGrilsAdapter.loadMoreFail();
                    }
                });
    }


    public BindingCommand onRefresh = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            mPage = 1;
            getGrils();
        }
    });


    public BindingCommand onLoadMore = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            getGrils();
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
            mGrils.add(1, (mGrils.get(new Random().nextInt(mGrils.size()))));
        }
    });

    public BindingCommand sub = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (mGrils.size() > 2) {
                mGrils.remove(1);
            }
        }
    });

    public BindingCommand edit = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (mGrils.size() > 2) {
                mGrils.get(1).setUrl(mGrils.get(new Random().nextInt(mGrils.size())).getUrl());
            }
        }
    });

}




