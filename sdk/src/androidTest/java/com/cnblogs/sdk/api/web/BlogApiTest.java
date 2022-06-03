package com.cnblogs.sdk.api.web;

import com.cnblogs.sdk.api.param.BlogListParam;
import com.cnblogs.sdk.base.BaseApiTest;
import com.cnblogs.sdk.base.CnblogsAndroidRunner;
import com.cnblogs.sdk.base.WebApi;
import com.cnblogs.sdk.internal.http.body.JsonRequestBody;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collections;

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

    @Test
    public void testNextArticle() {
        exec(mApi.nextArticle("dai-blog", "15921475"));
    }

    @Test
    public void testRecommendBlogs() {
        exec(mApi.recommendBlogs("15921475", "面渣逆袭：二十二图、八千字、二十问，彻底搞定MyBatis！"));
    }

    @Test
    public void testPostStat() {
        exec(mApi.postStat("wengzp", JsonRequestBody.create(Collections.singletonList("15951501"))));
    }

    @Test
    public void testAuthorTopList() {
        exec(mApi.authorTopList("wengzp"));
    }

    @Test
    public void testCommentList() {
        exec(mApi.commentList("chenrui7", "15953126", 0));
    }
}
