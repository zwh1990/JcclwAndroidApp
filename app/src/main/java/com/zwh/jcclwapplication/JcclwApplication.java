package com.zwh.jcclwapplication;

import android.app.Application;

/**
 * @author by zwh
 * @description：
 * @date 2021/2/3 10:35
 * @邮箱：zhaowh@zgjzd.cn
 */
public class JcclwApplication extends Application {

    private static JcclwApplication context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

    }

    public static JcclwApplication getAppContext(){
        return context;
    }

}
