package com.cnblogs.sdk.model;

/**
 * 用户信息
 * @author RAE
 * @date 2021/02/23
 */
public class UserInfo {

    /**
     * 账号
     */
    private String spaceUserId;

    /**
     * 昵称
     */
    private String displayName;

    /**
     * blogApp,非常重要的字段,唯一ID
     */
    private String blogApp;

    /**
     * 未读消息数量
     */
    private int unreadMsg;

    /**
     * 个人中心主页
     */
    private String homeLink;

    /**
     * 博客主页
     */
    private String blogLink;

    /**
     * 头像小图
     */
    private String iconName;

    /**
     * 头像大图
     */
    private String avatarName;

    public UserInfo() {
    }

    public String getSpaceUserId() {
        return spaceUserId;
    }

    public void setSpaceUserId(String spaceUserId) {
        this.spaceUserId = spaceUserId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getBlogApp() {
        if (blogApp == null) {
            return this.spaceUserId;
        }
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
