package com.taopao.baseapp.ui.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.widget.Toast;

import com.taopao.baseapp.http.Api;
import com.taopao.baseapp.http.RetrofitProvider;
import com.taopao.baseapp.http.RxSubscriber;
import com.taopao.baseapp.model.ImgListInfo;
import com.taopao.baseapp.ui.activity.RefreshActivity;
import com.taopao.baseapp.ui.adapter.RvGrilsAdapter;
import com.taopao.mvvmbase.base.BaseMVVMActivity;
import com.taopao.mvvmbase.base.BaseMVVMViewModel;
import com.taopao.mvvmbase.binding.command.BindingAction;
import com.taopao.mvvmbase.binding.command.BindingCommand;
import com.taopao.mvvmbase.utils.RxUtils;

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

    public ObservableList<ImgListInfo.ResultsBean> mGrils = new ObservableArrayList<>();

    public RvGrilsAdapter mGrilsAdapter = new RvGrilsAdapter(mGrils, mContext);

    @Override
    public void onCreate() {
        super.onCreate();
        getGrils();
    }

    public void getGrils() {
        if (mPage == 1) {
            showDialog("请骚等...");
        }
        RetrofitProvider.getInstance().create(Api.class)
                .getImgListPage(mPage + "")
                .compose(RxUtils.<ImgListInfo>schedulersTransformer())
                .subscribe(new RxSubscriber<ImgListInfo>(mContext, mViewState, mEvent, hideDialog) {
                    @Override
                    public void onResult(ImgListInfo imgListInfo) {
                        if (mPage == 1) {
                            mGrils.clear();
                        }
                        mGrils.addAll(imgListInfo.getResults());
                        CheckUpPageOrAdapter(imgListInfo.getResults(), mGrilsAdapter);
                        Toast.makeText(mContext, "下一次请求的页数: " + mPage, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public BindingCommand onLoadMore = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            getGrils();
        }
    });

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
            mGrils.add(1, mGrils.get(mGrils.size() - 1));
        }
    });

    public BindingCommand sub = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //模拟加载失败
            RetrofitProvider.getInstance().create(Api.class)
                    .getImgListPage(mPage + "撒盛大速度")
                    .compose(RxUtils.<ImgListInfo>schedulersTransformer())
                    .subscribe(new RxSubscriber<ImgListInfo>(mContext, mViewState, mEvent, hideDialog, true) {
                        @Override
                        public void onResult(ImgListInfo imgListInfo) {
                            if (mPage == 1) {
                                mGrils.clear();
                            }
                            mGrils.addAll(imgListInfo.getResults());
                            CheckUpPageOrAdapter(imgListInfo.getResults(), mGrilsAdapter);
                            Toast.makeText(mContext, "下一次请求的页数: " + mPage, Toast.LENGTH_SHORT).show();
                        }
                    });
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

    public BindingCommand refresh = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(RefreshActivity.class);
//            startActivity(Main2Activity.class);
        }
    });

}




