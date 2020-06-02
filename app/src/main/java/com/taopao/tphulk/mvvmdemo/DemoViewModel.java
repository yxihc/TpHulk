package com.taopao.tphulk.mvvmdemo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.taopao.hulkbase.log.HLog;
import com.taopao.hulkmvvm.BaseViewModel;
import com.taopao.hulkmvvm.IModel;

public class DemoViewModel extends BaseViewModel<DemoModel> implements LifecycleObserver{
    public ObservableField<String> age = new ObservableField<>("18");
    public MutableLiveData<String> name =new MutableLiveData<>("张三");
    public DemoViewModel(@NonNull Application application) {
        super(application);
    }
    @Override
    public DemoModel obtainModel() {
        return new DemoModel();
    }

}
