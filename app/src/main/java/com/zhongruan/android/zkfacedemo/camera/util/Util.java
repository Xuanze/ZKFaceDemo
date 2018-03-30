package com.zhongruan.android.zkfacedemo.camera.util;

import android.graphics.Canvas;
import android.graphics.Matrix;

public class Util {
    public static void prepareMatrix(Matrix matrix, boolean mirror, int displayOrientation, int viewWidth, int viewHeight) {
        matrix.setScale(mirror ? -1 : 1, 1);
        matrix.postRotate(displayOrientation);
        matrix.postScale(viewWidth / 2000f, viewHeight / 2000f);
        matrix.postTranslate(viewWidth / 2f, viewHeight / 2f);
    }

    public static void prepareCanvas(Canvas canvas, boolean mirror, int displayOrientation, int viewWidth, int viewHeight) {
        canvas.scale(mirror ? -1 : 1, 1);
        canvas.rotate(displayOrientation);
        canvas.scale(viewWidth / 2000f, viewHeight / 2000f);
        canvas.translate(viewWidth / 2f, viewHeight / 2f);
    }
}
