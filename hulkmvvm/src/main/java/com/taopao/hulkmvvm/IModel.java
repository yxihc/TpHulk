package com.taopao.hulkmvvm;

/**
 * @Author：淘跑
 * @Date: 2019/4/17 15:33
 * @Use：
 * ------------------------------------------------------
 * 每个 Model 都要实现这个类，{@link BaseModel}实现了此类
 */
public interface IModel {

    /**
     * ViewModel销毁时清除Model，数据层不能持有命周期长的对象
     */
    void onCleared();
}
