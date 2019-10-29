package com.cnblogs.api;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Cnblogs Android Logcat
 * Created by rae on 2019-10-21.
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
@SuppressWarnings("WeakerAccess")
public final class CLog {

    private final static String TAG = "cnblogs";
    final static HttpLoggingInterceptor.Logger HTTP_LOGGER = new HttpLoggingInterceptor.Logger() {
        @Override
        public void log(@NotNull String s) {
            d(s);
        }
    };

    public static void e(String msg, Throwable e) {
        Log.e(TAG, msg, e);
    }

    public static void e(String msg) {
        Log.e(TAG, msg);
    }

    public static void d(String msg) {
        Log.d(TAG, msg);
    }
}
