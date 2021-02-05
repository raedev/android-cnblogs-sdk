package com.cnblogs.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * 精简的用户信息
 * Created by rae on 2020-01-19.
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class SimpleUserInfoBean implements Parcelable {

    private String blogApp;
    private String blogLink;
    private String displayName;
    @SerializedName("iconName")
    private String avatar;
    @SerializedName("spaceUserId")
    private String userId;

    public String getBlogApp() {
        return blogApp;
    }

    public void setBlogApp(String blogApp) {
        this.blogApp = blogApp;
    }

    public String getBlogLink() {
        return blogLink;
    }

    public void setBlogLink(String blogLink) {
        this.blogLink = blogLink;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.blogApp);
        dest.writeString(this.blogLink);
        dest.writeString(this.displayName);
        dest.writeString(this.avatar);
        dest.writeString(this.userId);
    }

    public SimpleUserInfoBean() {
    }

    protected SimpleUserInfoBean(Parcel in) {
        this.blogApp = in.readString();
        this.blogLink = in.readString();
        this.displayName = in.readString();
        this.avatar = in.readString();
        this.userId = in.readString();
    }

    public static final Parcelable.Creator<SimpleUserInfoBean> CREATOR = new Parcelable.Creator<SimpleUserInfoBean>() {
        @Override
        public SimpleUserInfoBean createFromParcel(Parcel source) {
            return new SimpleUserInfoBean(source);
        }

        @Override
        public SimpleUserInfoBean[] newArray(int size) {
            return new SimpleUserInfoBean[size];
        }
    };
}
