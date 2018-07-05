package com.taopao.mvvmbase.binding.viewadapter.spinner;

/**
 * @Author： 淘跑
 * @Date: 2018/7/5 11:43
 * @Use： 下拉Spinner控件的键值对, 实现该接口, 返回key, value值, 在xml绑定List<IKeyAndValue>
 */
public interface IKeyAndValue {
    String getKey();

    String getValue();
}
