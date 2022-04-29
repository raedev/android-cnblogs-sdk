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

    /**
     * 固定值
     */
    @SerializedName("CategoryType")
    public String categoryType = "TopSiteCategory";

    /**
     * 父节点分类Id
     */
    @SerializedName("ParentCategoryId")
    public String parentCategoryId = "0";

    /**
     * 分类ID
     */
    @SerializedName("CategoryId")
    @NonNull
    public String categoryId;

    /**
     * 页码
     */
    @SerializedName("PageIndex")
    public int pageIndex;

    /**
     * 固定值
     */
    @SerializedName("ItemListActionName")
    public String actionName = "AggSitePostList";

    /**
     * 固定值
     */
    @SerializedName("TotalPostCount")
    public int totalPostCount = 4000;

    public BlogListParam(@NonNull String categoryId, int pageIndex) {
        this.categoryId = categoryId;
        this.pageIndex = pageIndex;
    }
}
