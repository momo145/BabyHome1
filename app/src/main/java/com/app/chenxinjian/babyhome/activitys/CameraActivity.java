/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Zillow
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is furnished
 * to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.app.chenxinjian.babyhome.activitys;

import android.content.Intent;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.Toast;


import com.app.chenxinjian.babyhome.R;
import com.app.chenxinjian.babyhome.activitys.base.BaseActivity;
import com.app.chenxinjian.babyhome.event.MessageEvent;
import com.app.chenxinjian.babyhome.fragments.CameraFragment;
import com.app.chenxinjian.babyhome.interfaces.CameraParamsChangedListener;
import com.app.chenxinjian.babyhome.interfaces.KeyEventsListener;
import com.app.chenxinjian.babyhome.interfaces.PhotoSavedListener;
import com.app.chenxinjian.babyhome.interfaces.PhotoTakenCallback;
import com.app.chenxinjian.babyhome.interfaces.RawPhotoTakenCallback;
import com.app.chenxinjian.babyhome.utils.SavingPhotoTask;
import com.app.chenxinjian.babyhome.utils.SharedPrefManager;

import org.xutils.common.util.LogUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CameraActivity extends BaseActivity implements PhotoTakenCallback, PhotoSavedListener, RawPhotoTakenCallback,
        CameraParamsChangedListener {

    public static final String PATH = "path";
    public static final String USE_FRONT_CAMERA = "use_front_camera";
    public static final String OPEN_PHOTO_PREVIEW = "open_photo_preview";
    public static final String LAYOUT_ID = "layout_id";

    private static final String IMG_PREFIX = "IMG_";
    private static final String IMG_POSTFIX = ".jpg";
    private static final String TIME_FORMAT = "yyyyMMdd_HHmmss";

    private KeyEventsListener keyEventsListener;
    private PhotoSavedListener photoSavedListener;

    private String path;
    private boolean openPreview;

    private boolean saving;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideActionBar();
        setContentView(R.layout.activity_take_photos);
        if (TextUtils.isEmpty(path = getIntent().getStringExtra(PATH))) {
            path = Environment.getExternalStorageDirectory().getPath();
        }
        openPreview = getIntent().getBooleanExtra(OPEN_PHOTO_PREVIEW, SharedPrefManager.i.isOpenPhotoPreview());
        if (openPreview != SharedPrefManager.i.isOpenPhotoPreview()) {
            SharedPrefManager.i.setOpenPhotoPreview(openPreview);
        }
        boolean useFrontCamera = getIntent().getBooleanExtra(USE_FRONT_CAMERA, SharedPrefManager.i.useFrontCamera());
        if (useFrontCamera != SharedPrefManager.i.useFrontCamera()) {
            SharedPrefManager.i.setUseFrontCamera(useFrontCamera);
        }
        init();
    }

    @Override
    protected void init() {
        CameraFragment fragment;
        int layoutId = getIntent().getIntExtra(LAYOUT_ID, -1);
        if (layoutId > 0) {
            fragment = CameraFragment.newInstance(layoutId, this, createCameraParams());
        } else {
            fragment = CameraFragment.newInstance(this, createCameraParams());
        }
        fragment.setParamsChangedListener(this);
        keyEventsListener = fragment;
        photoSavedListener = fragment;
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_content, fragment)
                .commit();
    }

    @Override
    protected void registerListener() {

    }

    @Override
    protected void unRegisterListener() {

    }

    @Override
    public void onEventMainThread(MessageEvent event) {
        super.onEventMainThread(event);
    }

    private Bundle createCameraParams() {
        Bundle bundle = new Bundle();

        bundle.putInt(CameraFragment.RATIO, SharedPrefManager.i.getCameraRatio());
        bundle.putInt(CameraFragment.FLASH_MODE, SharedPrefManager.i.getCameraFlashMode());
        bundle.putInt(CameraFragment.HDR_MODE, SharedPrefManager.i.isHDR());
        bundle.putInt(CameraFragment.QUALITY, SharedPrefManager.i.getCameraQuality());
        bundle.putInt(CameraFragment.FOCUS_MODE, SharedPrefManager.i.getCameraFocusMode());
        bundle.putBoolean(CameraFragment.FRONT_CAMERA, SharedPrefManager.i.useFrontCamera());

        return bundle;
    }

    private String createName() {
        String timeStamp = new SimpleDateFormat(TIME_FORMAT).format(new Date());
        return IMG_PREFIX + timeStamp + IMG_POSTFIX;
    }

    @Override
    public void photoTaken(byte[] data, int orientation) {
        savePhoto(data, createName(), path, orientation);
    }

    @Override
    public void rawPhotoTaken(byte[] data) {
        LogDebug(String.format("rawPhotoTaken: data[%1d]", data.length));
    }

    private void savePhoto(byte[] data, String name, String path, int orientation) {
        saving = true;
        new SavingPhotoTask(data, name, path, orientation, this).execute();
    }

    @Override
    public void photoSaved(String path, String name) {
        saving = false;
        Toast.makeText(this, "Photo " + name + " saved", Toast.LENGTH_SHORT).show();
        LogUtil.d("Photo " + name + " saved");
        if (true) {
            printExifOrientation(path);
        }
        if (openPreview) {
            openPreview(path, name);
        }
        if (photoSavedListener != null) {
            photoSavedListener.photoSaved(path, name);
        }
    }

    private void openPreview(String path, String name) {
        /*Intent intent = new Intent(this, PhotoPreviewActivity.class);
        intent.putExtra(BasePhotoActivity.EXTRAS.PATH, path);
        intent.putExtra(BasePhotoActivity.EXTRAS.NAME, name);
        intent.putExtra(BasePhotoActivity.EXTRAS.FROM_CAMERA, true);
        startActivityForResult(intent, BasePhotoActivity.EXTRAS.REQUEST_PHOTO_EDIT);*/
        showLongToast("打开预览");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       /* if (requestCode == BasePhotoActivity.EXTRAS.REQUEST_PHOTO_EDIT) {
            switch (resultCode) {
                case BasePhotoActivity.EXTRAS.RESULT_DELETED:
                    String path = data.getStringExtra(BasePhotoActivity.EXTRAS.PATH);
                    PhotoUtil.deletePhoto(path);
                    break;
            }
        }*/
    }

    private void printExifOrientation(String path) {
        try {
            ExifInterface exif = new ExifInterface(path);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            LogUtil.d("Orientation: " + orientation);
        } catch (IOException e) {
            LogUtil.e(e.getMessage());
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                keyEventsListener.zoomIn();
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                keyEventsListener.zoomOut();
                return true;
            case KeyEvent.KEYCODE_BACK:
                onBackPressed();
                return true;
            case KeyEvent.KEYCODE_CAMERA:
                keyEventsListener.takePhoto();
                return true;
        }
        return false;
    }

    @Override
    public void onQualityChanged(int id) {
        SharedPrefManager.i.setCameraQuality(id);
    }

    @Override
    public void onRatioChanged(int id) {
        SharedPrefManager.i.setCameraRatio(id);
    }

    @Override
    public void onFlashModeChanged(int id) {
        SharedPrefManager.i.setCameraFlashMode(id);
    }

    @Override
    public void onHDRChanged(int id) {
        SharedPrefManager.i.setHDRMode(id);
    }

    @Override
    public void onFocusModeChanged(int id) {
        SharedPrefManager.i.setCameraFocusMode(id);
    }

    @Override
    public void onBackPressed() {
        if (!saving) {
            super.onBackPressed();
        }
    }

}
