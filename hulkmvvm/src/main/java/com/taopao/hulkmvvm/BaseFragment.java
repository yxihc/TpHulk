/**
 * Author: taopao
 * Date: 2020/6/2 下午2:16
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
package com.taopao.hulkmvvm;

import android.os.Bundle;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @Author: TaoPao
 * @Date: 2020/6/2 下午2:16
 * @Description: -----------------------------------------------
 */
public abstract class BaseFragment<DB extends ViewDataBinding, VM extends BaseViewModel> extends Fragment implements IFragment<VM> {
    public DB mBinding;
    public VM mViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            initParam(savedInstanceState);
        } else if (getArguments() != null) {
            initParam(getArguments());
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            int layoutResID = getLayoutRes(inflater, container, savedInstanceState);
            //  如果getLayoutRes返回0,框架则不会调用setContentView()
            if (layoutResID != 0) {
                mBinding = DataBindingUtil.inflate(inflater, getLayoutRes(inflater, container, savedInstanceState), container, false);
                return mBinding.getRoot();
            }
        } catch (Exception e) {
            if (e instanceof InflateException) throw e;
            e.printStackTrace();
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewDataBinding();
        initView(savedInstanceState);
        initData(savedInstanceState);
        initListener(savedInstanceState);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mBinding != null) {
            mBinding.unbind();
        }
    }
    @Override
    public void initViewDataBinding() {
        mViewModel = obtainViewModel();
        if (mViewModel == null) {
            mViewModel = (VM) initViewModel();
        }
        if (mViewModel != null) {
            //让ViewModel拥有View的生命周期感应
            getLifecycle().addObserver(mViewModel);
            mBinding.setVariable(variableId(), mViewModel);
            mBinding.setLifecycleOwner(this);
        }
    }
    private ViewModel initViewModel() {
        Class modelClass;
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[1];
        } else {
            //如果没有指定泛型参数，则默认使用BaseViewModel
            modelClass = BaseViewModel.class;
        }
        ViewModel viewModel = new ViewModelProvider(this).get(modelClass);
        return viewModel;
    }
}