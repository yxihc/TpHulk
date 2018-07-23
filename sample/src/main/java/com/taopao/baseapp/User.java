package com.taopao.baseapp;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * @Author： 淘跑
 * @Date: 2018/7/5 16:48
 * @Use：
 */
public class User extends BaseObservable {
    private String name;

    @Bindable
    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
        notifyPropertyChanged(BR.age);
    }
 
    public User(String name, String age) {
        this.name = name;
        this.age = age;
    }

    private String age;

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
