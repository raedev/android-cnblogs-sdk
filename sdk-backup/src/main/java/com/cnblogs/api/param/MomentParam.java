package com.cnblogs.api.param;

import com.google.gson.annotations.SerializedName;

/**
 * 闪存参数
 * Created by RAE on 2020/03/24.
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class MomentParam {
    /**
     * 是否公开发布
     */
    @SerializedName("publicFlag")
    public int publicFlag = 1;
    /**
     * 发布内容
     */
    public String content;
}
