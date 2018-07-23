package com.taopao.mvvmbase.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.taopao.mvvmbase.R;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * @Author： 淘跑
 * @Date: 2018/4/26 08:34
 * @Use： 图片加载管理 方便以后切换图片加载库
 */

public class ImageLoader {
    //******************************默认加载方式********************************

    /**
     * 默认加载图片的方式
     */
    public static void loadImage(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new NullPointerException("GlideImageLoader with ImageView is null");
        }
        Glide.with(context)
                .load(url)
                .into(imageView);
    }

    /**
     * 默认加载图片的方式
     */
    public static void loadImage(Context context, ImageView imageView, String url, int errorResId) {
        if (imageView == null) {
            throw new NullPointerException("GlideImageLoader with ImageView is null");
        }

//        R.drawable.error_artwork
//        R.drawable.error_artworkranking
//        R.drawable.error_banner
//        R.drawable.error_information
        RequestOptions options = new RequestOptions()
                .error(errorResId);//加载错误的图
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }

    //默认加载
    public static void loadImage(Context context, ImageView imageView, int resId) {
        if (imageView == null) {
            throw new NullPointerException("GlideImageLoader with ImageView is null");
        }
        Glide.with(context)
                .load(resId)
                .into(imageView);
    }

    //默认加载
    public static void loadImage(Context context, ImageView imageView, Uri uri) {
        if (imageView == null) {
            throw new NullPointerException("GlideImageLoader with ImageView is null");
        }
        Glide.with(context)
                .load(uri)
                .into(imageView);
    }


    //******************************默认加载方式********************************


    //******************************加载圆形图片********************************

    /**
     * 加载圆形图片
     */
    public static void loadImageRound(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new NullPointerException("GlideImageLoader with ImageView is null");
        }
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.circleCrop();
        requestOptions.bitmapTransform(new CircleCrop());
        Glide.with(context)
                .load(url)
                .apply(requestOptions)
                .into(imageView);
    }

    /**
     * 加载圆形图片
     */
    public static void loadImageRound(Context context, ImageView imageView, String url, int errorResId) {
        if (imageView == null) {
            throw new NullPointerException("GlideImageLoader with ImageView is null");
        }

//        R.drawable.error_artwork
//        R.drawable.error_artworkranking
//        R.drawable.error_banner
//        R.drawable.error_information

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.circleCrop();
        requestOptions.bitmapTransform(new CircleCrop());
        requestOptions.error(errorResId);  //加载错误的图

        Glide.with(context)
                .load(url)
                .apply(requestOptions)
                .into(imageView);
    }


    public static void loadImageRound(Context context, ImageView imageView, int resId) {
        if (imageView == null) {
            throw new NullPointerException("GlideImageLoader with ImageView is null");
        }
        Glide.with(context)
                .load(resId)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(imageView);
    }

    public static void loadImageRound(Context context, ImageView imageView, Uri uri) {
        if (imageView == null) {
            throw new NullPointerException("GlideImageLoader with ImageView is null");
        }
        Glide.with(context)
                .load(uri)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(imageView);
    }
    //******************************加载圆形图片********************************


    //******************************加载图片带动画效果********************************

    /**
     * 加载带动画淡入淡出效果
     */
    public static void loadImageAnimationFade(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new NullPointerException("GlideImageLoader with ImageView is null");
        }
        Glide.with(context)
                .load(url)
                .transition(withCrossFade())
                .into(imageView);
    }

    public static void loadImageAnimationFade(Context context, ImageView imageView, Uri uri) {
        if (imageView == null) {
            throw new NullPointerException("GlideImageLoader with ImageView is null");
        }
        Glide.with(context)
                .load(uri)
                .transition(withCrossFade())
                .into(imageView);
    }

    public static void loadImageAnimationFade(Context context, ImageView imageView, int resId) {
        if (imageView == null) {
            throw new NullPointerException("GlideImageLoader with ImageView is null");
        }
        Glide.with(context)
                .load(resId)
                .transition(withCrossFade())
                .into(imageView);
    }


    /**
     * 加载带动画淡入淡出效果
     */
    public static void loadImageAnimationFade(Context context, ImageView imageView, String url, int errorResId) {
        if (imageView == null) {
            throw new NullPointerException("GlideImageLoader with ImageView is null");
        }


//        R.drawable.error_artwork
//        R.drawable.error_artworkranking
//        R.drawable.error_banner
//        R.drawable.error_information
        RequestOptions options = new RequestOptions()
                .error(errorResId);//加载错误的图
        Glide.with(context)
                .load(url)
                .apply(options)
                .transition(withCrossFade())
                .into(imageView);
    }

    //******************************加载图片带动画效果********************************


    //******************************显示圆角图片********************************

    /**
     * 显示圆角图片
     */
    public static void loadImageCircular(Context context, ImageView imageView, String url, int radius) {
        if (imageView == null) {
            throw new NullPointerException("GlideImageLoader with ImageView is null");
        }
        Glide.with(context)
                .load(url)
                .apply(RequestOptions.bitmapTransform(new GlideRoundTransform(context, radius)))
                .into(imageView);
    }

    public static void loadImageCircular(Context context, ImageView imageView, Uri uri, int radius) {
        if (imageView == null) {
            throw new NullPointerException("GlideImageLoader with ImageView is null");
        }
        Glide.with(context)
                .load(uri)
                .apply(RequestOptions.bitmapTransform(new GlideRoundTransform(context, radius)))
                .into(imageView);
    }

    public static void loadImageCircular(Context context, ImageView imageView, int resId, int radius) {
        if (imageView == null) {
            throw new NullPointerException("GlideImageLoader with ImageView is null");
        }
        Glide.with(context)
                .load(resId)
                .apply(RequestOptions.bitmapTransform(new GlideRoundTransform(context, radius)))
                .into(imageView);
    }

    /**
     * 显示圆角图片
     */
    public static void loadImageCircular(Context context, ImageView imageView, String url, int radius, int errorResId) {
        if (imageView == null) {
            throw new NullPointerException("GlideImageLoader with ImageView is null");
        }

//        R.drawable.error_artwork
//        R.drawable.error_artworkranking
//        R.drawable.error_banner
//        R.drawable.error_information
        RequestOptions options = new RequestOptions()
                .bitmapTransform(new GlideRoundTransform(context, radius))
                .error(errorResId);//加载错误的图
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView)
        ;
    }


    //******************************显示圆角图片********************************

    /**
     * 显示小图
     */
    public static void loadImageSmall(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new NullPointerException("GlideImageLoader with ImageView is null");
        }
        Glide.with(context).load(url)
                .thumbnail(0.5f)
                .into(imageView);
    }

    /**
     * 加载完成监听
     */
    public static void loadImageListener(Context context, ImageView imageView, String url, RequestListener<Drawable> listener) {
        if (imageView == null) {
            throw new NullPointerException("GlideImageLoader with ImageView is null");
        }
        Glide.with(context).load(url)
                .listener(listener)
                .into(imageView);
    }


    //******************************给除了ImageView以外的View设置背景图片********************************

    /**
     * 给除了ImageView以外的View设置背景图片
     */
    public static void loadViewBackground(Context context, final View view, String url) {
        if (view == null) {
            throw new NullPointerException("GlideImageLoader with view is null");
        }
        Glide.with(context).load(url)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        view.setBackground(resource);
                    }
                });
    }

    public static void loadViewBackground(Context context, final View view, Uri uri) {
        if (view == null) {
            throw new NullPointerException("GlideImageLoader with view is null");
        }
        Glide.with(context).load(uri)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        view.setBackground(resource);
                    }
                });
    }
    //******************************给除了ImageView以外的View设置背景图片********************************


    //******************************以下是用法记录********************************

    /**
     * 得到bitmap  用法记录
     */
    public static void getImageBitmap(Context context, String url) {
//        新用法
        RequestOptions options = new RequestOptions()
                .centerCrop()
//                .placeholder(R.drawable.ic_launcher)//占位图
//                .error(R.drawable.ic_launcher)//加载错误的图
                .priority(Priority.HIGH);//优先级
        Glide.with(context)
                .asBitmap()
                .load(url)
                .apply(options)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                    }
                });
    }


}
