package com.cnblogs.sdk.api.web;

import com.cnblogs.sdk.base.BaseApiTest;
import com.cnblogs.sdk.base.CnblogsAndroidRunner;
import com.cnblogs.sdk.base.WebApi;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * 搜索接口测试
 * @author RAE
 * @date 2022/02/15
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
@RunWith(CnblogsAndroidRunner.class)
public class SearchApiTest extends BaseApiTest {

    @WebApi
    private ISearchApi mApi;

    @Test
    public void testSearchBlogs() {
        exec(mApi.searchBlogs("java", 2));
        exec(mApi.searchMyBlogs("http", 1));
    }

    @Test
    public void testSearchNews() {
        exec(mApi.searchNews("java", 1));
    }

    @Test
    public void testSearchQuestions() {
        exec(mApi.searchQuestions("java", 1));
    }

    @Test
    public void testSearchKbs() {
        exec(mApi.searchKbs("java", 1));
    }

    @Test
    public void testSearchUsers() {
        exec(mApi.searchUsers("ae", 2));
    }
}
