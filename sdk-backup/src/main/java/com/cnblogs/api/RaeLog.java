package com.cnblogs.api;

import android.util.Log;

/**
 * Created by rae on 2020-02-12.
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public final class RaeLog {

    public static void d(String message, Object... args) {
        Log.i("rae", String.format(message, args));
    }

    public static void e(String message, Object... args) {
        Log.e("rae", String.format(message, args));
    }

    public static void e(String message, Throwable ex) {
        Log.e("rae", message, ex);
    }

}
