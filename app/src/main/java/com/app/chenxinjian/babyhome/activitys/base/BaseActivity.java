package com.app.chenxinjian.babyhome.activitys.base;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.app.chenxinjian.babyhome.R;
import com.app.chenxinjian.babyhome.dialog.DialogInfo;
import com.app.chenxinjian.babyhome.dialog.base.BaseDialog;
import com.app.chenxinjian.babyhome.event.MessageEvent;
import com.app.chenxinjian.babyhome.utils.ResManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.Event;

/**
 * Created by 陈欣健 on 16/4/23.
 */
public abstract class BaseActivity extends AppCompatActivity {

    //初始化
    protected abstract void init();

    //注册事
    protected abstract void registerListener();

    //反注册事件
    protected abstract void unRegisterListener();

    //EventBus 处理事件
    @Subscribe
    public void onEventMainThread(MessageEvent event) {

    }


    private void registerEventBus() {
        LogDebug("registerEventBus");
        EventBus.getDefault().register(this);
    }

    private void unRegisterEventBus() {
        LogDebug("unRegisterEventBus");
        EventBus.getDefault().unregister(this);
    }


    protected void LogDebug(String msg) {
        LogUtil.d(msg);
    }


    protected void LogError(String msg) {
        LogUtil.e(msg);
    }


    protected void showShortToast(int resId) {
        Toast.makeText(this, ResManager.getString(resId), Toast.LENGTH_SHORT).show();
    }


    protected void showShortToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }


    protected void showLongToast(int resId) {
        Toast.makeText(this, ResManager.getString(resId), Toast.LENGTH_LONG).show();
    }


    protected void showLongToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    protected void showCommonDialog(DialogInfo dialogInfo) {

    }

    BaseDialog CommonDialog = null;

    protected void dismissCommonDialog() {
        if (CommonDialog != null && CommonDialog.isShowing()) {
            CommonDialog.dismiss();
        }
    }

    protected void hideActionBar() {
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            getActionBar().hide();
        }

    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerEventBus();
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterEventBus();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unRegisterListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerListener();
    }


}
