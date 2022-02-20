package com.cnblogs.sdk.model;

import android.os.Parcelable;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 文章抽象类
 * 由于传递的数据会比较大，不生成{@link Parcelable}接口，界面传值请通过Id从数据库中查询
 * @author RAE
 * @date 2021/03/01
 */
public abstract class ArticleInfo {

    /**
     * 所属分类
     */
    private String categoryId;

    /**
     * 文章内容，不插入数据库，读取的时候从缓存文件中加载赋值
     */
    private String contentId;

    /**
     * 标题
     */
    private String title;

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
     * 原文地址
     */
    private String url;

    /**
     * 缩略图
     */
    @Nullable
    private String thumbUrl;

    /**
     * 作者信息
     */
    @Nullable
    @SerializedName("author")
    private PersonalInfo authorInfo;

    private List<String> mThumbUrls;


    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Nullable
    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(@Nullable String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    @Nullable
    public PersonalInfo getAuthorInfo() {
        return authorInfo;
    }

    public void setAuthorInfo(@Nullable PersonalInfo authorInfo) {
        this.authorInfo = authorInfo;
    }

    public List<String> getThumbUrls() {
        return mThumbUrls;
    }

    public void setThumbUrls(List<String> thumbUrls) {
        mThumbUrls = thumbUrls;
    }
}
