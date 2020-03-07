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
     * 使用默认的解析器，会默认解析公共的Json格式
     * 如服务器返回的默认格式：{success:true, message="",data:[]}
     */
    boolean isDefault() default false;

    /**
     * 是否将服务器返回的源数据返回回去，默认为FALSE
     * 注意：要求接受返回值为字符串类型
     * 接口定义示例：Observable<String> method();
     */
    boolean source() default false;
}
