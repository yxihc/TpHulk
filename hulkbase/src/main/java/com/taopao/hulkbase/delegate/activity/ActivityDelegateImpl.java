/**
 * Copyright (C), 2017-2020
 * Author: taopao
 * Date: 2020/5/22 下午4:14
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
package com.taopao.hulkbase.delegate.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 *
 * @Copyright (C), 2017-2020
 * @Author: TaoPao
 * @Date: 2020/5/22 下午4:14
 * @Description: java类作用描述
 */
public class ActivityDelegateImpl  implements ActivityDelegate {
    private Activity mActivity;
    public ActivityDelegateImpl(@NonNull Activity activity) {
        this.mActivity = activity;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.e("====", "onCreate: " );
    }
    @Override
    public void onStart() {
    }
    @Override
    public void onResume() {
    }
    @Override
    public void onPause() {
    }
    @Override
    public void onStop() {
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
    }
    @Override
    public void onDestroy() {
    }
}