package com.cnblogs.sdk.internal;

import com.cnblogs.sdk.parser.IHtmlParser;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * HTML解析器注解
 * 用来标志一个请求的响应使用对应的{@link com.cnblogs.sdk.parser.IHtmlParser}实现类来解析数据
 * @author RAE
 * @date 2021/02/20
 */
@SuppressWarnings("rawtypes")
@Target(ElementType.METHOD)
public @interface HtmlParser {
    Class<? extends IHtmlParser> value();
}
