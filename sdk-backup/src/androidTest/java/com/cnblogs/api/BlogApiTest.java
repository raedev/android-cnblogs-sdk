package com.cnblogs.api;


import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.cnblogs.api.param.BlogListParam;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by rae on 2019-10-21.
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
@RunWith(AndroidJUnit4.class)
public class BlogApiTest extends CnblogsApiTest {

    private IBlogApi mBlogApi;

    @Override
    public void setup() {
        super.setup();
        mBlogApi = mOpenApi.getBlogApi();
    }

    @Test
    public void testBlogList() {
        run(mBlogApi.getHomeBlogs(new BlogListParam(1, "SiteHome", 0, 808)));
    }

    @Test
    public void testBlogDetail() {
        run(mBlogApi.getBlogDetail("https://www.cnblogs.com/skabyy/p/11396571.html"));
    }

    @Test
    public void testBlogViewCount() {
        run(mBlogApi.getBlogViewCount("skabyy", "11396571"));
    }

    @Test
    public void testBlogCommentCount() {
        run(mBlogApi.getBlogCommentCount("skabyy", "11396571"));
    }

    @Test
    public void testBlogPostInfo() {
        run(mBlogApi.getBlogPostInfo("skabyy", "166858", "11396571", "2f59e04f-bc3c-e311-8d02-90b11c0b17d6"));
    }


    @Test
    public void testHomeCategory() {
        run(mBlogApi.getHomeCategory());
    }

}
