package com.cnblogs.sdk.internal.parser.search;

import com.cnblogs.sdk.model.QuestionInfo;

/**
 * 博问搜索列表解析
 * @author RAE
 * @date 2022/02/20
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class QuestionSearchParser extends BaseSearchParser<QuestionInfo> {
    @Override
    protected QuestionInfo create() {
        return new QuestionInfo();
    }
}
