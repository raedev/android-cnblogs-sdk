package com.cnblogs.sdk.parser.blog;

import androidx.annotation.NonNull;

import com.cnblogs.sdk.parser.BaseHtmlParser;

import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * 博文
 * @author RAE
 * @date 2021/03/01
 */
@SuppressWarnings("SpellCheckingInspection")
public final class BlogContentParser extends BaseHtmlParser<String> {

    @Override
    public String parseHtml(@NonNull String html) throws IOException {
        // 返回格式为：“博客内容”，要去掉双引号
        int length = html.length();
        if (length < 3) {
            return html;
        }
        return html.substring(1, length - 1);
    }

    @Override
    protected String parseHtml(Document doc) {
        return null;
    }
}
