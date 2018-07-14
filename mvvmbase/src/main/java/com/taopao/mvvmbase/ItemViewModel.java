package com.taopao.mvvmbase;

import android.content.Context;
import android.databinding.ObservableField;

import com.taopao.mvvmbase.base.BaseItemViewModel;

/**
 * @Author：淘跑
 * @Date: 2018/7/14 17:59
 * @Use：
 */

public class ItemViewModel extends BaseItemViewModel {
    public ItemViewModel(Context context) {
        super(context);
    }

    public ObservableField<User> mUser = new ObservableField<>();

    @Override
    public void setData(Object data) {
        super.setData(data);
        mUser.set((User) mData);
    }
}
