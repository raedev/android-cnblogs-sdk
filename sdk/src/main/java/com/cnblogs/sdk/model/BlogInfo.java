package com.cnblogs.sdk.model;

/**
 * 博客信息
 * @author RAE
 * @date 2022/02/17
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class BlogInfo extends ArticleInfo {

    /**
     * 文章Id，为URL里面的参数
     */
    private String postId;

    /**
     * 博客Id，从正文里面解析的Id
     */
    private String blogId;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getBlogId() {
        return blogId;
    }

    public void setBlogId(String blogId) {
        this.blogId = blogId;
    }
}
