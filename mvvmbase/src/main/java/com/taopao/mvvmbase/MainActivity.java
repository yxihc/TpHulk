package com.taopao.mvvmbase;

import android.databinding.Observable;
import android.util.Log;
import android.widget.Toast;

import com.taopao.mvvmbase.base.BaseActivity;
import com.taopao.mvvmbase.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity<ActivityMainBinding, LoginViewModel> {
    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected LoginViewModel initViewModel() {
        return new LoginViewModel(this);
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        mViewModel.title.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                Toast.makeText(MainActivity.this, mViewModel.title.get() + ":11111", Toast.LENGTH_SHORT).show();
                mViewModel.title.set("000");
            }
        });
        mViewModel.mEvent.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                Toast.makeText(MainActivity.this, "改变了", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
