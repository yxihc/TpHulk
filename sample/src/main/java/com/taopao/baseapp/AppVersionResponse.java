package com.taopao.baseapp;


import java.io.Serializable;

/**
 * @Author： 淘跑
 * @Date: 2018/6/6 14:56
 * @Use： app版本
 */
public class AppVersionResponse implements Serializable {
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    private String info;
    private int isFlag;
    private String url;
    private String version;


    public int getIsFlag() {
        return isFlag;
    }

    public void setIsFlag(int isFlag) {
        this.isFlag = isFlag;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
