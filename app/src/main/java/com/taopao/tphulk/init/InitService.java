/**
 * Copyright (C), 2017-2020
 * Author: taopao
 * Date: 2020/6/2 下午3:17
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
package com.taopao.tphulk.init;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

/**
 *
 * @Copyright (C), 2017-2020
 * @Author: TaoPao
 * @Date: 2020/6/2 下午3:17
 * @Description:
 * -----------------------------------------------------
 * 冷启动优化，所有初始化（并不重要的初始化，例如bugly，推送，统计）耗时的操作放在这里
 */
public class InitService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public InitService() {
        super("initService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.e("====", "耗时操作");
    }
}