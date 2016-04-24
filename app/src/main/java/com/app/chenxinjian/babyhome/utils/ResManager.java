package com.app.chenxinjian.babyhome.utils;

import android.support.annotation.NonNull;

import com.app.chenxinjian.babyhome.app.App;

/**
 * Created by 陈欣健 on 16/4/23.
 * Email:416941523@qq.com
 */
public class ResManager {

    public static String getString(@NonNull int id) {
        return App.getInstance().getString(id);
    }


}

