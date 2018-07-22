package com.taopao.mvvmbase.binding.viewadapter.image;


import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.taopao.mvvmbase.binding.command.BindingCommand;
import com.taopao.mvvmbase.glide.GlideRoundTransform;


/**
 * @Author： 淘跑
 * @Date: 2018/7/5 11:43
 * @Use：
 */
public final class ViewAdapter {
    /**
     * @param imageView      imageView
     * @param url            图片的网络地址
     * @param round          是否显示圆形图片
     * @param placeholderRes 加载前的占位图
     * @param errorRes       加载错误的图
     * @param radius         显示圆角图片
     */
    @BindingAdapter(value = {"glideUrl", "glideRound", "glidePlaceholderRes", "glideErrorRes", "glideRadius"}, requireAll = false)
    public static void setImageUri(ImageView imageView, String url, boolean round, int placeholderRes, int errorRes, int radius) {
        if (!TextUtils.isEmpty(url)) {
            //使用Glide框架加载图片
            RequestOptions requestOptions = new RequestOptions();

            //加载圆形
            if (round) {
                requestOptions.circleCrop();
                requestOptions.bitmapTransform(new CircleCrop());
            }
            //加载前的占位图
            if (placeholderRes != 0) {
                requestOptions.placeholder(placeholderRes);
            }

            //加载错误的图
            if (errorRes != 0) {
                requestOptions.error(errorRes);
            }

            //显示圆角图片
            if (radius != 0) {
                requestOptions.bitmapTransform(new GlideRoundTransform(imageView.getContext(), radius));
            }

            Glide.with(imageView.getContext())
                    .load(url)
                    .apply(requestOptions)
                    .into(imageView);
        }
    }


    /**
     * 给view设置网络图片背景
     *
     * @param view
     * @param url
     */
    @BindingAdapter(value = {"glidBackgroundUrl"}, requireAll = false)
    public static void onCheckedChangedCommand(final View view, String url) {
        Glide.with(view.getContext()).load(url)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        view.setBackground(resource);
                    }
                });
    }
}

