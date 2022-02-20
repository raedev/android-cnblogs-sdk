package com.cnblogs.sdk.internal.http.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.cnblogs.sdk.internal.handler.BeforeJsonHandler;
import com.cnblogs.sdk.internal.handler.JsonHandler;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * JSON 自定义处理器，用于解析公共数据，默认实现{@link BeforeJsonHandler}<br>
 * <p>
 * 服务器返回示例：{"success":true, "message": null, "data":[] }
 * 需求：当解析success==false 时，抛出message消息，success==true时，返回data字段数据；
 * 使用自定义JsonHandler来解析处理该部分数据即可；
 * </p>
 * @author RAE
 * @date 2022/02/19
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface JSON {

    Class<? extends JsonHandler> value() default BeforeJsonHandler.class;

    /**
     * 数据解析字段
     * <p>如果设置为空字符串，则会继续传递原始数据，否则传递该字段的值</p>
     */
    String dataField() default "Value";

    /**
     * 是否传递原始数据
     * 如果设置为TRUE，dataField为空
     */
    boolean transitive() default false;

}
