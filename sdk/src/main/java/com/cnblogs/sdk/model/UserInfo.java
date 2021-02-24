package com.cnblogs.sdk.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 用户信息
 * @author RAE
 * @date 2021/02/23
 */
public class UserInfo extends AuthTokenInfo implements Parcelable {

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel in) {
            return new UserInfo(in);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };
    /**
     * spaceUserId : 446312
     * displayName : RAE
     * blogApp : rae
     * unreadMsg : 0
     * homeLink : https://home.cnblogs.com/u/chenrui7
     * blogLink : https://www.cnblogs.com/chenrui7/
     * iconName : //pic.cnblogs.com/face/446312/20200420163446.png
     * avatarName : //pic.cnblogs.com/avatar/446312/20200420163446.png
     */


    private int spaceUserId;
    private String displayName;
    private String blogApp;
    private int unreadMsg;
    private String homeLink;
    private String blogLink;
    private String iconName;
    private String avatarName;

    protected UserInfo(Parcel in) {
        super(in);
        spaceUserId = in.readInt();
        displayName = in.readString();
        blogApp = in.readString();
        unreadMsg = in.readInt();
        homeLink = in.readString();
        blogLink = in.readString();
        iconName = in.readString();
        avatarName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(spaceUserId);
        dest.writeString(displayName);
        dest.writeString(blogApp);
        dest.writeInt(unreadMsg);
        dest.writeString(homeLink);
        dest.writeString(blogLink);
        dest.writeString(iconName);
        dest.writeString(avatarName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getSpaceUserId() {
        return spaceUserId;
    }

    public void setSpaceUserId(int spaceUserId) {
        this.spaceUserId = spaceUserId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getBlogApp() {
        return blogApp;
    }

    public void setBlogApp(String blogApp) {
        this.blogApp = blogApp;
    }

    public int getUnreadMsg() {
        return unreadMsg;
    }

    public void setUnreadMsg(int unreadMsg) {
        this.unreadMsg = unreadMsg;
    }

    public String getHomeLink() {
        return homeLink;
    }

    public void setHomeLink(String homeLink) {
        this.homeLink = homeLink;
    }

    public String getBlogLink() {
        return blogLink;
    }

    public void setBlogLink(String blogLink) {
        this.blogLink = blogLink;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public String getAvatarName() {
        return avatarName;
    }

    public void setAvatarName(String avatarName) {
        this.avatarName = avatarName;
    }
}
