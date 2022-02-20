package com.cnblogs.sdk.internal.http.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.cnblogs.sdk.internal.parser.HtmlParser;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Html类型请求
 * @author RAE
 * @date 2022/02/15
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface HTML {

    /**
     * 定义解析器
     */
    Class<? extends HtmlParser<?>> value();
}
