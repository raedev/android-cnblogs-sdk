package com.cnblogs.api;

import android.support.annotation.NonNull;
import android.util.Log;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Cnblogs Android Logcat
 * Created by rae on 2019-10-21.
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public final class CLog {

    private final static String TAG = "Cnblogs";

    final static HttpLoggingInterceptor.Logger HTTP_LOGGER = new HttpLoggingInterceptor.Logger() {
        @Override
        public void log(@NonNull String msg) {
            Log.i("CNBLOGS_OPEN_API", msg);
        }
    };

    public static void e(String msg, Throwable e) {
        Log.e(TAG, msg, e);
    }

    public static void e(String msg) {
        Log.e(TAG, msg);
    }

    public static void w(String msg) {
        Log.w(TAG, msg);
    }

    public static void d(String msg) {
        Log.d(TAG, msg);
    }

    public static void i(String msg) {
        Log.i(TAG, msg);
    }
}
