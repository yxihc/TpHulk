package com.taopao.mvvmbase.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.List;

/**
 * @Author：淘跑
 * @Date: 2018/7/14 16:02
 * @Use：
 */
public abstract class BaseBindingRvAdapter<T, BM extends BaseItemViewModel> extends RecyclerView.Adapter<BaseBindingRvAdapter.BindingViewHolder> {
    protected Context mContext;
    protected int mLayoutResId;
    protected LayoutInflater mLayoutInflater;
    protected ObservableList<T> mData = new ObservableArrayList<T>();

    public BaseBindingRvAdapter(@LayoutRes int layoutResId, @Nullable List<T> data) {
        this.mData = data == null ? new ObservableArrayList<T>() : (ObservableList<T>) data;
        if (layoutResId != 0) {
            this.mLayoutResId = layoutResId;
        }
        mData.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<T>>() {
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

    public BaseBindingRvAdapter(@Nullable List<T> data) {
        this(0, data);
    }

    public BaseBindingRvAdapter(@LayoutRes int layoutResId) {
        this(layoutResId, null);
    }

    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(mContext);
        }
        ViewDataBinding binding = DataBindingUtil.inflate(mLayoutInflater, mLayoutResId, parent, false);
        return new BindingViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull BaseBindingRvAdapter.BindingViewHolder holder, int position) {
        ViewDataBinding binding = DataBindingUtil.getBinding(holder.itemView);
        BM bm = initViewModel();
        bm.setData(getItem(position));
        binding.setVariable(initVariableId(), bm);
        binding.executePendingBindings();
    }

    protected abstract int initVariableId();

    protected abstract BM initViewModel();


    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    /**
     * 得到item
     *
     * @param position
     * @return
     */
    @Nullable
    public T getItem(@IntRange(from = 0) int position) {
        if (position >= 0 && position < mData.size())
            return mData.get(position);
        else
            return null;
    }

    /**
     * @return 列表数据
     */
    @NonNull
    public List<T> getData() {
        return mData;
    }

    public class BindingViewHolder extends RecyclerView.ViewHolder {
        public BindingViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
        }
    }

}
