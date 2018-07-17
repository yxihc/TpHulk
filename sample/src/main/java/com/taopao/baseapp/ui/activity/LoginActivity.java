package com.taopao.baseapp.ui.activity;

import android.databinding.Observable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;

import com.taopao.baseapp.R;
import com.taopao.baseapp.databinding.ActivityLoginBinding;
import com.taopao.baseapp.ui.viewmodel.LoginViewModel;
import com.taopao.mvvmbase.base.BaseMVVMActivity;

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
    public void initViewObservable() {
        super.initViewObservable();

        mViewModel.pwdIsHide.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                if (mViewModel.pwdIsHide.get()) {
                    //密码可见
                    //在xml中定义id后,使用binding可以直接拿到这个view的引用,不再需要findViewById去找控件了
                    mBinding.ivSwichPasswrod.setImageResource(R.mipmap.show_psw_press);
                    mBinding.etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //密码不可见
                    mBinding.ivSwichPasswrod.setImageResource(R.mipmap.show_psw);
                    mBinding.etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }
}
