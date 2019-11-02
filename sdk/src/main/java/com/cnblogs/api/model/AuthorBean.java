package com.cnblogs.api.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者信息
 */
public class AuthorBean implements Parcelable {

    public static final Creator<AuthorBean> CREATOR = new Creator<AuthorBean>() {
        @Override
        public AuthorBean createFromParcel(Parcel source) {
            return new AuthorBean(source);
        }

        @Override
        public AuthorBean[] newArray(int size) {
            return new AuthorBean[size];
        }
    };
    // 作者Id，一般为blogApp
    private String id;
    // 用户Id
    private String userId;
    // 昵称
    private String name;
    // 头像地址
    private String avatar;
    // 粉丝数
    private String fansCount;
    // 关注数
    private String followerCount;

    public AuthorBean() {
    }

    protected AuthorBean(Parcel in) {
        this.id = in.readString();
        this.userId = in.readString();
        this.name = in.readString();
        this.avatar = in.readString();
        this.fansCount = in.readString();
        this.followerCount = in.readString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFansCount() {
        return fansCount;
    }

    public void setFansCount(String fansCount) {
        this.fansCount = fansCount;
    }

    public String getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(String followerCount) {
        this.followerCount = followerCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.userId);
        dest.writeString(this.name);
        dest.writeString(this.avatar);
        dest.writeString(this.fansCount);
        dest.writeString(this.followerCount);
    }
}
