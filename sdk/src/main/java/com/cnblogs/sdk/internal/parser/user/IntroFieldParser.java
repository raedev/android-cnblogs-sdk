package com.cnblogs.sdk.internal.parser.user;

import android.text.TextUtils;

import com.cnblogs.sdk.internal.parser.HtmlParser;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

/**
 * 个人信息Form字段解析
 * @author RAE
 * @date 2022/02/18
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class IntroFieldParser extends HtmlParser<Map<String, String>> {

    @Override
    protected Map<String, String> parseHtml(Document doc) {
        Map<String, String> result = new HashMap<>();
        // input 输入框
        parseInputItems(result, doc.select("input"));
        // textarea 文本框
        parseInputItems(result, doc.select("textarea"));
        // textarea 文本框
        parseInputItems(result, doc.select("select"));

        // select 下拉框
        Elements items = doc.select("select");
        for (Element item : items) {
            String name = item.attr("name");
            String value = null;

        }

        return result;
    }

    private void parseInputItems(Map<String, String> map, Elements items) {
        for (Element item : items) {
            String name = item.attr("name");
            boolean isHidden = TextUtils.equals("hidden", item.attr("type"));
            if (map.containsKey(name) && isHidden) {
                // 不添加已经存在并且为Hidden字段的
                continue;
            }
            String tag = item.tagName();
            // textarea
            if (TextUtils.equals("textarea", tag)) {
                map.put(name, item.text());
                continue;
            }
            // select
            if (TextUtils.equals("select", tag)) {
                Elements options = item.select("option");
                for (Element option : options) {
                    if (option.hasAttr("selected")) {
                        map.put(name, option.attr("value"));
                        break;
                    }
                }
                continue;
            }
            // default
            map.put(name, item.attr("value"));
        }
    }
}
