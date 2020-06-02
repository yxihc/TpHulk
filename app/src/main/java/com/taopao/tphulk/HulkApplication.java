/**
 * Copyright (C), 2017-2020
 * Author: taopao
 * Date: 2020/5/22 下午3:20
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
package com.taopao.tphulk;

import com.taopao.hulkbase.config.ConfigModule;
import com.taopao.hulkbase.delegate.app.BaseApplication;
import com.taopao.tphulk.init.MyConfigModule;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @Copyright (C), 2017-2020
 * @Author: TaoPao
 * @Date: 2020/5/22 下午3:20
 * @Description: ⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️
 * --------------------------------------------------------
 *
 */
public class HulkApplication extends BaseApplication {
    @Override
    public List<ConfigModule> getConfigModules() {
        List<ConfigModule> configModules = new ArrayList<>();
        configModules.add(new MyConfigModule());
        return configModules;
    }
}