package com.cnblogs.sdk.internal.utils;

import com.cnblogs.sdk.BuildConfig;

/**
 * 定义常量
 * @author RAE
 * @date 2021/12/29
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public final class Constant {

    /**
     * 官网地址
     */
    public final static String URL = "https://cnblogs.com";
    /**
     * 开放接口域名
     */
    public final static String OPEN_API_DOMAIN = "api.cnblogs.com";
    /**
     * 开放接口地址
     */
    public final static String OPEN_API_URL = "https://" + OPEN_API_DOMAIN;
    /**
     * 调试模式
     */
    public static boolean DEBUG = BuildConfig.DEBUG;

}
