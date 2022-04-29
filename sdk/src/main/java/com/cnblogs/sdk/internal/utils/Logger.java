package com.cnblogs.sdk.internal.utils;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cnblogs.sdk.BuildConfig;

import java.util.Objects;

/**
 * 日志记录
 * @author RAE
 * @date 2021/12/29
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public final class Logger {

    private static LoggerWriter sLoggerWriter = new CnblogsLoggerWriter();

    /**
     * 设置日志接口
     * @param writer 接口
     */
    public static void setLoggerWriter(LoggerWriter writer) {
        sLoggerWriter = Objects.requireNonNull(writer, "日记记录接口不能为空");
    }

    final static boolean DEBUG = BuildConfig.DEBUG;

    final static String TAG = "Cnblogs";

    static void print(int level, String tag, String msg, Throwable throwable) {
        if (tag == null) {
            tag = TAG;
        }
        int len = msg.length();
        int maxLength = 2000;
        int countOfSub = len / maxLength;
        // 先输出
        String printMessage = msg.substring(0, Math.min(len, maxLength));
        // 调用日志接口输出
        sLoggerWriter.write(level, tag, printMessage, throwable);
        if (countOfSub > 0) {
            print(level, tag, msg.substring(maxLength), throwable);
        }
    }

    /**
     * 写日志
     */
    public interface LoggerWriter {
        /**
         * 写日志方法
         * @param level 日志级别
         * @param tag TAG
         * @param message 消息
         * @param throwable 异常信息
         */
        void write(int level, @NonNull String tag, @NonNull String message, @Nullable Throwable throwable);
    }

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

    /**
     * 默认日志实现
     */
    public static class CnblogsLoggerWriter implements LoggerWriter {

        @Override
        public void write(int level, @NonNull String tag, @NonNull String message, @Nullable Throwable throwable) {
            // Android Logcat Console
            if (throwable != null) {
                Log.println(level, tag, message + "\n" + Log.getStackTraceString(throwable));
            } else {
                Log.println(level, tag, message);
            }
        }
    }
}
