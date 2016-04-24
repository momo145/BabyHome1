package com.app.chenxinjian.babyhome.app;

import android.app.Application;
import android.content.Context;

import com.app.chenxinjian.babyhome.utils.SharedPrefManager;

import org.xutils.BuildConfig;
import org.xutils.x;

/**
 * Created by 陈欣健 on 16/4/23.
 * Email:416941523@qq.com
 */
public class App extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        x.Ext.init(this);
        x.Ext.setDebug(true); // 是否输出debug日志, 开启debug会影响性能.
        SharedPrefManager.i.init(context);
    }

    public static Context getInstance() {
        return context;
    }
}
