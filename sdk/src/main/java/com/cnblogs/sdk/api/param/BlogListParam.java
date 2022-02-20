package com.cnblogs.sdk.api.param;

import androidx.annotation.NonNull;

import com.cnblogs.sdk.internal.http.body.JsonRequestBody;
import com.google.gson.annotations.SerializedName;

/**
 * 博客列表请求参数
 * @author RAE
 * @date 2022/02/17
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class BlogListParam extends JsonRequestBody {
    @SerializedName("CategoryType")
    public String categoryType = "TopSiteCategory";

    @SerializedName("ParentCategoryId")
    public String parentCategoryId = "0";

    @SerializedName("CategoryId")
    @NonNull
    public String categoryId;

    @SerializedName("PageIndex")
    public int pageIndex;

    @SerializedName("ItemListActionName")
    public String actionName = "AggSitePostList";

    @SerializedName("TotalPostCount")
    public int totalPostCount = 4000;

    public BlogListParam(@NonNull String categoryId, int pageIndex) {
        this.categoryId = categoryId;
        this.pageIndex = pageIndex;
    }
}
