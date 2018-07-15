package com.taopao.mvvmbase.base;

import android.os.Bundle;

/**
 * @Author：淘跑
 * @Date: 2018/7/7 12:43
 * @Use：
 */

public interface IBaseViewModel {

    /**
     * View的界面创建时回调
     */
    void onCreate();

    /**
     * View的界面销毁时回调
     */
    void onDestroy();

    /**
     * 注册RxBus
     */
    void registerRxBus();

    /**
     * 移除RxBus
     */
    void removeRxBus();


    void initParam(Bundle bundle);

}
