package com.cnblogs.sdk.internal.http;

/**
 * HttpHeader常量
 * @author RAE
 * @date 2022/02/17
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public final class HttpHeader {
    /**
     * XMLHttpRequest 异步请求
     */
    public final static String XHR = "X-Requested-With:XMLHttpRequest";
    /**
     * 请求体格式为JSON类型
     */
    public final static String CONTENT_TYPE_JSON = "Content-Type:application/json; charset=UTF-8";
    /**
     * 请求需要接收JSON类型数据
     */
    public final static String ACCEPT_TYPE_JSON = "accept:application/json;";
    /**
     * 浏览器引擎
     */
    public static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 11_2_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.141 Safari/537.36";
    /**
     * 将Form-Data参数转换为JSON格式
     */
    public final static String JSON_PARAM = "param:application/json;";
}
