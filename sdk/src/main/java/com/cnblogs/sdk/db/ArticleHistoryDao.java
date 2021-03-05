package com.cnblogs.sdk.db;

import androidx.annotation.Nullable;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.cnblogs.sdk.model.ArticleAndHistory;
import com.cnblogs.sdk.model.ArticleHistoryInfo;
import com.cnblogs.sdk.model.ArticleType;

import java.util.List;

/**
 * 足迹表
 */
@Dao
public interface ArticleHistoryDao {

    /**
     * 插入一条文章足迹
     *
     * @param m 文章足迹
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(ArticleHistoryInfo m);

    /**
     * 查找文章足迹
     *
     * @param userId 用户ID
     * @param postId 文章ID
     * @return 足迹
     */
    @Query("SELECT * FROM ARTICLE_HISTORY WHERE USER_ID=:userId AND POST_ID=:postId")
    @Nullable
    ArticleHistoryInfo find(String userId, String postId);

    /**
     * 更新文章足迹
     *
     * @param m 足迹
     */
    @Update
    void update(ArticleHistoryInfo m);

    /**
     * 分页查询足迹
     *
     * @param type   文章类型 {@link ArticleType}
     * @param userId 用户ID
     * @param page   页码
     * @return 足迹列表
     */
    @Transaction
    @Query("SELECT * FROM ARTICLE_HISTORY WHERE ARTICLE_TYPE=:type AND USER_ID=:userId AND READ_COUNT>0  ORDER BY CREATED_AT DESC LIMIT 20 OFFSET :page*20")
    List<ArticleAndHistory> queryAll(String type, String userId, int page);

    /**
     * 分页查询足迹
     *
     * @param userId 用户ID
     * @param page   页码
     * @return 足迹列表
     */
    @Transaction
    @Query("SELECT * FROM ARTICLE_HISTORY WHERE USER_ID=:userId AND READ_COUNT>0  ORDER BY CREATED_AT DESC LIMIT 20 OFFSET :page*20")
    List<ArticleAndHistory> queryAll(String userId, int page);
}
