package com.taopao.mvvmbase.binding.viewadapter.swiperefresh;

import android.databinding.BindingAdapter;
import android.support.v4.widget.SwipeRefreshLayout;

import com.taopao.mvvmbase.binding.command.BindingCommand;


/**
 * @Author： 淘跑
 * @Date: 2018/7/5 11:43
 * @Use：
 */
public class ViewAdapter {
    @BindingAdapter(value = {"onRefreshCommand"})
    public static void onRefreshCommand(SwipeRefreshLayout swipeRefreshLayout, final BindingCommand onRefreshCommand) {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (onRefreshCommand != null) {
                    onRefreshCommand.execute();
                }
            }
        });
    }


    /**
     * 设置刷新状态 需要状态改变才可以
     *
     * @param swipeRefreshLayout
     * @param isRefresh
     */
    @BindingAdapter(value = {"isRefreshing"}, requireAll = false)
    public static void isRefreshing(SwipeRefreshLayout swipeRefreshLayout, boolean isRefresh) {
        swipeRefreshLayout.setRefreshing(isRefresh);
    }


    /**
     * 直接关闭刷新 需要状态改变才可以
     *
     * @param swipeRefreshLayout
     * @param isRefresh
     */
    @BindingAdapter(value = {"finishRefresh"}, requireAll = false)
    public static void finishRefresh(SwipeRefreshLayout swipeRefreshLayout, boolean isRefresh) {
        swipeRefreshLayout.setRefreshing(false);
    }


//    @BindingAdapter(value = {"onRefreshCommand"})
//    public static void onRefreshCommand(SwipeRefreshLayout swipeRefreshLayout, final BindingCommand onRefreshCommand) {
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                if (onRefreshCommand != null) {
//                    onRefreshCommand.execute();
//                }
//            }
//        });
//
//    }

}
