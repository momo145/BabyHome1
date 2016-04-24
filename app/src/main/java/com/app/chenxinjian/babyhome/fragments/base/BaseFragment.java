package com.app.chenxinjian.babyhome.fragments.base;

import android.app.Fragment;
import android.os.Bundle;
import android.widget.Toast;

import com.app.chenxinjian.babyhome.app.App;
import com.app.chenxinjian.babyhome.dialog.DialogInfo;
import com.app.chenxinjian.babyhome.dialog.base.BaseDialog;
import com.app.chenxinjian.babyhome.event.MessageEvent;
import com.app.chenxinjian.babyhome.utils.ResManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.xutils.common.util.LogUtil;

/**
 * Created by 陈欣健 on 16/4/24.
 * Email:416941523@qq.com
 */
public abstract class BaseFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

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
        Toast.makeText(App.getInstance(), ResManager.getString(resId), Toast.LENGTH_SHORT).show();
    }


    protected void showShortToast(String text) {
        Toast.makeText(App.getInstance(), text, Toast.LENGTH_SHORT).show();
    }


    protected void showLongToast(int resId) {
        Toast.makeText(App.getInstance(), ResManager.getString(resId), Toast.LENGTH_LONG).show();
    }


    protected void showLongToast(String text) {
        Toast.makeText(App.getInstance(), text, Toast.LENGTH_LONG).show();
    }

    protected void showCommonDialog(DialogInfo dialogInfo) {

    }

    BaseDialog CommonDialog = null;

    protected void dismissCommonDialog() {
        if (CommonDialog != null && CommonDialog.isShowing()) {
            CommonDialog.dismiss();
        }
    }
}
