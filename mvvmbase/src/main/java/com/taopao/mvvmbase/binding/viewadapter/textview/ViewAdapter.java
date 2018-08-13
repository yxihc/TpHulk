package com.taopao.mvvmbase.binding.viewadapter.textview;

import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.TextView;

import com.taopao.mvvmbase.utils.StringUtil;

public class ViewAdapter {
    @BindingAdapter(value = {"textIsNullGone"}, requireAll = false)
    public static void onClickCommand(TextView view, String string) {
        //如果是空的话就设置为Gone
        if (StringUtil.isBlank(string)) {
            if (view.getVisibility() != View.GONE) {
                view.setVisibility(View.GONE);
            }

        } else {
            if (view.getVisibility() != View.VISIBLE) {
                view.setVisibility(View.VISIBLE);
            }
            view.setText(string);
        }
    }

}
