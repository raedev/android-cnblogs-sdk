package com.cnblogs.sdk.parser;

import androidx.annotation.NonNull;

import com.cnblogs.sdk.internal.HtmlParser;

import java.io.IOException;

/**
 * Html解析
 * @author RAE
 * @date 2021/02/20
 */
public interface IHtmlParser<T> {

    /**
     * 将HTML代码解析成对象
     * @param methodName 自定义方法名称{@link HtmlParser#methodName()}
     * @param html HTML代码
     * @return 对象
     * @throws IOException 抛出异常
     */
    T parseHtml(@NonNull String html) throws IOException;
}
