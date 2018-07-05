package com.taopao.mvvmbase.binding.viewadapter.viewgroup;

/**
 * @Author： 淘跑
 * @Date: 2018/7/5 11:43
 * @Use：
 */
import android.databinding.ViewDataBinding;

public interface IBindingItemViewModel<V extends ViewDataBinding> {
    void injecDataBinding(V binding);
}
