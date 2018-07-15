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
     * 加载失败返回的错误码以及错误信息
     */
    void onErrorMsg(int errorCode, String errorMsg);

    /**
     * 用来显示没有网络时候的界面
     */
    void showNoNetworkView();




    /**
     * 显示错误界面
     */
    void showErrorView();

    /**
     * 隐藏错误界面
     */
    void hideErrorView();

    /**
     * 显示手机登录的页面
     */
    void showLoginView();

    /**
     * 显示未登录页面
     */
    void showLogoutView();

    /**
     * 显示登录错误或者登录失效的Dialog
     */
    void showLoginDialog(String msg);


}
