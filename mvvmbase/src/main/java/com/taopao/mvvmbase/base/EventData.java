package com.taopao.mvvmbase.base;

/**
 * @Author： 淘跑
 * @Date: 2018/7/16 16:56
 * @Use：
 */
public class EventData {
    public EventData() {

    }

    public EventData(int errorCode, String errorMessage, int state) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.state = state;
    }

    public int errorCode = 0;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String errorMessage = "";

    /**
     * 0是显示普通的服务器提示错误
     * 1是显示登录的错误
     */
    public int state = 0;


}
