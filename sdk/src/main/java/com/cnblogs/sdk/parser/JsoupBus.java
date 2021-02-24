package com.cnblogs.sdk.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * 提供Jsoup单例
 * @author RAE
 * @date 2021/02/15
 */
public final class JsoupBus {

    private static final JsoupBus S_JSOUP_BUS = new JsoupBus();

    public static JsoupBus getDefault() {
        return S_JSOUP_BUS;
    }

    /**
     * 解析HTML
     * @param html HTML
     * @return 文档
     */
    public Document parse(String html) {
        return Jsoup.parse(html);
    }
}
