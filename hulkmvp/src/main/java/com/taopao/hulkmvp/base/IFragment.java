package com.taopao.hulkmvp.base;


import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import com.taopao.hulkmvp.mvp.IPresenter;

/**
 * @Author：淘跑
 * @Date: 2019/4/17 15:56
 * @Use： 框架要求框架中的每个 {@link Fragment} 都需要实现此类,以满足规范
 * @see BaseFragment
 */
public interface IFragment<P extends IPresenter> {
    default  int getLayoutRes(){
        return 0;
    }

    default ViewBinding obtainViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container){
        return null;
    }

    /**
     * 初始化 View
     *
     * @return
     */
    default void initView(@NonNull View view) {
    }

    /**
     * 初始化数据
     *
     * @param savedInstanceState
     */
    void initData(@Nullable Bundle savedInstanceState);

    /**
     * 页面传值
     *
     * @param bundle
     */
    default void initParam(Bundle bundle) {

    }

    default void setData(@Nullable Object data) {
    }

    @Nullable
    default P obtainPresenter() {
        return null;
    }

    void setPresenter(@Nullable P presenter);


}
