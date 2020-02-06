package com.cnblogs.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户信息
 * Created by ChenRui on 2017/1/14 02:21.
 */
public class UserInfoBean implements Parcelable {

    private String blogApp;
    /**
     * 头像地址
     */
    @SerializedName("iconName")
    private String avatar;
    /**
     * 昵称
     */
    private String displayName;
    /**
     * 备注名称
     */
    private String remarkName;
    /**
     * 是已经关注
     */
    private boolean mHasFollow;
    /**
     * 自我介绍
     */
    private String introduce;
    /**
     * 入园时间
     */
    private String joinDate;
    private String fansCount;
    private String followCount;
    // 登录账号
    private String loginAccount;
    // 用户Id
    private String userId;

    /* 资料信息 */
    private Map<String, String> profiles;

    public UserInfoBean() {
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UserInfoBean) {
            return TextUtils.equals(this.blogApp, ((UserInfoBean) obj).getBlogApp());
        }
        return super.equals(obj);
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Map<String, String> getProfiles() {
        return profiles;
    }

    public void setProfiles(Map<String, String> profiles) {
        this.profiles = profiles;
    }

    public String getBlogApp() {
        return blogApp;
    }

    public void setBlogApp(String blogApp) {
        this.blogApp = blogApp;
    }

    public String getDisplayName() {
        // 优先显示备注
        if (!TextUtils.isEmpty(remarkName)) {
            return remarkName;
        }
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public String getRemarkName() {
        return remarkName;
    }

    public void setRemarkName(String remarkName) {
        this.remarkName = remarkName;
    }


    public boolean isHasFollow() {
        return mHasFollow;
    }

    public void setHasFollow(boolean hasFollow) {
        mHasFollow = hasFollow;
    }


    public String getFansCount() {
        return fansCount;
    }

    public void setFansCount(String fansCount) {
        this.fansCount = fansCount;
    }

    public String getFollowCount() {
        return followCount;
    }

    public void setFollowCount(String followCount) {
        this.followCount = followCount;
    }

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
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
        dest.writeString(this.avatar);
        dest.writeString(this.displayName);
        dest.writeString(this.remarkName);
        dest.writeByte(this.mHasFollow ? (byte) 1 : (byte) 0);
        dest.writeString(this.introduce);
        dest.writeString(this.joinDate);
        dest.writeString(this.fansCount);
        dest.writeString(this.followCount);
        dest.writeString(this.loginAccount);
        dest.writeString(this.userId);
        dest.writeInt(this.profiles.size());
        for (Map.Entry<String, String> entry : this.profiles.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeString(entry.getValue());
        }
    }

    protected UserInfoBean(Parcel in) {
        this.blogApp = in.readString();
        this.avatar = in.readString();
        this.displayName = in.readString();
        this.remarkName = in.readString();
        this.mHasFollow = in.readByte() != 0;
        this.introduce = in.readString();
        this.joinDate = in.readString();
        this.fansCount = in.readString();
        this.followCount = in.readString();
        this.loginAccount = in.readString();
        this.userId = in.readString();
        int profilesSize = in.readInt();
        this.profiles = new HashMap<String, String>(profilesSize);
        for (int i = 0; i < profilesSize; i++) {
            String key = in.readString();
            String value = in.readString();
            this.profiles.put(key, value);
        }
    }

    public static final Creator<UserInfoBean> CREATOR = new Creator<UserInfoBean>() {
        @Override
        public UserInfoBean createFromParcel(Parcel source) {
            return new UserInfoBean(source);
        }

        @Override
        public UserInfoBean[] newArray(int size) {
            return new UserInfoBean[size];
        }
    };
}
