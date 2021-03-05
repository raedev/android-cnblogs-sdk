package com.cnblogs.sdk.parser;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * HTML解析器注解
 * 用来标志一个请求的响应使用对应的{@link com.cnblogs.sdk.parser.IHtmlParser}实现类来解析数据
 * @author RAE
 * @date 2021/02/20
 */
@SuppressWarnings("rawtypes")
@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface HtmlParser {

    /**
     * 解析该接口的HTML解析类
     */
    Class<? extends IHtmlParser> value();

}
