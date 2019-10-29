package com.cnblogs.api;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

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

    public BlogApiTest() {
        mBlogApi = mApi.getBlogApi();
    }

    @Test
    public void testBlogList() {
        run(mBlogApi.getHomeBlogs(new BlogListParam(1, "SiteHome", 0, 808)));
    }

    @Test
    public void testHomeCategory() {
        run(mBlogApi.getHomeCategory());

        Context context = InstrumentationRegistry.getContext();

    }
}
