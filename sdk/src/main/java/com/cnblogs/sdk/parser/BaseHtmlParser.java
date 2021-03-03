package com.cnblogs.sdk.parser;

import androidx.annotation.NonNull;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * HTML解析器基类
 * @author RAE
 * @date 2021/02/25
 */
public abstract class BaseHtmlParser<T> implements IHtmlParser<T> {

    /**
     * 缓存的方法
     */
    private final Map<String, Method> mMethodCacheMap = new ConcurrentHashMap<>();

    @Override
    public T parseHtml(@NonNull String html) throws IOException {
        Document doc = Jsoup.parse(html);
        return parseHtml(doc);
    }

    /**
     * 解析HTML
     * @param doc 文档
     * @return 对象
     */
    protected abstract T parseHtml(Document doc);

    // region 解析工具方法

    protected int parseInt(String value) {
        return HtmlParserUtils.parseInt(value);
    }

    protected String findBlogApp(String url) {
        return HtmlParserUtils.findBlogApp(url);
    }

    protected String getNumber(String text) {
        return getNumber(text, "0");
    }

    protected String getNumber(String text, String defValue) {
        return HtmlParserUtils.getNumber(text, defValue);
    }

    protected boolean isEmpty(String text) {
        return HtmlParserUtils.isEmpty(text);
    }


    // endregion
}
