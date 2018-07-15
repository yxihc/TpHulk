package com.taopao.mvvmbase;

import com.taopao.mvvmbase.base.BaseActivity;
import com.taopao.mvvmbase.databinding.ActivityTextBBinding;

public class TextBActivity extends BaseActivity<ActivityTextBBinding, TextBViewModel> {
    @Override
    protected int getContentView() {
        return R.layout.activity_text_b;
    }

    @Override
    protected TextBViewModel initViewModel() {
        return new TextBViewModel(this);
    }
}
