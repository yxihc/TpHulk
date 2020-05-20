package com.taopao.hulkmvp;

import android.os.Bundle;
import android.view.InflateException;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

public  abstract class BaseActivity extends AppCompatActivity implements IActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
//            两个都有的话优先使用viewbinding
            ViewBinding viewBinding = setViewBinding(getLayoutInflater());
            if (viewBinding!=null){
                setContentView(viewBinding.getRoot());
            }else{
                int layoutResID = getLayoutRes();
                //如果getLayoutRes返回0,框架则不会调用setContentView()
                if (layoutResID != 0) {
                    setContentView(layoutResID);
                }
            }
        } catch (Exception e) {
            if (e instanceof InflateException) throw e;
            e.printStackTrace();
        }
        initView(savedInstanceState);
        initData(savedInstanceState);
        initListener(savedInstanceState);
    }
}
