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
    private int id;

    /**
     * 回复消息内容
     */
    private String content;

    public ReplyMessageParam(int id, String content) {
        this.id = id;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
