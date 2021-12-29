package com.cnblogs.sdk.internal;

import android.util.Log;

/**
 * 日志记录
 * @author RAE
 * @date 2021/02/15
 */
public final class CnblogsLogger {

    public static boolean DEBUG = true;

    public static final String TAG = "CnblogsLogger";

    public static void e(String msg, Throwable e) {
        Log.e(TAG, msg, e);
    }

    public static void e(String msg) {
        Log.e(TAG, msg);
    }

    public static void w(String msg) {
        Log.w(TAG, msg);
    }

    public static void w(String msg, Throwable e) {
        Log.w(TAG, msg, e);
    }

    public static void d(String msg) {
        if (DEBUG) {
            Log.d(TAG, msg);
        }
    }

    public static void i(String msg) {
        if (DEBUG) {
            Log.i(TAG, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (DEBUG) {
            Log.d(TAG.concat(".").concat(tag), msg);
        }
    }
}
