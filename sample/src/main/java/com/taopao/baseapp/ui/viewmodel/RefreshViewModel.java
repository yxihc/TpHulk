package com.taopao.baseapp.ui.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
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
import com.taopao.mvvmbase.utils.RxUtils;

import junit.framework.TestResult;

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

    public RvGrilsAdapter mGrilsAdapter = new RvGrilsAdapter(mGrils);

    @Override
    public void onCreate() {
        super.onCreate();
        mLimit = 10;
        getGrils();
    }

    public void getGrils() {
        if (mPage == 1) {
            mGrilsAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
            showDialog("请骚等...");
        }
        RetrofitProvider.getInstance().create(Api.class)
                .getImgListPage(mPage + "")
                .compose(RxUtils.<ImgListInfo>schedulersTransformer())
                .subscribe(new RxSubscriber<ImgListInfo>(mContext, mEvent, hideDialog) {
                    @Override
                    public void onResult(ImgListInfo imgListInfo) {
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


    boolean is = true;
    String page = "";

    public void getGrils1() {
        if (mPage == 2) {
            if (is) {
                page = mPage + "ss";//模拟请求失败

            } else {
                page = mPage + "";
            }
            is = !is;
        } else if (mPage == 5) {
            if (is) {
                page = mPage + "ss";

            } else {
                page = mPage + "";
            }
            is = !is;
        } else {
            page = mPage + "";
        }

        RetrofitProvider.getInstance().create(Api.class)
                .getImgListPage(page)
                .compose(RxUtils.<ImgListInfo>schedulersTransformer())
                .subscribe(new RxSubscriber<ImgListInfo>(mContext, mEvent, hideDialog) {
                    @Override
                    public void onResult(ImgListInfo imgListInfo) {
                        if (mPage == 1) {
                            mGrils.clear();
                        }
                        mGrils.addAll(imgListInfo.getResults());
                        CheckUpPageOrAdapter(imgListInfo.getResults(), mGrilsAdapter);
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




