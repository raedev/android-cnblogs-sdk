package com.cnblogs.sdk.api.web;

import com.cnblogs.sdk.api.param.BlogListParam;
import com.cnblogs.sdk.base.BaseApiTest;
import com.cnblogs.sdk.base.CnblogsAndroidRunner;
import com.cnblogs.sdk.base.WebApi;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * 博客接口测试
 * @author RAE
 * @date 2022/02/15
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
@RunWith(CnblogsAndroidRunner.class)
public class BlogApiTest extends BaseApiTest {

    @WebApi
    private IBlogApi mApi;

    /**
     * 测试博客列表
     */
    @Test
    public void testBlogList() {
        BlogListParam param = new BlogListParam("108698", 1);
        exec(mApi.blogList(param));
    }

    /**
     * 测试博客内容
     */
    @Test
    public void testBlogContent() {
        String url = "https://www.cnblogs.com/rainybunny/p/15906537.html";
        exec(mApi.content(url));
    }
}
