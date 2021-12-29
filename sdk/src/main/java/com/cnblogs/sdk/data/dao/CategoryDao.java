package com.cnblogs.sdk.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.cnblogs.sdk.model.CategoryInfo;

import java.util.List;

/**
 * 分类数据库
 * @author RAE
 * @date 2021/02/26
 */
@Dao
public interface CategoryDao {

    /**
     * 新增分类
     * @param items 分类
     * @return 主键Id
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CategoryInfo> items);

    /**
     * 更新分类
     * @param item 分类
     * @return 影响行数
     */
    @Update
    int update(CategoryInfo item);

    /**
     * 所有分类数量
     * @return 数量
     */
    @Query("SELECT COUNT(*) FROM CATEGORY")
    int count();

    /**
     * 查询所有分类
     * @return 分类列表
     */
    @Query("SELECT * FROM CATEGORY ORDER BY orderNo")
    List<CategoryInfo> queryAll();

    /**
     * 查询首页分类
     * @return 分类列表
     */
    @Query("SELECT * FROM CATEGORY WHERE visible=0 AND parentId='0' ORDER BY orderNo")
    List<CategoryInfo> queryHome();

    /**
     * 通过Id查找分类
     * @param categoryId 分类Id
     * @return 分类
     */
    @Query("SELECT * FROM CATEGORY WHERE categoryId=:categoryId")
    CategoryInfo find(String categoryId);
}
