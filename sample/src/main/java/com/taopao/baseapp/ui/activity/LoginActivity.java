package com.taopao.baseapp.ui.activity;

import com.taopao.baseapp.R;
import com.taopao.baseapp.databinding.ActivityLoginBinding;
import com.taopao.baseapp.ui.viewmodel.LoginViewModel;
import com.taopao.mvvmbase.base.BaseMVVMActivity;
import com.taopao.mvvmbase.utils.RxUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends BaseMVVMActivity<ActivityLoginBinding, LoginViewModel> {
    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected LoginViewModel initMVVMViewModel() {
        return new LoginViewModel(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void initView() {
        super.initView();

        //如果不想显示加载中动画
        showNormalView();


        Observable.timer(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .compose(RxUtils.<Long>schedulersTransformer())//timer 默认在新线程，所以需要切换回主线程
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        showNormalView();
                    }
                });

    }
}
