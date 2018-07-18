package com.taopao.mvvmbase.factory;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;

/**
 * @Author： 淘跑
 * @Date: 2018/7/18 09:42
 * @Use： Fragment工厂
 */

public class FragmentFactory {
    public FragmentFactory() {
        //不可以实例化
        //UnsupportedOperationException: 不支持的操作异常
        throw new UnsupportedOperationException("FragmentFactory: you can't instantiate me...");
    }

    public static Fragment createFragmentByTag(Context context, String tag, Bundle args) {
        String fragmentName = context.getPackageName() + ".fragment" + tag + "Fragment";
        return Fragment.instantiate(context, fragmentName, args);
    }


}
