package com.taopao.mvvmbase;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * @Author： 淘跑
 * @Date: 2018/7/5 16:48
 * @Use：
 */
public class User extends BaseObservable {
    private String name;

    public User(String name) {
        this.name = name;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }
}
