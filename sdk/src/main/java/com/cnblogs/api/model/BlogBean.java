package com.cnblogs.api.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 博客实体
 * 注意：序列化的时候不要传数据大的这两个字段（summary、content)
 * Created by ChenRui on 2016/11/28 23:45.
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class BlogBean implements Parcelable {

    public static final Creator<BlogBean> CREATOR = new Creator<BlogBean>() {
        @Override
        public BlogBean createFromParcel(Parcel source) {
            return new BlogBean(source);
        }

        @Override
        public BlogBean[] newArray(int size) {
            return new BlogBean[size];
        }
    };
    private String blogId; // 博客Id
    private String postId; // 帖子Id
    private String title; // 标题
    private String url; // 原文路径
    private String blogApp; // 作者BlogAPP
    private String avatar; // 头像地址
    private String summary; // 摘要
    private String author; // 作者昵称
    private String likeCount; // 推荐数
    private String commentCount; // 评论数
    private String viewCount; // 阅读数
    private String postDate; // 发布时间

    public BlogBean() {
    }

    protected BlogBean(Parcel in) {
        this.blogId = in.readString();
        this.postId = in.readString();
        this.title = in.readString();
        this.url = in.readString();
        this.blogApp = in.readString();
        this.avatar = in.readString();
        this.summary = in.readString();
        this.author = in.readString();
        this.likeCount = in.readString();
        this.commentCount = in.readString();
        this.viewCount = in.readString();
        this.postDate = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBlogApp() {
        return blogApp;
    }

    public void setBlogApp(String blogApp) {
        this.blogApp = blogApp;
    }

    public String getBlogId() {
        return blogId;
    }

    public void setBlogId(String blogId) {
        this.blogId = blogId;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
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

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getViewCount() {
        return viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.blogId);
        dest.writeString(this.postId);
        dest.writeString(this.title);
        dest.writeString(this.url);
        dest.writeString(this.blogApp);
        dest.writeString(this.avatar);
        dest.writeString(this.summary);
        dest.writeString(this.author);
        dest.writeString(this.likeCount);
        dest.writeString(this.commentCount);
        dest.writeString(this.viewCount);
        dest.writeString(this.postDate);
    }
}
