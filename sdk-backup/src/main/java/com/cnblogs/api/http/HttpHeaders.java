package com.cnblogs.api.http;

/**
 * 公共请求头常量定义
 * Created by rae on 2019-10-22.
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public final class HttpHeaders {

    /**
     * 异步请求
     */
    public static final String XHR = "X-Requested-With: XMLHttpRequest";
    /**
     * 引擎
     */
    static final String UA = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.120 Safari/537.36";
    /**
     * 接收的字符串
     */
    static final String LANG = "zh-CN,zh;q=0.8";
    /**
     * 缓存控制
     */
    static final String CACHE_CONTROL = "no-cache";
}
