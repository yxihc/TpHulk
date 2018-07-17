package com.taopao.baseapp.base;

import android.databinding.BaseObservable;
import android.databinding.Observable;

import com.afollestad.materialdialogs.MaterialDialog;
import com.taopao.mvvmbase.base.BaseMVVMActivity;
import com.taopao.mvvmbase.base.BaseMVVMFragment;
import com.taopao.mvvmbase.base.BaseMVVMViewModel;
import com.taopao.mvvmbase.utils.MaterialDialogUtils;

/**
 * @Author： 淘跑
 * @Date: 2018/7/17 11:04
 * @Use：
 */

public class BaseViewModel extends BaseMVVMViewModel {
    private MaterialDialog mMaterialDialog;

    public BaseViewModel(BaseMVVMActivity activity) {
        super(activity);
    }

    public BaseViewModel(BaseMVVMFragment fragment) {
        super(fragment);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        hideDialog.addOnPropertyChangedCallback(new OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                HideDialog();
                finishRefresh.set(!finishRefresh.get());
            }
        });

    }

    /**
     * 显示dialog
     *
     * @param msg
     */
    public void showDialog(String msg) {
        if (mMaterialDialog != null) {
            mMaterialDialog.show();
        } else {
            MaterialDialog.Builder builder = MaterialDialogUtils.showIndeterminateProgressDialog(mContext, msg, true);
            mMaterialDialog = builder.show();
        }
    }

    /**
     * 隐藏dialog
     */
    public void HideDialog() {
        if (mMaterialDialog != null && mMaterialDialog.isShowing()) {
            mMaterialDialog.dismiss();
        }
    }

}
