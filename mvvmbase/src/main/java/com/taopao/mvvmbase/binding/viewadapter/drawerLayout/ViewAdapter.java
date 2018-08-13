package com.taopao.mvvmbase.binding.viewadapter.drawerLayout;

import android.databinding.BindingAdapter;
import android.support.v4.widget.DrawerLayout;

public class ViewAdapter {
    @BindingAdapter(value = {"closeDrawer", "drawerGravity"}, requireAll = false)
    public static void close(DrawerLayout view, boolean isOpen, int gravity) {
        view.closeDrawer(gravity);
    }
}
