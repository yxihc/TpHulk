package com.taopao.mvvmbase;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.taopao.mvvmbase.base.BaseViewModel;
import com.taopao.mvvmbase.binding.command.BindingAction;
import com.taopao.mvvmbase.binding.command.BindingCommand;

/**
 * @Author： 淘跑
 * @Date: 2018/7/9 15:22
 * @Use：
 */

public class LoginViewModel extends BaseViewModel {
    public LoginViewModel(Context context) {
        super(context);
    }

    public BindingCommand netWorkClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Toast.makeText(mContext, "666", Toast.LENGTH_SHORT).show();
            Log.d("LoginViewModel", "call: " + "111111111");
            throw new NullPointerException("不可以为null");
        }
    });

}
