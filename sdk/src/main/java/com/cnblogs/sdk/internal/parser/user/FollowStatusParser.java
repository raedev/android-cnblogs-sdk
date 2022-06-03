package com.cnblogs.sdk.internal.parser.user;

import com.cnblogs.sdk.internal.parser.HtmlParser;

import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * 关注状态
 * @author RAE
 * @date 2022/03/01
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class FollowStatusParser extends HtmlParser<Boolean> {

    @Override
    protected Boolean parseHtml(Document doc) throws IOException {
        String text = doc.select("a").attr("onclick");
        return text.startsWith("unfollow");
    }
}
