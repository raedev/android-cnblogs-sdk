package com.cnblogs.api.http;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by rae on 2019-10-21.
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface HtmlParser {

    /**
     * HTML解析器
     */
    Class<? extends IHtmlParser> value();
}
