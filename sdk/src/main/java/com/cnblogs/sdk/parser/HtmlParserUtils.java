package com.cnblogs.sdk.parser;

import android.text.TextUtils;

import androidx.annotation.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 解析工具
 * @author RAE
 * @date 2021/02/25
 */
@SuppressWarnings("AlibabaUndefineMagicConstant")
public final class HtmlParserUtils {

    private static final Pattern S_NUMBER_PATTERN = Pattern.compile("\\d+");
    private static final Pattern S_DATE_PATTERN = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
    private static final Pattern S_DATE_TIME_PATTERN = Pattern.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}");

    /**
     * 从路径中获取blogApp
     * @param url 路径
     * @return blogApp
     */
    public static String findBlogApp(@Nullable String url) {
        if (url == null) {
            return null;
        }

        // 处理规则：一般为链接的最后一个path
        // 比如：https://home.cnblogs.com/u/blogApp

        if (url.endsWith("/")) {
            // 去除最后一个 '/'
            url = url.substring(0, url.length() - 1);
        }
        int endIndex = url.lastIndexOf("/");
        if (endIndex > 0) {
            return url.substring(endIndex + 1);
        }
        return url;
    }

    /**
     * 字符串是否为空
     * @param text 文本
     * @return 是否为空
     */
    public static boolean isEmpty(String text) {
        if (text == null) {
            return true;
        }
        return TextUtils.isEmpty(text.trim());
    }

    /**
     * 修复URL
     * 如：//cnblogs.com
     * @param url 路径
     */
    public static String fixUrl(@Nullable String url) {
        if (url != null && url.startsWith("//")) {
            return "https:".concat(url);
        }
        return url;
    }

    /**
     * 文本是否包含字符串
     * @param text 文本
     * @param array 包含的字符串
     */
    public static boolean containsText(@Nullable String text, String... array) {
        if (TextUtils.isEmpty(text) || text == null) {
            return false;
        }
        for (String item : array) {
            if (text.contains(item)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 移除文本
     * @param text 文本
     */
    public static String removeText(@Nullable String text, String... array) {
        if (TextUtils.isEmpty(text) || text == null) {
            return text;
        }
        for (String item : array) {
            text = text.replace(item, "");
        }
        return text;
    }

    /**
     * 字符串转Int
     * @param text 字符串
     */
    public static int parseInt(String text) {
        return parseInt(text, 0);
    }

    /**
     * 字符串转Int
     * @param text 字符串
     * @param defaultValue 默认值
     */
    public static int parseInt(String text, int defaultValue) {
        if (TextUtils.isEmpty(text)) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return defaultValue;
    }


    /**
     * 获取最后匹配的数字
     */
    public static String getNumber(String text) {
        return getNumber(text, text);
    }

    /**
     * 获取最后匹配的数字
     */
    public static String getNumber(String text, String def) {
        if (TextUtils.isEmpty(text)) {
            return text;
        }
        Matcher matcher = S_NUMBER_PATTERN.matcher(text);
        while (matcher.find()) {
            def = matcher.group();
        }
        return def;
    }

    /**
     * 从字符串中查找日期
     * 匹配格式：
     * 1、yyyy-mm-dd
     * 2、yyyy-mm-dd HH:mm
     * @param text 字符串
     * @return 日期
     */
    @Nullable
    public static String findDate(String text) {
        if (isEmpty(text)) {
            return null;
        }
        Matcher matcher = S_DATE_TIME_PATTERN.matcher(text);
        if (matcher.find()) {
            return matcher.group();
        }
        matcher = S_DATE_PATTERN.matcher(text);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }
}
