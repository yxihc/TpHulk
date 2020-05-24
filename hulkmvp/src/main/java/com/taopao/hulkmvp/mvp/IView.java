package com.taopao.hulkmvp.mvp;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import static com.taopao.hulkbase.utils.Preconditions.checkNotNull;


/**
 * ================================================
 *
 * @Author：淘跑
 * @Date: 2019/4/17 15:33
 * @Use： 框架要求框架中的每个 View 都需要实现此类, 以满足规范
 * <p>
 * ================================================
 */
public interface IView {

    /**
     * 显示加载
     */
    default void showLoading() {

    }

    /**
     * 隐藏加载
     */
    default void hideLoading() {

    }

    /**
     * 显示信息
     *
     * @param message 消息内容, 不能为 {@code null}
     */
    default void showMessage(@NonNull String message) {
    }

    /**
     * 跳转 {@link AppCompatActivity}
     *
     * @param intent {@code intent} 不能为 {@code null}
     */
    default void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
    }

    /**
     * 杀死自己
     */
    default void killMyself() {

    }


    default void showError() {
    }

    default void showSuccess() {

    }

}
