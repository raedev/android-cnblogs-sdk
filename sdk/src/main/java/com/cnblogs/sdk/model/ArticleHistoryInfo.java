package com.cnblogs.sdk.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * 文章足迹
 * 当每次点开文章的时候，都会记录到足迹表
 * 足迹表不单记录了足迹信息，还可以记录文章的一些扩展字段，如：已读状态、收藏状态等等
 */
@SuppressWarnings("unused")
@Entity(tableName = "ARTICLE_HISTORY")
public class ArticleHistoryInfo {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "AUTO_ID")
    private long autoId;

    /**
     * 用户Id
     */
    @ColumnInfo(name = "USER_ID", index = true)
    private String userId;

    /**
     * 文章Id
     */
    @ColumnInfo(name = "POST_ID", index = true)
    private String postId;

    /**
     * 文章类型 {@link ArticleType}
     */
    @ColumnInfo(name = "ARTICLE_TYPE", defaultValue = "BLOG")
    private String articleType;

    /**
     * 是否收藏
     */
    @ColumnInfo(name = "IS_FAVORITE")
    private boolean isFavorite;

    /**
     * 是否点赞推荐
     */
    @ColumnInfo(name = "IS_LIKE")
    private boolean isLike;


    /**
     * 足迹创建时间
     */
    @ColumnInfo(name = "CREATED_AT")
    private long createdAt;

    /**
     * 阅读次数
     */
    @ColumnInfo(name = "READ_COUNT", defaultValue = "1")
    private int readCount;

    /**
     * 足迹更新时间
     */
    @ColumnInfo(name = "UPDATED_AT")
    private long updatedAt;

    public long getAutoId() {
        return autoId;
    }

    public void setAutoId(long autoId) {
        this.autoId = autoId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getArticleType() {
        return articleType;
    }

    public void setArticleType(String articleType) {
        this.articleType = articleType;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public int getReadCount() {
        return readCount;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }
}
