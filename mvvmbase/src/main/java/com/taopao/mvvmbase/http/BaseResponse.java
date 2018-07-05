package com.taopao.mvvmbase.http;

/**
 * @Author： 淘跑
 * @Date: 2018/7/5 11:43
 * @Use：
 */
public class BaseResponse<T> {
    public static final int onResultOk = 200;//请求成功
    public static final int tokenInvalid = 201;//token失效
    private int errorCode;//错误码
    private String errorMsg;//错误内容

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    private T data;

    /**
     * 是否请求成功
     *
     * @return
     */
    public boolean isOk() {
        if (errorCode == onResultOk) {
            return true;
        } else {
            return false;
        }
    }
}
