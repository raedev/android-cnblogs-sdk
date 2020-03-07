package com.cnblogs.api.param;

/**
 * 回复消息参数
 * Created by rae on 2020-02-18.
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class ReplyMessageParam {
    /**
     * 消息ID
     */
    private String id;

    /**
     * 回复消息内容
     */
    private String content;

    public ReplyMessageParam(String id, String content) {
        this.id = id;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
