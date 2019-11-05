package com.cnblogs.api.param;

/**
 * Created by rae on 2019-10-11.
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class BlogRecommendParam {
    private int itemId;
    private String itemTitle;

    public BlogRecommendParam(int itemId, String itemTitle) {
        this.itemId = itemId;
        this.itemTitle = itemTitle;
    }


    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }
}
