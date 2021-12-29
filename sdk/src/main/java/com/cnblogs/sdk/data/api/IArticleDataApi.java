package com.cnblogs.sdk.data.api;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cnblogs.sdk.model.ArticleAndHistory;
import com.cnblogs.sdk.model.ArticleHistoryInfo;
import com.cnblogs.sdk.model.ArticleType;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

/**
 * 文章列表
 *
 * @author RAE
 * @date 2021/03/04
 */
public interface IArticleDataApi {

    /**
     * 查询文章足迹信息
     *
     * @param postId 文章
     * @return 足迹信息
     */
    Observable<ArticleHistoryInfo> queryArticleHistory(String postId);

    /**
     * 保存文章足迹，没有足迹的时候自动新增
     * 必须提供文章ID，否则抛异常
     *
     * @param entity 足迹信息
     * @return 保存后的足迹信息
     */
    Observable<ArticleHistoryInfo> saveArticleHistory(@NonNull ArticleHistoryInfo entity);

    /**
     * 查询足迹
     *
     * @param type 筛选文章类型，为空查所有类型
     * @param page 分页页码
     * @return 足迹和文章关联列表
     */
    Observable<List<ArticleAndHistory>> queryHistory(@Nullable ArticleType type, int page);
}
