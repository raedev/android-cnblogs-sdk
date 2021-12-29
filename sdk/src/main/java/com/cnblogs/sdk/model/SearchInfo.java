package com.cnblogs.sdk.model;

import com.google.gson.annotations.SerializedName;

/**
 * 搜素
 * @author RAE
 * @date 2021/07/24
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class SearchInfo {
    /**
     * 搜索词
     */
    public String word;

    /**
     * 今日搜索次数
     */
    @SerializedName("day_total")
    public int dayTotal;
    /**
     * 本周搜索次数
     */
    @SerializedName("week_total")
    public int weekTotal;
    /**
     * 本月搜索次数
     */
    @SerializedName("month_total")
    public int monthTotal;


}
