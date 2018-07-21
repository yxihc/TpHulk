package com.taopao.mvvmbase.base;

import android.view.View;

/**
 * @Author： 淘跑
 * @Date: 2018/7/16 10:59
 * @Use：
 */

public class ViewState {
    /**
     * 显示正常显示的状态
     */
    public final static int Normal_view = 0;
    /**
     * /**
     * 用来显示没有网络时候的界面
     */
    public final static int NoNetwork_view = 1;
    /**
     * 显示加载错误界面
     */
    public final static int Error_view = 2;

    /**
     * 显示加载中的页面
     */
    public final static int Loading_view = 3;

}
