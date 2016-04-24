package com.app.chenxinjian.babyhome.dialog.base;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.app.chenxinjian.babyhome.event.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.xutils.common.util.LogUtil;

/**
 * Created by 陈欣健 on 16/4/23.
 * Email:416941523@qq.com
 */
public abstract class BaseDialog extends Dialog {

    public BaseDialog(Context context) {
        super(context);
    }

    public BaseDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected BaseDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    protected void LogDebug(String msg) {
        LogUtil.d(msg);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onStart() {
        super.onStart();
        registerEventBus();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unRegisterEventBus();
    }

    private void registerEventBus() {
        LogDebug("dialog registerEventBus");
        EventBus.getDefault().register(this);
    }

    private void unRegisterEventBus() {
        LogDebug("dialog unRegisterEventBus");
        EventBus.getDefault().unregister(this);
    }

    //EventBus 处理事件
    @Subscribe
    public void onEventMainThread(MessageEvent event) {
    }

    ;
}
