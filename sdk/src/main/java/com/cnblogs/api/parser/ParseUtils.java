package com.cnblogs.api.parser;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
                .replace("/detail/", "")
                .replace("/", "");
    }

    public static String getUserAlias(String text) {
        if (TextUtils.isEmpty(text)) return text;
        // showCommentBox(1243228,607820);return false;
        Matcher matcher = Pattern.compile("\\d+").matcher(text);
        while (matcher.find()) {
            text = matcher.group();
        }
        return text;
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

    @SuppressLint("SimpleDateFormat")
    public static String getDate(String text, String sourcePattern, String targetPattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(sourcePattern);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Date utcDate = sdf.parse(text);
            sdf.setTimeZone(TimeZone.getDefault());
            sdf.applyPattern(targetPattern);
            return sdf.format(utcDate);
        } catch (Exception e) {
            e.printStackTrace();
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

    /**
     * 引用评论内容
     *
     * @param comment 要引用的评论
     * @param content 你要发表的内容
     * @return 内容
     */
    public static String formatCommentContent(String authorName, String quote, String content) {
        // {"blogApp":"silenttiger","postId":6323406,"body":"@TCG2008\n[quote]网页应用都差不多，什么QQ上的应用宝，空间的应用啊，百度轻应用...主要都是为了引流，你一个小公司当然要从微信百度上引导别人使用你的产品啦。[/quote]\naa","parentCommentId":"3608347"}
        StringBuilder sb = new StringBuilder();
        sb.append("@");
        sb.append(authorName);
        sb.append("\n");
        sb.append("[quote]");
        sb.append(quote);
        sb.append("[/quote]");
        sb.append("\n");
        sb.append(content);
        return sb.toString();
    }

    public static String getUrl(String url) {
        // 解决缺少https协议头
        if (TextUtils.isEmpty(url)) return url;
        if (url.startsWith("//")) return "https:" + url; // 博客园差不多全站https了
        return url;
    }

    /**
     * h获取默认值
     *
     * @param text         原文本
     * @param defaultValue 默认值
     */
    public static String getString(String text, String defaultValue) {
        return TextUtils.isEmpty(text) ? defaultValue : text;
    }

    @NonNull
    public static List<String> findMatchers(String pattern, String text) {
        List<String> values = new ArrayList<>();
        Matcher matcher = Pattern.compile(pattern).matcher(text);
        while (matcher.find()) {
            values.add(matcher.group());
        }
        return values;
    }

    public static String getString(List<String> values, int index) {
        int size = values.size();
        if (size <= 0 || index + 1 > size) return null;
        return values.get(Math.max(0, index));
    }
}
