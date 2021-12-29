package com.cnblogs.sdk.model;

import com.google.gson.annotations.SerializedName;

/**
 * 信息流实体
 * @author RAE
 * @date 2021/07/24
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class FeedInfo {

    public String id;

    /**
     * 标题
     */
    public String title;

    /**
     * 图片地址
     */
    @SerializedName("thumb_url")
    public String thumbUrl;

    /**
     * 链接地址
     */
    @SerializedName("link_url")
    public String linkUrl;
}
