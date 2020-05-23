package com.taopao.hulkimg.engine;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.taopao.hulkimg.listener.OnImageCompleteCallback;

public interface ImageEngine {
    /**
     * Loading image
     *
     * @param context
     * @param url
     * @param imageView
     */
    void loadImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView);
    /**
     * Loading image
     *
     * @param context
     * @param url
     * @param imageView
     */
    void loadImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView,  OnImageCompleteCallback callback);
    /**
     * Load album catalog pictures
     *
     * @param context
     * @param url
     * @param imageView
     */
    void loadFolderImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView);

    /**
     * Load GIF image
     *
     * @param context
     * @param url
     * @param imageView
     */
    void loadAsGifImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView);

    /**
     * Load picture list picture
     *
     * @param context
     * @param url
     * @param imageView
     */
    void loadGridImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView);
}
