package com.cnblogs.sdk.model;

/**
 * 评论信息
 * @author RAE
 * @date 2022/03/01
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class CommentInfo {


    /**
     * 评论Id
     */
    private String id;
    /**
     * 文章Id
     */
    private String postId;

    /**
     * 回复评论Id
     */
    private String parentId;
    /**
     * 评论者
     */
    private String authorName;
    /**
     * 评论者blogApp
     */
    private String blogApp;
    /**
     * 发表时间
     */
    private String date;
    ve

    /**
     * 评论内容
     */
    private CharSequence body;

    /**
     * 引用的评论内容
     */
    private String quote;

    /**
     * 支持数量
     */
    private String like;
    /**
     * 反对数量
     */
    private String unlike;
    /**
     * 评论者头像
     */
    private String avatar;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getBlogApp() {
        return blogApp;
    }

    public void setBlogApp(String blogApp) {
        this.blogApp = blogApp;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public CharSequence getBody() {
        return body;
    }

    public void setBody(CharSequence body) {
        this.body = body;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getUnlike() {
        return unlike;
    }

    public void setUnlike(String unlike) {
        this.unlike = unlike;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
