package com.cnblogs.sdk.internal.parser.search;

import com.cnblogs.sdk.model.KbInfo;

/**
 * 知识库搜索列表解析
 * @author RAE
 * @date 2022/02/20
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class KbSearchParser extends BaseSearchParser<KbInfo> {
    @Override
    protected KbInfo create() {
        return new KbInfo();
    }
}
