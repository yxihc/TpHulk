package com.taopao.hulkmvp.mvp;

/**
 * @Author：淘跑
 * @Date: 2019/4/17 15:33
 * @Use： 框架要求框架中的每个 Model 都需要实现此类,以满足规范
 * @see BaseModel
 */
public interface IModel {

    /**
     * 在框架中 {@link BasePresenter#onDestroy()} 时会默认调用 {@link IModel#onDestroy()}
     */
    void onDestroy();
}
