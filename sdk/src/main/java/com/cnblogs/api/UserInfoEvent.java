package com.cnblogs.api;

/**
 * 退出登录事件
 * Created by rae on 2020-01-03.
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class UserInfoEvent {

    public final static int TYPE_LOGOUT = 1;
    public final static int TYPE_UPDATE_INFO = 2;


    private int type;

    private long timestamp;

    public UserInfoEvent(int type) {
        this.type = type;
        this.timestamp = System.currentTimeMillis();
    }

    public int getType() {
        return type;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
