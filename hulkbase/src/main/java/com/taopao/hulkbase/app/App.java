package com.taopao.hulkbase.app;

import com.taopao.hulkbase.ConfigModule;

import java.util.ArrayList;
import java.util.List;

/**
 * @Copyright (C), 2017-2020
 * @Author: TaoPao
 * @Date: 2020/5/22 下午2:55
 * @Description: 作用描述
 */
public interface App {
    default List<ConfigModule> getConfigModules(){
        return new ArrayList<>();
    }
}
