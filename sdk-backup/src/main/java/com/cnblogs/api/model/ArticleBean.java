package com.cnblogs.api.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 文章类
 * Created by rae on 2019/11/2.
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class ArticleBean implements Parcelable {

    private String title; // 标题
    private String summary; // 摘要
    private String content; // 内容
    private String likeCount; // 推荐数
    private String commentCount; // 评论数
    private String viewCount; // 阅读数
    private String postDate; // 发布时间
    private String url; // 原文路径
    private String coverUrl; // 封面图路径
    private boolean isLike; // 是否推荐
    private AuthorBean author; // 作者信息

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getViewCount() {
        return viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public AuthorBean getAuthor() {
        return author;
    }

    public void setAuthor(AuthorBean author) {
        this.author = author;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    public ArticleBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.summary);
        dest.writeString(this.content);
        dest.writeString(this.likeCount);
        dest.writeString(this.commentCount);
        dest.writeString(this.viewCount);
        dest.writeString(this.postDate);
        dest.writeString(this.url);
        dest.writeString(this.coverUrl);
        dest.writeByte(this.isLike ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.author, flags);
    }

    protected ArticleBean(Parcel in) {
        this.title = in.readString();
        this.summary = in.readString();
        this.content = in.readString();
        this.likeCount = in.readString();
        this.commentCount = in.readString();
        this.viewCount = in.readString();
        this.postDate = in.readString();
        this.url = in.readString();
        this.coverUrl = in.readString();
        this.isLike = in.readByte() != 0;
        this.author = in.readParcelable(AuthorBean.class.getClassLoader());
    }

    public static final Creator<ArticleBean> CREATOR = new Creator<ArticleBean>() {
        @Override
        public ArticleBean createFromParcel(Parcel source) {
            return new ArticleBean(source);
        }

        @Override
        public ArticleBean[] newArray(int size) {
            return new ArticleBean[size];
        }
    };
}
