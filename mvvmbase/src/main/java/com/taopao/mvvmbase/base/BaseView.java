package com.taopao.mvvmbase.base;

/**
 * @Author： 淘跑
 * @Date: 2018/7/13 11:57
 * @Use: View基类
 */

public interface BaseView {
    /**
     * 用来加载前显示加载动画
     */
    void showLoadingDialog();

    /**
     * 用来取消加载动画
     */
    void hideLoadingDialog();

    /**
     * 显示第一次加载的加载中界面
     */
    void showLoadingView();

    /**
     * 刷新界面
     */
    void refreshView();

    /**
     * 加载失败返回的错误码以及错误信息
     */

    void onErrorMsg(int errorCode, String errorMsg);


    /**
     * 显示加载错误界面
     */
    void showErrorView();

    /**
     * 显示加载数据为空的布局
     */
    void showEmptyView();

    /**
     * 显示正常显示的状态
     */
    void showNormalView();


    /**
     * 显示登录错误或者登录失效的Dialog
     */
    void showLoginDialog(int errorCode, String msg);


}
