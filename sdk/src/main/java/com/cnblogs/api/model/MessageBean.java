package com.cnblogs.api.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 消息实体
 * Created by rae on 2019-08-14.
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class MessageBean implements Parcelable {
    private String id;
    private String messageId;
    private String blogApp;
    private String authorName;
    private String avatar;
    private String content;
    private String postDate;
    // 来源地址
    private String sourceUrl;
    private String title;
    // 是否为当前作者回复的消息
    private boolean isAuthor;

    public boolean isAuthor() {
        return isAuthor;
    }

    public void setAuthor(boolean author) {
        isAuthor = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
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

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public MessageBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.messageId);
        dest.writeString(this.blogApp);
        dest.writeString(this.authorName);
        dest.writeString(this.avatar);
        dest.writeString(this.content);
        dest.writeString(this.postDate);
        dest.writeString(this.sourceUrl);
        dest.writeString(this.title);
        dest.writeByte(this.isAuthor ? (byte) 1 : (byte) 0);
    }

    protected MessageBean(Parcel in) {
        this.id = in.readString();
        this.messageId = in.readString();
        this.blogApp = in.readString();
        this.authorName = in.readString();
        this.avatar = in.readString();
        this.content = in.readString();
        this.postDate = in.readString();
        this.sourceUrl = in.readString();
        this.title = in.readString();
        this.isAuthor = in.readByte() != 0;
    }

    public static final Creator<MessageBean> CREATOR = new Creator<MessageBean>() {
        @Override
        public MessageBean createFromParcel(Parcel source) {
            return new MessageBean(source);
        }

        @Override
        public MessageBean[] newArray(int size) {
            return new MessageBean[size];
        }
    };
}
