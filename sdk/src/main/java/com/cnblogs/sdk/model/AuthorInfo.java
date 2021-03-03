package com.cnblogs.sdk.model;

/**
 * 作者信息
 * @author RAE
 * @date 2021/03/01
 */
public class AuthorInfo {

    private String url;
    private String avatar;
    private String blogApp;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
}
