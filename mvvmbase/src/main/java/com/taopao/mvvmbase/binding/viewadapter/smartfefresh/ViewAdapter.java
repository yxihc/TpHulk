package com.taopao.mvvmbase.binding.viewadapter.smartfefresh;

import android.databinding.BindingAdapter;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.taopao.mvvmbase.binding.command.BindingCommand;

/**
 * @Author：淘跑
 * @Date: 2018/7/14 16:02
 * @Use：SmartRefreshLayout列表刷新的绑定适配器
 */
public class ViewAdapter {

    @BindingAdapter(value = {"onRefreshCommand", "onLoadMoreCommand"}, requireAll = false)
    public static void OnRefreshLoadMoreListener(SmartRefreshLayout layout, final BindingCommand onRefreshCommand, final BindingCommand onLoadMoreCommand) {
        layout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (onLoadMoreCommand != null) {
                    onLoadMoreCommand.execute();
                }
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (onRefreshCommand != null) {
                    onRefreshCommand.execute();
                }
            }
        });
    }

    @BindingAdapter(value = "finishRefreshOrLoadMore")
    public static void finishRefreshOrLoadMore(SmartRefreshLayout layout, boolean f) {
        layout.finishRefresh();
        layout.finishLoadMore();
    }
}
