package com.cnblogs.sdk.internal.parser.ranking;

import com.cnblogs.sdk.model.RankingInfo;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * 精简排行榜解析
 * @author RAE
 * @date 2022/02/17
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
class LiteRankingParser {

    public List<RankingInfo> parse(Document doc) {
        List<RankingInfo> result = new ArrayList<>();
        Elements cards = doc.select(".card");
        for (Element card : cards) {
            String group = card.select(".card-title").text();
            Elements lis = card.select(".item-list li");
            for (Element li : lis) {
                RankingInfo m = new RankingInfo();
                m.setGroup(group);
                m.setUrl(li.select("a").attr("href"));
                m.setTitle(li.select("a").text());
                result.add(m);
            }
        }
        return result;
    }
}
