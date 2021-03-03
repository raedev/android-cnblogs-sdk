package com.cnblogs.sdk.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cnblogs.sdk.model.ArticleInfo;

import java.util.List;

/**
 * 文章数据库
 * @author RAE
 * @date 2021/03/01
 */
@Dao
public interface ArticleDao {

    /**
     * 插入所有，已存在的时候将替换
     * @param data 数据
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ArticleInfo> data);

    /**
     * 根据主键查询文章
     * @param postId Id
     * @return 文章实体
     */
    @Query("SELECT * FROM ARTICLE WHERE postId=:postId")
    ArticleInfo find(String postId);
}
