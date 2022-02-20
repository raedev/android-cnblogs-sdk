package com.cnblogs.sdk.internal.parser.search;

import com.cnblogs.sdk.model.NewsInfo;

/**
 * 新闻搜索列表解析
 * @author RAE
 * @date 2022/02/20
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class NewsSearchParser extends BaseSearchParser<NewsInfo> {
    @Override
    protected NewsInfo create() {
        return new NewsInfo();
    }
}
