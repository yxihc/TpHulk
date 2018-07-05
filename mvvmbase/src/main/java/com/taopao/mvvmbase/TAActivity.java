package com.taopao.mvvmbase;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.taopao.mvvmbase.databinding.ActivityTaBinding;

public class TAActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityTaBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_ta);
    }
}
