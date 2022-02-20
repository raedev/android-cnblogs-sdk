package com.cnblogs.sdk.internal.parser.search;

import com.cnblogs.sdk.model.BlogInfo;

/**
 * 博客搜索列表解析
 * @author RAE
 * @date 2022/02/20
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class BlogSearchParser extends BaseSearchParser<BlogInfo> {
    @Override
    protected BlogInfo create() {
        return new BlogInfo();
    }
}
