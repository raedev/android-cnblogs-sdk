package com.cnblogs.sdk.model;

import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

/**
 * 文章实体，包括：博客、新闻、知识库
 * 由于传递的数据会比较大，不生成{@link Parcelable}接口，界面传值请通过Id从数据库中查询
 * @author RAE
 * @date 2021/03/01
 */
@Entity(tableName = "ARTICLE")
public class ArticleInfo {

    /**
     * 标题
     */
    private String title;

    /**
     * 文章Id，为URL里面的参数
     */
    @PrimaryKey(autoGenerate = false)
    @NonNull
    private String postId;

    /**
     * 博客Id，从正文里面解析的Id
     */
    private String blogId;

    /**
     * 类型：{@link ArticleType#name()}
     */
    private String type;

    /**
     * 简要
     */
    private String summary;

    /**
     * 评论数量
     */
    private String commentCount;

    /**
     * 查看数量
     */
    private String viewCount;

    /**
     * 点赞数量
     */
    private String likeCount;

    /**
     * 发表时间
     */
    private String postDate;

    /**
     * 标签
     */
    private String tag;

    /**
     * 所属分类
     */
    private String categoryId;

    /**
     * 原文地址
     */
    private String url;

    /**
     * 缩略图
     */
    @Nullable
    private String thumbUrl;

    /**
     * 是否已读
     */
    private boolean isRead;

    /**
     * 创建时间
     */
    private long createdTime;

    /**
     * 作者信息
     */
    @Nullable
    private AuthorInfo authorInfo;

    public ArticleInfo() {
        this.postId = UUID.randomUUID().toString();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NonNull
    public String getPostId() {
        return postId;
    }

    public void setPostId(@NonNull String postId) {
        this.postId = postId;
    }

    public String getBlogId() {
        return blogId;
    }

    public void setBlogId(String blogId) {
        this.blogId = blogId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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

    public String getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @Nullable
    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(@Nullable String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    @Nullable
    public AuthorInfo getAuthorInfo() {
        return authorInfo;
    }

    public void setAuthorInfo(@Nullable AuthorInfo authorInfo) {
        this.authorInfo = authorInfo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
