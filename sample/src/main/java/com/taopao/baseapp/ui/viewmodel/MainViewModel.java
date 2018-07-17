package com.taopao.baseapp.ui.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;

import com.taopao.baseapp.base.BaseViewModel;
import com.taopao.baseapp.http.Api;
import com.taopao.baseapp.http.RetrofitProvider;
import com.taopao.baseapp.http.RxSubscriber;
import com.taopao.baseapp.model.ImgListInfo;
import com.taopao.baseapp.ui.adapter.RvGrilsAdapter;
import com.taopao.mvvmbase.base.BaseMVVMActivity;
import com.taopao.mvvmbase.binding.command.BindingAction;
import com.taopao.mvvmbase.binding.command.BindingCommand;
import com.taopao.mvvmbase.utils.RxUtils;

/**
 * @Author： 淘跑
 * @Date: 2018/7/17 14:29
 * @Use：
 */
public class MainViewModel extends BaseViewModel {
    public MainViewModel(BaseMVVMActivity activity) {
        super(activity);
    }

    public ObservableList<ImgListInfo.ResultsBean> mGrils = new ObservableArrayList<>();

    public RvGrilsAdapter mGrilsAdapter = new RvGrilsAdapter(mGrils);

    @Override
    public void onCreate() {
        super.onCreate();
        getGrils();
    }

    public void getGrils() {
        showDialog("请骚等...");
        RetrofitProvider.getInstance().create(Api.class)
                .getImgListPage(mPage + "")
                .compose(RxUtils.<ImgListInfo>schedulersTransformer())
                .subscribe(new RxSubscriber<ImgListInfo>(mContext, mEvent, hideDialog) {
                    @Override
                    public void onResult(ImgListInfo imgListInfo) {
                        if (mPage == 1) {
                            mGrils.clear();
                        }
                        mGrils.addAll(imgListInfo.getResults());
                    }
                });
    }

    public BindingCommand onLoadMore = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            mPage++;
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

}




