package com.cnblogs.sdk.internal.utils;

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

    final static String TAG = "Cnblogs";

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
        print(Log.ERROR, makeTag(tag), msg, e);
    }

    public static void w(String msg) {
        w(null, msg, null);
    }

    public static void w(String msg, Throwable e) {
        w(null, msg, e);
    }

    public static void w(String tag, String msg) {
        w(tag, msg, null);
    }

    public static void w(String tag, String msg, Throwable e) {
        print(Log.WARN, makeTag(tag), msg, e);
    }

    public static void i(String msg) {
        i(null, msg);
    }

    public static void i(String tag, String msg) {
        if (DEBUG) {
            print(Log.INFO, tag, msg);
        }
    }

    public static void d(String msg) {
        d(null, msg);
    }

    public static void d(String tag, String msg) {
        if (DEBUG) {
            print(Log.DEBUG, tag, msg);
        }
    }

    static String makeTag(@Nullable String tag) {
        return tag == null ? TAG : TAG.concat(".").concat(tag);
    }

    static void print(int level, String tag, String msg) {
        print(level, makeTag(tag), msg, null);
    }

    static void print(int level, String tag, String msg, Throwable throwable) {
        if (tag == null) {
            tag = TAG;
        }
        int len = msg.length();
        int maxLength = 2000;
        int countOfSub = len / maxLength;
        // 先输出
        String printMessage = msg.substring(0, Math.min(len, maxLength));
        if (throwable != null) {
            Log.println(level, tag, printMessage + "\n" + Log.getStackTraceString(throwable));
        } else {
            Log.println(level, tag, printMessage);
        }

        if (countOfSub > 0) {
            print(level, tag, msg.substring(maxLength), throwable);
        }
    }
}
