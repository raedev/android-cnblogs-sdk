package com.cnblogs.sdk.model;

import com.google.gson.annotations.SerializedName;

/**
 * 信息流实体
 * @author RAE
 * @date 2021/07/24
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class FeedInfo {

    private String id;

    /**
     * 标题
     */
    private String title;

    /**
     * 图片地址
     */
    @SerializedName("thumb_url")
    private String thumbUrl;

    /**
     * 链接地址
     */
    @SerializedName("link_url")
    private String linkUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }
}
