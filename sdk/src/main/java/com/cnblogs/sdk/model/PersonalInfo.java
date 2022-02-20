package com.cnblogs.sdk.model;

import java.util.List;
import java.util.Map;

/**
 * 个人信息
 * @author RAE
 */
public class PersonalInfo {

    /**
     * 显示名称
     */
    private String displayName;

    /**
     * 头像地址
     */
    private String avatarUrl;

    /**
     * 备注名称
     */
    private String remarkName;

    /**
     * blogApp
     */
    private String blogApp;

    /**
     * 博客地址
     */
    private String blogUrl;

    /**
     * 粉丝数
     */
    private int fansCount;

    /**
     * 关注数
     */
    private int followCount;

    /**
     * 园龄
     */
    private String blogAge;

    /**
     * 入园时间
     */
    private String joinDate;

    /**
     * 是否关注
     */
    private boolean isFollowing;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 园子ID
     */
    private String parkId;

    /**
     * 关注时间
     */
    private String followDate;

    /**
     * 自我介绍
     */
    private String introduce;

    /**
     * 其他自定义字段，个人中心每个人设置不同和展示的字段都不同。
     * key: 标题
     * value: 属性值
     * 如：园龄：8年10个月
     */
    private Map<String, String> profileMap;

    private List<UserGroup> groups;

    public List<UserGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<UserGroup> groups) {
        this.groups = groups;
    }

    public String getRemarkName() {
        return remarkName;
    }

    public void setRemarkName(String remarkName) {
        this.remarkName = remarkName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public boolean isFollowing() {
        return isFollowing;
    }

    public void setFollowing(boolean following) {
        isFollowing = following;
    }

    public String getBlogApp() {
        return blogApp;
    }

    public void setBlogApp(String blogApp) {
        this.blogApp = blogApp;
    }

    public int getFansCount() {
        return fansCount;
    }

    public void setFansCount(int fansCount) {
        this.fansCount = fansCount;
    }

    public int getFollowCount() {
        return followCount;
    }

    public void setFollowCount(int followCount) {
        this.followCount = followCount;
    }

    public String getBlogAge() {
        return blogAge;
    }

    public void setBlogAge(String blogAge) {
        this.blogAge = blogAge;
    }

    public String getBlogUrl() {
        return blogUrl;
    }

    public void setBlogUrl(String blogUrl) {
        this.blogUrl = blogUrl;
    }

    public Map<String, String> getProfileMap() {
        return profileMap;
    }

    public void setProfileMap(Map<String, String> profileMap) {
        this.profileMap = profileMap;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getFollowDate() {
        return followDate;
    }

    public void setFollowDate(String followDate) {
        this.followDate = followDate;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getParkId() {
        return parkId;
    }

    public void setParkId(String parkId) {
        this.parkId = parkId;
    }
}
