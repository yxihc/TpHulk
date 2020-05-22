package com.taopao.hulkbase.app;


/**
 * @Copyright (C), 2017-2020
 * @Author: TaoPao
 * @Date: 2020/5/22 下午4:13
 * @Description: 作用描述
 */
public interface IActivity {

    default boolean useFragment() {
        return true;
    }
}
