package com.cnblogs.api.param;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rae on 2019-10-08.
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class BlogListParam {

    @SerializedName("PageIndex")
    private int page;
    @SerializedName("CategoryType")
    private String type;
    // 父ID
    @SerializedName("ParentCategoryId")
    private int parentId;
    // 分类ID
    @SerializedName("CategoryId")
    private int categoryId;
    // 默认为 AggSitePostList
    @SerializedName("ItemListActionName")
    private String actionName;

    public BlogListParam(String actionName, String type, int parentId, int categoryId, int page) {
        this.page = page;
        this.type = type;
        this.parentId = parentId;
        this.categoryId = categoryId;
        this.actionName = actionName;
    }

    public BlogListParam(int page, String type, int parentId, int categoryId) {
        this.page = page;
        this.type = type;
        this.parentId = parentId;
        this.categoryId = categoryId;
        this.actionName = "AggSitePostList";
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
