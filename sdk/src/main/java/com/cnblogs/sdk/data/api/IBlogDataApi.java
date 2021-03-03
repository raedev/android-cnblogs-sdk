package com.cnblogs.sdk.data.api;

import com.cnblogs.sdk.model.ArticleInfo;
import com.cnblogs.sdk.model.CategoryInfo;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

/**
 * 博客数据接口
 * @author RAE
 * @date 2021/03/01
 */
public interface IBlogDataApi {

    /**
     * 查询博客列表
     * @param categoryInfo 所属分类
     * @param page 页码
     * @return 博客列表
     */
    Observable<List<ArticleInfo>> queryBlogs(CategoryInfo categoryInfo, int page);
}
