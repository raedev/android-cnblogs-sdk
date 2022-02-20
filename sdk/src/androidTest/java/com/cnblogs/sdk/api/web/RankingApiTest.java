package com.cnblogs.sdk.api.web;

import com.cnblogs.sdk.base.BaseApiTest;
import com.cnblogs.sdk.base.CnblogsAndroidRunner;
import com.cnblogs.sdk.base.WebApi;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * 排行榜接口测试
 * @author RAE
 * @date 2022/02/15
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
@RunWith(CnblogsAndroidRunner.class)
public class RankingApiTest extends BaseApiTest {

    @WebApi
    private IRankingApi mApi;

    @Test
    public void testLiteBlogRanking() {
        exec(mApi.liteBlogRanking());
    }

    @Test
    public void testLiteNewsRanking() {
        exec(mApi.liteNewsRanking());
    }
}
