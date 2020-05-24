package com.taopao.tphulk.mvvmdemo;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.ViewModel;

public class TestViewModel extends ViewModel implements LifecycleObserver {

    public ObservableField<String> age = new ObservableField<>("ObservableField");

}
