package com.taopao.hulkbase.log;

import android.util.Log;

public class HLog {
    private static boolean isShowLog=true;
    private HLog() {
        throw new IllegalStateException("you can't instantiate me!");
    }
    public static int v(String tag, String msg) {
        if (!isShowLog){
            return -1;
        }
        return Log.v(tag,msg);
    }

    public static int v(String tag, String msg, Throwable tr) {
        if (!isShowLog){
            return -1;
        }
        return Log.v(tag,msg,tr);
    }

    public static int d(String tag, String msg) {
        if (!isShowLog){
            return -1;
        }
        return Log.d(tag,msg);
    }

    public static int d(String tag, String msg, Throwable tr) {
        if (!isShowLog){
            return -1;
        }
        return Log.d(tag,msg,tr);
    }

    public static int i(String tag, String msg) {
        if (!isShowLog){
            return -1;
        }
        return Log.i(tag,msg);
    }

    public static int i(String tag, String msg, Throwable tr) {
        if (!isShowLog){
            return -1;
        }
        return Log.i(tag,msg,tr);
    }

    public static int w(String tag, String msg) {
        if (!isShowLog){
            return -1;
        }
        return Log.w(tag,msg);
    }

    public static int w(String tag, String msg, Throwable tr) {
        if (!isShowLog){
            return -1;
        }
        return Log.w(tag,msg,tr);
    }

    public static int w(String tag, Throwable tr) {
        if (!isShowLog){
            return -1;
        }
        return Log.w(tag,tr);
    }

    public static int e(String tag, String msg) {
        if (!isShowLog){
            return -1;
        }
        return Log.e(tag,msg);
    }

    public static int e(String tag, String msg, Throwable tr) {
        if (!isShowLog){
            return -1;
        }
        return Log.e(tag,msg,tr);
    }


}
