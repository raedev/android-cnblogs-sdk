package com.cnblogs.sdk.model;

/**
 * 文章统计数量
 * @author RAE
 * @date 2022/03/01
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class PostStatInfo {
    /**
     * 文章Id
     */
    private int postId;
    /**
     * 踩数量
     */
    private int buryCount;
    /**
     * 推荐数量
     */
    private int diggCount;
    /**
     * 评论数量
     */
    private int feedbackCount;
    /**
     * 查看数量
     */
    private int viewCount;

    public int getBuryCount() {
        return buryCount;
    }

    public void setBuryCount(int buryCount) {
        this.buryCount = buryCount;
    }

    public int getDiggCount() {
        return diggCount;
    }

    public void setDiggCount(int diggCount) {
        this.diggCount = diggCount;
    }

    public int getFeedbackCount() {
        return feedbackCount;
    }

    public void setFeedbackCount(int feedbackCount) {
        this.feedbackCount = feedbackCount;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }
}
