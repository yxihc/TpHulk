package com.taopao.mvvmbase;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.taopao.mvvmbase.databinding.ActivityTextBBinding;

public class TextBActivity extends AppCompatActivity {

    private ActivityTextBBinding mViewDataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_text_b);
        mViewDataBinding.setUser(new User("zhangs"));
    }
}
