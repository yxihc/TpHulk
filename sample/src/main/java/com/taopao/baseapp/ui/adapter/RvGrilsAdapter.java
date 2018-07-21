package com.taopao.baseapp.ui.adapter;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;

import com.taopao.baseapp.R;
import com.taopao.baseapp.model.ImgListInfo;
import com.taopao.mvvmbase.base.BaseBindingRvAdapter;

import java.util.List;

/**
 * @Author： 淘跑
 * @Date: 2018/7/17 14:35
 * @Use：
 */
public class RvGrilsAdapter extends BaseBindingRvAdapter<ImgListInfo.ResultsBean> {
    public RvGrilsAdapter(@Nullable List<ImgListInfo.ResultsBean> data, Context context) {
        super(R.layout.recycle_item_grils, data, context);
    }

    @Override
    protected void convert(BindingViewHolder helper, ViewDataBinding binding, ImgListInfo.ResultsBean item) {

    }
}
