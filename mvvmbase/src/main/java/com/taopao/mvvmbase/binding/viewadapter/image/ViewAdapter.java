package com.taopao.mvvmbase.binding.viewadapter.image;


import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * @Author： 淘跑
 * @Date: 2018/7/5 11:43
 * @Use：
 */
public final class ViewAdapter {
    @BindingAdapter(value = {"url", "placeholderRes"}, requireAll = false)
    public static void setImageUri(ImageView imageView, String url, int placeholderRes) {
        if (!TextUtils.isEmpty(url)) {
            //使用Glide框架加载图片
//            Glide.with(imageView.getContext())
//                    .load(url)
//                    .apply(new RequestOptions().placeholder(placeholderRes))
//                    .into(imageView);
            Picasso.get().load(url).into(imageView);
        }
    }
}

