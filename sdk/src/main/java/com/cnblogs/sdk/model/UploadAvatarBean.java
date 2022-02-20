package com.cnblogs.sdk.model;

import com.google.gson.annotations.SerializedName;

/**
 * 上传头像返回实体
 * @author RAE
 * @date 2022/02/20
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class UploadAvatarBean {
    @SerializedName("IconName")
    private String iconName;
    @SerializedName("AvatarName")
    private String avatarName;

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
