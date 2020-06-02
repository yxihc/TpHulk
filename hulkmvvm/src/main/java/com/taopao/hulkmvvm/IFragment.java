/**
 * Copyright (C), 2017-2020
 * Author: taopao
 * Date: 2020/6/2 下午2:18
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
package com.taopao.hulkmvvm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

/**
 * @Author: TaoPao
 * @Date: 2020/6/2 下午2:18
 * @Description: java类作用描述
 */
public interface IFragment<VM extends BaseViewModel> {

    int getLayoutRes(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);


    default ViewBinding obtainViewBinding(@Nullable LayoutInflater layoutInflater){
        return null;
    }

    int variableId();
    default void initViewDataBinding(){

    }
    /**
     * 初始化 View,
     *
     * @param savedInstanceState
     * @return
     */
    default void initView(@Nullable Bundle savedInstanceState) {

    }
    /**
     * 初始化数据
     *
     * @param savedInstanceState
     */
    default void initData(@Nullable Bundle savedInstanceState) {

    }
    /**
     * 初始化监听
     * @param savedInstanceState
     */
    default void initListener(@Nullable Bundle savedInstanceState) {

    }
    /**
     * 页面传值
     *
     * @param bundle
     */
    default void initParam(Bundle bundle) {

    }
    /**
     * 初始化ViewModel
     *
     * @return 继承BaseViewModel的ViewModel
     */
    @Nullable
    default VM obtainViewModel() {
        return null;
    }
//


}