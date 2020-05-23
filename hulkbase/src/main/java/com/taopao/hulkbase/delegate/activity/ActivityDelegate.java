package com.taopao.hulkbase.delegate.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @Copyright (C), 2017-2020
 * @Author: TaoPao
 * @Date: 2020/5/22 下午4:03
 * @Description:  {@link AppCompatActivity} 代理类,用于框架内部在每个 {@link AppCompatActivity} 的对应生命周期中插入需要的逻辑
 */
public interface ActivityDelegate {

    void onCreate(@Nullable Bundle savedInstanceState);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onSaveInstanceState(@NonNull Bundle outState);

    void onDestroy();
}
