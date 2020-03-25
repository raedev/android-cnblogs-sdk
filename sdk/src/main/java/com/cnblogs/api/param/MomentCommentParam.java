package com.cnblogs.api.param;

import com.google.gson.annotations.SerializedName;

/**
 * 闪存回复参数
 * Created by RAE on 2020/03/24.
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class MomentCommentParam {
    @SerializedName("IngId")
    public int ingId;
    @SerializedName("ReplyToUserId")
    public int userId;
    @SerializedName("ParentCommentId")
    public int parentCommentId;
    @SerializedName("Content")
    public String content;
}
