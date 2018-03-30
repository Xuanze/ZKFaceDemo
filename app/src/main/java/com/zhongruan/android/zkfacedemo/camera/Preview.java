package com.zhongruan.android.zkfacedemo.camera;

import android.hardware.Camera;
import android.os.Handler;
import android.os.Message;

import com.zhongruan.android.zkfacedemo.config.ABLConfig;

/**
 * Created by LHJ on 2018/3/22.
 */

public class Preview implements Camera.PreviewCallback {

    private Handler mHander;

    public Preview(Handler mHander) {
        this.mHander = mHander;
    }

    @Override
    public void onPreviewFrame(byte[] bytes, Camera camera) {
        if (bytes != null) {
            Message m = mHander.obtainMessage();
            m.what = ABLConfig.UPDATE_FACE_RECT;
            m.obj = bytes;
            m.sendToTarget();
        }
    }
}
