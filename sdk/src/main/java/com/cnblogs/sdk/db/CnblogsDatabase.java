package com.cnblogs.sdk.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.cnblogs.sdk.model.ArticleInfo;
import com.cnblogs.sdk.model.CategoryInfo;

/**
 * 本地数据库
 * @author RAE
 * @date 2021/02/26
 */
@Database(entities = {
        CategoryInfo.class,
        ArticleInfo.class
}, exportSchema = false, version = 1)
@TypeConverters({Converters.class})
public abstract class CnblogsDatabase extends RoomDatabase {

    /**
     * 获取分类数据库
     * @return 分类接口
     */
    public abstract CategoryDao getCategoryDao();

    /**
     * 获取文章数据库
     * @return 数据库接口
     */
    public abstract ArticleDao getArticleDao();
}