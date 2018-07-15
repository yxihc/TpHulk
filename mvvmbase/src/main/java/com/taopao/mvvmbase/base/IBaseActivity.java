package com.taopao.mvvmbase.base;

import android.os.Bundle;

/**
 * @Author： 淘跑
 * @Date: 2018/7/5 15:23
 * @Use：
 */
public interface IBaseActivity {
    /**
     * 页面间传值
     *
     * @param bundle
     */
    void initParam(Bundle bundle);

    /**
     * 控件的一些常用操作
     */
    void initView();

    /**
     * 请求数据等操作
     */
    void initData();

    /**
     * 设置沉浸式状态栏
     */
    void setStatusBar();

    /**
     * 刷新布局
     */
    void refreshLayout();


    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    int initVariableId();


    void initViewObservable();

}
