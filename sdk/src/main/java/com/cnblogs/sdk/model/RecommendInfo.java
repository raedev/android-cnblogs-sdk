package com.cnblogs.sdk.model;

/**
 * 推荐博客
 * @author RAE
 * @date 2022/03/01
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class RecommendInfo {
    private String itemId;
    private String title;
    private String content;
    private String url;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
