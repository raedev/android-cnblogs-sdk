package com.cnblogs.sdk.internal.utils

import java.util.regex.Pattern

/**
 * 解析相关
 * @author RAE
 * @date 2022/10/04
 * @copyright Copyright (c) https://github.com/raedev All rights reserved.
 */
internal object HtmlUtils {

    private val NUMBER_PATTERN = Pattern.compile("\\d+")
    private val DATE_PATTERN = Pattern.compile("\\d{4}-\\d{2}-\\d{2}")
    private val DATE_TIME_PATTERN = Pattern.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}")

    /**
     * 解析成数字类型
     */
    fun String.parseNumber(defValue: Int = 0): Int {
        val matcher = NUMBER_PATTERN.matcher(this)
        return if (matcher.find()) matcher.group().toIntOrNull() ?: defValue else defValue
    }

    /**
     * 解析URL，将转换成标准的http协议
     */
    fun String.parseUrl(): String {
        if (this.startsWith("//")) return "https:$this"
        return this
    }


    /**
     * 解析成日期类型
     */
    fun String.parseDate(): String? {
        var matcher = DATE_TIME_PATTERN.matcher(this)
        if (matcher.find()) {
            return matcher.group()
        }
        matcher = DATE_PATTERN.matcher(this)
        if (matcher.find()) {
            return matcher.group()
        }
        return null
    }


    /**
     * 从路径中获取blogApp
     */
    fun String.parseBlogApp(): String {
        // 处理规则：一般为链接的最后一个path
        // 比如：https://home.cnblogs.com/u/blogApp
        if (this.endsWith("/")) {
            // 去除最后一个 '/'
            return this.substring(0, this.length - 1)
        }
        val endIndex: Int = this.lastIndexOf("/")
        return if (endIndex > 0) this.substring(endIndex + 1) else this
    }
}