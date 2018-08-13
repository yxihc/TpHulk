package com.taopao.mvvmbase.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.orhanobut.logger.Logger;

import java.util.Locale;

/**
 * 语言工具类
 */
public class LanguageUtil {
    private static LanguageUtil instance;
    public static final String spName = "LanguageSetting";//sp文件名
    private static final String LANGUAGE = "Language";//语言名key
    private static final String LANGUAGETYPE = "LanguageType";//语言type  key
    public final static int FOLLOW_SYSTEM = 0;//系统默认语言
    public final static int SIMPLIFIED_CHINESE = 1;//中文
    public final static int ENGLISH = 2;//英文

    private BroadcastReceiver receiver = new LocaleChangeReceiver();

    private LanguageUtil() {
    }

    public static LanguageUtil getInstance() {
        //单利模式获取LanguageUtil
        if (instance == null) {
            synchronized (LanguageUtil.class) {
                instance = new LanguageUtil();
            }
        }
        return instance;
    }


    /**
     * 中英文切换
     *
     * @param context context必须是activity的context
     */
    public void changeEnglishOrChineseLanguage(Context context) {
        String appLanguage = getAppLanguage(context);

        Logger.d("======================" + appLanguage);
        if (appLanguage.equals("en")) {
            //如果是英文的话就切换中文
            changeAppLanguage(context, SIMPLIFIED_CHINESE);
        } else if (appLanguage.equals("zh")) {
            //如果是中文就切换英文
            changeAppLanguage(context, ENGLISH);
        } else {
            //如果既不是中文也不是英文就切换中文
            changeAppLanguage(context, SIMPLIFIED_CHINESE);
        }
    }

    /**
     * 修改语言设置
     */
    public void changeAppLanguage(Context context, int type) {
        Resources resources = context.getResources();
        Configuration config = resources.getConfiguration();
        DisplayMetrics dm = resources.getDisplayMetrics();
        switch (type) {
            case FOLLOW_SYSTEM:
                config.locale = Locale.getDefault();
                break;
            case ENGLISH:
                config.locale = Locale.US;
                break;
            case SIMPLIFIED_CHINESE:
                config.locale = Locale.SIMPLIFIED_CHINESE;
                break;
        }

        //注册广播 暂时不需要这么做
//        context.registerReceiver(receiver, new IntentFilter(Intent.ACTION_LOCALE_CHANGED));
        saveLanguageSetting(context, type, config.locale.getLanguage());
        resources.updateConfiguration(config, dm);
    }

    /**
     * 判断是否与设定的语言相同.
     *
     * @param context
     * @return
     */
    public boolean isSameWithSetting(Context context) {
        return true;
    }

    /**
     * 设置当前的语言的自定义编号
     */
    private void saveLanguageSetting(Context context, int type, String name) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE);

        sharedPreferences.edit()
                .putInt(LANGUAGETYPE, type)
                .commit();

        sharedPreferences.edit()
                .putString(LANGUAGE, name)
                .commit();

    }

    /**
     * 得到当前的语言的自定义编号
     */
    public int getAppLanguageSetting(Context context) {
        return context
                .getSharedPreferences(spName, Context.MODE_PRIVATE)
                .getInt(LANGUAGETYPE, FOLLOW_SYSTEM);
    }

    /**
     * 得到当前的语言名称
     */
    public String getAppLanguage(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(spName,
                Context.MODE_PRIVATE);
        return preferences.getString(LANGUAGE,
                Locale.getDefault().getLanguage());
    }

    class LocaleChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //改变系统语言会接收到 ACTION_LOCALE_CHANGED
            //改变应用内部语言不会收到 ACTION_LOCALE_CHANGED
            if (!Intent.ACTION_LOCALE_CHANGED.equals(intent.getAction())) {
                int languageType = context.getSharedPreferences(spName, Context.MODE_PRIVATE).getInt(LANGUAGETYPE, FOLLOW_SYSTEM);
//                languageType=FOLLOW_SYSTEM 表示用户重来没有设置过语言 这时候我们就要根据系统的变化来显示语言
                if (languageType == FOLLOW_SYSTEM) {
                    changeAppLanguage(context, languageType);
                }
            }
        }
    }
}
