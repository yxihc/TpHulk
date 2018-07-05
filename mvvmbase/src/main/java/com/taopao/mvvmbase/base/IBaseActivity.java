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
     * 初始化数据
     */
    void initData();


    /**
     * 设置沉浸式状态栏
     */
    void setStatusBar();

}
