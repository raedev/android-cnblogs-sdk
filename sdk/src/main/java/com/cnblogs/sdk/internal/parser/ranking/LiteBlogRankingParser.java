package com.cnblogs.sdk.internal.parser.ranking;

import com.cnblogs.sdk.internal.parser.HtmlParser;
import com.cnblogs.sdk.internal.utils.CnblogsUtils;
import com.cnblogs.sdk.model.RankingInfo;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * 精简版的排行榜解析
 * @author RAE
 * @date 2021/03/01
 */
public class LiteBlogRankingParser extends HtmlParser<List<RankingInfo>> {

    @Override
    protected List<RankingInfo> parseHtml(Document doc) {
        LiteRankingParser parser = new LiteRankingParser();
        List<RankingInfo> data = parser.parse(doc);
        CnblogsUtils.remove(data, m -> {
            // 不包含新闻类型
            return m.getGroup() != null && m.getGroup().contains("新闻");
        });
        return data;
    }

}
