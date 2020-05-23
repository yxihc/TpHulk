package com.taopao.hulkimg.listener;

public interface OnImageCompleteCallback {
    /**
     * 开始加载
     */
    void onShowLoading();
    /**
     * 隐藏加载
     */
    void onHideLoading();
}
