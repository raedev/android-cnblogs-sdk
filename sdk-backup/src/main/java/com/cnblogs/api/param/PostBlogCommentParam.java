package com.cnblogs.api.param;

/**
 * 发表评论请求参数
 * Created by rae on 2019/11/8.
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class PostBlogCommentParam {
    private int postId;
    private int parentCommentId;
    private String body;

    public PostBlogCommentParam(int postId, int parentCommentId, String body) {
        this.postId = postId;
        this.parentCommentId = parentCommentId;
        this.body = body;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(int parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
