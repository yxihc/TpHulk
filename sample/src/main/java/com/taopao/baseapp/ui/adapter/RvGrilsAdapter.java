package com.taopao.baseapp.ui.adapter;

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
    public RvGrilsAdapter(@Nullable List<ImgListInfo.ResultsBean> data) {
        super(R.layout.recycle_item_grils, data);
    }

    @Override
    protected void convert(BindingViewHolder helper, ViewDataBinding binding, ImgListInfo.ResultsBean item) {

    }
}
