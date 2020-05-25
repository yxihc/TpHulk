package com.taopao.hulkmvp.mvp;

import androidx.lifecycle.LifecycleObserver;

import com.taopao.hulkbase.utils.Preconditions;
import com.taopao.hulkmvp.mvp.IModel;
import com.taopao.hulkmvp.mvp.IPresenter;
import com.taopao.hulkmvp.mvp.IView;

public abstract class BasePresenter<M extends IModel,V extends IView> implements IPresenter, LifecycleObserver {
    private V mRootView=null;
    private M mModel=null;
    public BasePresenter(V rootView) {
        Preconditions.checkNotNull(rootView, "%s cannot be null", IView.class.getName());
        this.mRootView = rootView;
        if (mModel == null) {
            mModel = obtainModel();
        }
        onStart();
    }
    public abstract M obtainModel();
    @Override
    public void onStart() {

    }
    @Override
    public void onDestroy() {
        if (mModel != null)
            mModel.onDestroy();
        this.mModel = null;
        this.mRootView = null;
    }
}
