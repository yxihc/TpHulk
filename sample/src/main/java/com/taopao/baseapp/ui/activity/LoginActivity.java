package com.taopao.baseapp.ui.activity;

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
}
