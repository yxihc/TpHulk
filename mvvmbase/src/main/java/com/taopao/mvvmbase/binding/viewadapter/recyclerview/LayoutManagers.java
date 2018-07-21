package com.taopao.mvvmbase.binding.viewadapter.recyclerview;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * A collection of factories to create RecyclerView LayoutManagers so that you can easily set them
 * in your layout.
 */
public class LayoutManagers {


    //Animation 默认提供5种方法（渐显、缩放、从下到上，从左到右、从右到左）
    /**
     * 渐显
     */
    public static final int Animation_ALPHAIN = 0x00000001;
    /**
     * 缩放
     */
    public static final int Animation_SCALEIN = 0x00000002;
    /**
     * 从下到上
     */
    public static final int Animation_SLIDEIN_BOTTOM = 0x00000003;
    /**
     * 从左到右
     */
    public static final int Animation_SLIDEIN_LEFT = 0x00000004;
    /**
     * 从右到左
     */
    public static final int Animation_SLIDEIN_RIGHT = 0x00000005;


    protected LayoutManagers() {
    }

    public interface LayoutManagerFactory {
        RecyclerView.LayoutManager create(RecyclerView recyclerView, Context context);
    }

    /**
     * A {@link LinearLayoutManager}.
     */
    public static LayoutManagerFactory linear() {
        return new LayoutManagerFactory() {
            @Override
            public RecyclerView.LayoutManager create(RecyclerView recyclerView, Context context) {
                if (context == null) {
                    return new LinearLayoutManager(recyclerView.getContext());
                } else {
                    return new LinearLayoutManager(context);
                }
            }
        };
    }

    /**
     * A {@link LinearLayoutManager} with the given orientation and reverseLayout.
     */
    public static LayoutManagerFactory linear(@Orientation final int orientation, final boolean reverseLayout) {
        return new LayoutManagerFactory() {
            @Override
            public RecyclerView.LayoutManager create(RecyclerView recyclerView, Context context) {
                if (context == null) {
                    return new LinearLayoutManager(recyclerView.getContext(), orientation, reverseLayout);
                } else {
                    return new LinearLayoutManager(context, orientation, reverseLayout);
                }
            }
        };
    }

    /**
     * A {@link GridLayoutManager} with the given spanCount.
     */
    public static LayoutManagerFactory grid(final int spanCount) {
        return new LayoutManagerFactory() {
            @Override
            public RecyclerView.LayoutManager create(RecyclerView recyclerView, Context context) {
                if (context == null) {
                    return new GridLayoutManager(recyclerView.getContext(), spanCount);
                } else {
                    return new GridLayoutManager(context, spanCount);
                }

            }
        };
    }

    /**
     * A {@link GridLayoutManager} with the given spanCount, orientation and reverseLayout.
     **/
    public static LayoutManagerFactory grid(final int spanCount, @Orientation final int orientation, final boolean reverseLayout) {
        return new LayoutManagerFactory() {
            @Override
            public RecyclerView.LayoutManager create(RecyclerView recyclerView, Context context) {
                if (context == null) {
                    return new GridLayoutManager(recyclerView.getContext(), spanCount, orientation, reverseLayout);
                } else {
                    return new GridLayoutManager(context, spanCount, orientation, reverseLayout);
                }

            }
        };
    }

    /**
     * A {@link StaggeredGridLayoutManager} with the given spanCount and orientation.
     */
    public static LayoutManagerFactory staggeredGrid(final int spanCount, @Orientation final int orientation) {
        return new LayoutManagerFactory() {
            @Override
            public RecyclerView.LayoutManager create(RecyclerView recyclerView, Context context) {
                return new StaggeredGridLayoutManager(spanCount, orientation);
            }
        };
    }

    @IntDef({LinearLayoutManager.HORIZONTAL, LinearLayoutManager.VERTICAL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Orientation {
    }
}
