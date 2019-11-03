package com.cnblogs.api.parser;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.HttpUrl;

/**
 * 解析器工具
 * 常用的一些方法
 * Created by rae on 2019-10-22.
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
@SuppressWarnings("WeakerAccess")
public final class ParseUtils {

    /**
     * 从字符串中提取为数字的文字
     *
     * @param text     原文本
     * @param defValue 如果没有提取到返回默认值
     */
    public static String getNumber(String text, String defValue) {
        if (TextUtils.isEmpty(text)) return defValue;
        Matcher matcher = Pattern.compile("\\d+").matcher(text);
        while (matcher.find()) {
            defValue = matcher.group();
        }
        return defValue;
    }

    public static String getNumber(String text) {
        return getNumber(text, text);
    }

    public static int parseInt(String text) {
        try {
            return Integer.parseInt(text);
        } catch (Exception ignored) {
        }
        return 0;
    }

    /**
     * 移除括号内文字
     */
    public static String removeBrackets(String text) {
        return Pattern.compile("\\(\\d+\\)").matcher(text).replaceAll("");
    }

    public static String getCount(String text) {
        if (TextUtils.isEmpty(text)) return "0";
        return getNumber(text.trim());
    }

    public static String getBlogApp(String authorUrl) {
        if (TextUtils.isEmpty(authorUrl)) return null;
        try {
            HttpUrl httpUrl = HttpUrl.parse(authorUrl);
            if (httpUrl != null) {
                List<String> pathSegments = httpUrl.pathSegments();
                String path = null;
                for (String pathSegment : pathSegments) {
                    if (!TextUtils.isEmpty(pathSegment)) {
                        path = pathSegment;
                    }
                }
                if (!TextUtils.isEmpty(path)) {
                    return path;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (authorUrl.contains("home.cnblogs.com")) {
            try {
                authorUrl = authorUrl.replace("//", "http://");
                Uri uri = Uri.parse(authorUrl);
                return uri.getLastPathSegment();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return authorUrl
                .replace("https", "")
                .replace("http", "")
                .replace("://www.cnblogs.com/", "")
                .replace("/u/", "")
                .replace("/", "");
    }


    public static String getDate(String text) {
        if (TextUtils.isEmpty(text)) {
            return text;
        }

        String pattern = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}";
        Matcher matcher = Pattern.compile(pattern).matcher(text);
        if (!matcher.find()) {
            return text;
        }

        text = matcher.group();

        // UTC 时间
        if (text.contains("T") || text.contains("+") || text.contains("Z")) {
            text = toLocalDate(text);
        }

        return text;
    }

    /**
     * 转为友好的日期
     *
     * @param text 日期
     */
    public static String getNiceDate(String text) {

        String time = text.split(" ")[1];

        // 时间间隔
        long span = (System.currentTimeMillis() - parseDate(text).getTime()) / 1000;

        // 今天过去的时间
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        long today = (System.currentTimeMillis() - calendar.getTimeInMillis()) / 1000;
        if (span < 0) {
            return text;
        } else if (span < 60) {
            text = "刚刚";
        } else if (span < 3600) {
            text = (span / 60) + "分钟前";
        } else if (span < today) {
            text = "今天 " + time;
        } else if (span < today + 86400) {
            text = "昨天 " + time;
        } else if (span < today + 2 * 86400) {
            text = "前天 " + time;
        }


        return text;
    }


    /**
     * UTC时间转本地时间
     *
     * @param text UTC时间
     */
    @SuppressLint("SimpleDateFormat")
    private static String toLocalDate(String text) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Date utcDate = sdf.parse(text);
            sdf.setTimeZone(TimeZone.getDefault());
            sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
            return sdf.format(utcDate);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return text;
    }


    public static Date parseDate(String text) {
        return parseDate(text, "yyyy-MM-dd HH:mm");
    }

    public static Date parseDate(String text, String pattern) {
        if (TextUtils.isEmpty(text)) {
            return new Date();
        }
        Date target;
        try {
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            target = format.parse(text);// RaeDateUtil.parseCommentInList(text, "yyyy-MM-dd HH:mm");
        } catch (Exception e) {
            Log.e("rae", "解析出错!", e);
            target = new Date();
        }
        return target;
    }

    public static String formatDate(String text, String pattern) {
        try {
            Date date = parseDate(text);
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            return format.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }
}
