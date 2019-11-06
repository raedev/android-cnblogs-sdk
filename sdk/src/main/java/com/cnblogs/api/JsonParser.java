package com.cnblogs.api;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 标记为用JSON解析
 * Created by rae on 2019-10-22.
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface JsonParser {

    /**
     * 使用默认的解析器
     */
    boolean isDefault() default false;
}
