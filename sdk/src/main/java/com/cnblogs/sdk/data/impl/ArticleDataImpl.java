package com.cnblogs.sdk.data.impl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cnblogs.sdk.api.IBlogApi;
import com.cnblogs.sdk.db.ArticleDao;
import com.cnblogs.sdk.db.ArticleHistoryDao;
import com.cnblogs.sdk.exception.CnblogsSdkException;
import com.cnblogs.sdk.exception.CnblogsSdkIOException;
import com.cnblogs.sdk.model.ArticleAndHistory;
import com.cnblogs.sdk.model.ArticleHistoryInfo;
import com.cnblogs.sdk.model.ArticleInfo;
import com.cnblogs.sdk.model.ArticleType;
import com.cnblogs.sdk.model.CategoryInfo;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;

/**
 * 文章数据库接口
 *
 * @author RAE
 * @date 2021/03/01
 */
abstract class ArticleDataImpl extends BaseDataApi implements com.cnblogs.sdk.data.api.ArticleDataApi {
    protected final IBlogApi mBlogApi;
    protected final ArticleDao mArticleDao;
    protected final ArticleHistoryDao mArticleHistoryDao;

    public ArticleDataImpl() {
        mBlogApi = getWebApiProvider().getBlogApi();
        mArticleDao = getDatabase().getArticleDao();
        mArticleHistoryDao = getDatabase().getArticleHistoryDao();
    }


    /**
     * 文章缓存到数据库
     *
     * @param data 数据
     * @return 观察者
     */
    protected ObservableSource<List<ArticleInfo>> cacheToLocal(CategoryInfo category, List<ArticleInfo> data) {
        return Observable.create(emitter -> {
            for (ArticleInfo item : data) {
                item.setCategoryId(category.getCategoryId());
                item.setCreatedTime(System.currentTimeMillis());
                // 从数据库中查询
                ArticleInfo dbItem = mArticleDao.find(item.getPostId());
                // 保留的字段
                if (dbItem != null) {
                    item.setThumbUrl(dbItem.getThumbUrl());
                    item.setCreatedTime(dbItem.getCreatedTime());
                }
            }
            mArticleDao.insertAll(data);
            emitter.onNext(data);
            emitter.onComplete();
        });
    }

    @Override
    public Observable<ArticleHistoryInfo> queryArticleHistory(String postId) {
        return Observable.create(emitter -> {
            ArticleHistoryInfo entity = mArticleHistoryDao.find(getUserId(), postId);
            if (entity == null) {
                emitter.onError(new CnblogsSdkException("没有文章足迹信息"));
                return;
            }
            emitter.onNext(entity);
            emitter.onComplete();
        });
    }

    @Override
    public Observable<ArticleHistoryInfo> saveArticleHistory(@NonNull ArticleHistoryInfo entity) {
        return Observable.create(emitter -> {
            if (isEmpty(entity.getPostId())) {
                emitter.onError(new CnblogsSdkException("必须设置文章ID才能新增足迹信息"));
                emitter.onComplete();
                return;
            }
            // 设置更新时间
            entity.setUpdatedAt(System.currentTimeMillis());
            if (entity.getAutoId() > 0) {
                // 直接更新
                mArticleHistoryDao.update(entity);
            } else {
                ArticleHistoryInfo dbEntity = mArticleHistoryDao.find(getUserId(), entity.getPostId());
                if (dbEntity == null) {
                    // 第一次新增
                    entity.setReadCount(1);
                    entity.setCreatedAt(System.currentTimeMillis());
                    mArticleHistoryDao.insert(entity);
                } else {
                    // 没有ID，数据库已存在，二次更新
                    entity.setAutoId(dbEntity.getAutoId());
                    mArticleHistoryDao.update(entity);
                }
            }
            // 查询更新结果
            ArticleHistoryInfo dbEntity = mArticleHistoryDao.find(getUserId(), entity.getPostId());
            if (dbEntity == null) {
                throw new CnblogsSdkIOException("更新文章足迹失败");
            }
            emitter.onNext(dbEntity);
            emitter.onComplete();
        });
    }

    @Override
    public Observable<List<ArticleAndHistory>> queryHistory(@Nullable ArticleType type, int page) {
        return Observable.create(emitter -> {
            List<ArticleAndHistory> data;
            if (type == null) {
                data = mArticleHistoryDao.queryAll(getUserId(), page);
            } else {
                data = mArticleHistoryDao.queryAll(type.name(), getUserId(), page);
            }
            emitter.onNext(data);
            emitter.onComplete();
        });
    }
}
