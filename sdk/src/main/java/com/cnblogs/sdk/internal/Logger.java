package com.cnblogs.sdk.internal;

import android.util.Log;

import androidx.annotation.Nullable;

import com.cnblogs.sdk.BuildConfig;

/**
 * 日志记录
 * @author RAE
 * @date 2021/12/29
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public final class Logger {

    final static boolean DEBUG = BuildConfig.DEBUG;

    final static String TAG = "Cnblogs.Logger";

    public static void e(String msg) {
        e(null, msg, null);
    }

    public static void e(String msg, Throwable e) {
        e(null, msg, e);
    }

    public static void e(String tag, String msg) {
        e(tag, msg, null);
    }

    public static void e(String tag, String msg, Throwable e) {
        Log.e(makeTag(tag), msg, e);
    }

    public static void w(String msg) {
        w(msg, null);
    }

    public static void w(String msg, Throwable e) {
        w(null, msg, e);
    }

    public static void w(String tag, String msg, Throwable e) {
        Log.w(makeTag(tag), msg, e);
    }

    public static void i(String msg) {
        i(null, msg);
    }

    public static void i(String tag, String msg) {
        if (DEBUG) {
            Log.i(makeTag(tag), msg);
        }
    }

    public static void d(String msg) {
        d(null, msg);
    }

    public static void d(String tag, String msg) {
        if (DEBUG) {
            Log.d(makeTag(tag), msg);
        }
    }

    static String makeTag(@Nullable String tag) {
        return tag == null ? TAG : TAG.concat(".").concat(tag);
    }
}
