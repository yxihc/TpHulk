package com.taopao.mvvmbase.base;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.*;
import com.chad.library.adapter.base.BaseViewHolder;
import com.taopao.mvvmbase.BR;
import com.taopao.mvvmbase.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author：淘跑
 * @Date: 2018/7/14 16:02
 * @Use：
 */
public abstract class BaseBindingRvAdapter<T> extends BaseQuickAdapter<T, BaseBindingRvAdapter.BindingViewHolder> {

    public ObservableList<T> mTObservableList;

    @Override
    protected void convert(BindingViewHolder helper, T item) {
        ViewDataBinding binding = helper.getBinding();
        binding.setVariable(BR.item, item);
        binding.executePendingBindings();
        convert(helper, binding, item);
    }

    protected abstract void convert(BindingViewHolder helper, ViewDataBinding binding, T item);

    @Override
    protected View getItemView(int layoutResId, ViewGroup parent) {
        ViewDataBinding binding = DataBindingUtil.inflate(mLayoutInflater, layoutResId, parent, false);
        if (binding == null) {
            return super.getItemView(layoutResId, parent);
        }
        View view = binding.getRoot();
        view.setTag(R.id.BaseQuickAdapter_databinding_support, binding);
        return view;
    }

    public BaseBindingRvAdapter(@Nullable List<T> data) {
        this(0, data);
    }

    public BaseBindingRvAdapter(@LayoutRes int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
        this.mTObservableList = data == null ? new ObservableArrayList<T>() : (ObservableList<T>) data;
        if (layoutResId != 0) {
            this.mLayoutResId = layoutResId;
        }
        mTObservableList.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<T>>() {
            @Override
            public void onChanged(ObservableList<T> ts) {
                notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(ObservableList<T> ts, int positionStart, int itemCount) {
                notifyItemRangeChanged(positionStart, itemCount);
            }

            @Override
            public void onItemRangeInserted(ObservableList<T> ts, int positionStart, int itemCount) {
                notifyItemRangeInserted(positionStart, itemCount);
            }

            @Override
            public void onItemRangeMoved(ObservableList<T> ts, int fromPosition, int toPosition, int itemCount) {
                for (int i = 0; i < itemCount; i++) {
                    notifyItemMoved(fromPosition + i, toPosition + i);
                }
            }

            @Override
            public void onItemRangeRemoved(ObservableList<T> ts, int positionStart, int itemCount) {
                notifyItemRangeRemoved(positionStart, itemCount);
            }
        });
    }

    public static class BindingViewHolder extends BaseViewHolder {
        public BindingViewHolder(View view) {
            super(view);
        }

        public ViewDataBinding getBinding() {
            return (ViewDataBinding) itemView.getTag(R.id.BaseQuickAdapter_databinding_support);
        }
    }

}
