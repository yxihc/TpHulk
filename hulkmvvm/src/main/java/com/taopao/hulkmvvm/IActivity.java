package com.taopao.hulkmvvm;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

/**
 * ================================================
 *
 * @Author：淘跑
 * @Date: 2020/4/17 10:17
 * @Use： 框架要求框架中的每个 {@link AppCompatActivity} 都需要实现此类,以满足规范
 * @see BaseActivity
 * <p>
 * ================================================
 */
public interface IActivity {

      int getLayoutRes();


    default ViewBinding obtainViewBinding(@Nullable LayoutInflater layoutInflater){
        return null;
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

//    @Nullable
//    default P obtainPresenter() {
//        return null;
//    }
//
//    void setPresenter(@Nullable P presenter);


}
