package com.zhongruan.android.zkfacedemo.zip.archiver;

/**
 * Desc:解压进度接口
 */
public interface IArchiverListener {
    void onStartArchiver();
    void onProgressArchiver(int current, int total);
    void onEndArchiver();
}
