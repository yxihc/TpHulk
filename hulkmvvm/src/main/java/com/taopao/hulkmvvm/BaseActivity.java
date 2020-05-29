package com.taopao.hulkmvvm;

import android.os.Bundle;
import android.view.InflateException;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseActivity<DB extends ViewDataBinding,VM extends IViewModel> extends AppCompatActivity implements IActivity {

    public DB mBinding;
    public VM mViewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            initParam(savedInstanceState);
        } else if (getIntent() != null && getIntent().getExtras() != null) {
            initParam(getIntent().getExtras());
        }
        try {
            int layoutResID = getLayoutRes();
            //如果getLayoutRes返回0,框架则不会调用setContentView()
            if (layoutResID != 0) {
                mBinding = DataBindingUtil.setContentView(this, layoutResID);
            }
        } catch (Exception e) {
            if (e instanceof InflateException) throw e;
            e.printStackTrace();
        }
        createViewModel();
        if (mViewModel!=null){
            //让ViewModel拥有View的生命周期感应
            getLifecycle().addObserver(mViewModel);
            mBinding.setVariable(variableId(),mViewModel);
            mBinding.setLifecycleOwner(this);
        }
        initView(savedInstanceState);
        initData(savedInstanceState);
        initListener(savedInstanceState);
    }



    public void  createViewModel(){
        if (mViewModel == null) {
            Class modelClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[1];
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                modelClass = BaseViewModel.class;
            }
            mViewModel = (VM) new ViewModelProvider(this).get(modelClass);
        }
    }

}
