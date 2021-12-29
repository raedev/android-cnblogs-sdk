package com.cnblogs.sdk.data.impl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;
import com.cnblogs.sdk.api.IBlogWebApi;
import com.cnblogs.sdk.data.api.IArticleDataApi;
import com.cnblogs.sdk.data.dao.ArticleDao;
import com.cnblogs.sdk.data.dao.ArticleHistoryDao;
import com.cnblogs.sdk.exception.CnblogsRuntimeException;
import com.cnblogs.sdk.exception.CnblogsSdkIOException;
import com.cnblogs.sdk.internal.CnblogsLogger;
import com.cnblogs.sdk.model.ArticleAndHistory;
import com.cnblogs.sdk.model.ArticleHistoryInfo;
import com.cnblogs.sdk.model.ArticleInfo;
import com.cnblogs.sdk.model.ArticleType;
import com.cnblogs.sdk.model.CategoryInfo;
import com.cnblogs.sdk.security.CnblogsEncrypt;
import com.github.raedev.swift.AppSwift;
import com.github.raedev.swift.utils.TextUtils;

import java.io.File;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;

/**
 * 文章数据库接口
 * @author RAE
 * @date 2021/03/01
 */
public abstract class IArticleDataImpl extends BaseDataApi implements IArticleDataApi {
    protected final IBlogWebApi mBlogApi;
    protected final ArticleDao mArticleDao;
    protected final ArticleHistoryDao mArticleHistoryDao;

    public IArticleDataImpl() {
        mBlogApi = getWebApiProvider().getBlogApi();
        mArticleDao = getDatabase().getArticleDao();
        mArticleHistoryDao = getDatabase().getArticleHistoryDao();
    }


    /**
     * 文章缓存到数据库
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
                emitter.onError(new CnblogsRuntimeException("没有文章足迹信息"));
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
                emitter.onError(new CnblogsRuntimeException("必须设置文章ID才能新增足迹信息"));
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

    // region 私有方法

    /**
     * 获取文章缓存的Key
     */
    private String getArticleCacheKey(ArticleInfo article) {
        return CnblogsEncrypt.md5("article_" + article.getPostId());
    }

    /**
     * 检查本地是否已经缓存
     */
    protected boolean hasCache(ArticleInfo article) {
        String postId = article.getPostId();
        if (TextUtils.isEmpty(postId)) {
            return false;
        }
        // 检查缓存文件是否存在
        File cacheDir = AppSwift.getContext().getExternalCacheDir();
        File file = new File(cacheDir, getArticleCacheKey(article));
        return file.exists();
    }

    /**
     * 缓存文章内容
     * @param article 文章信息
     * @param content 文章内容
     */
    protected void cacheArticleContent(ArticleInfo article, String content) {
        // 检查缓存文件是否存在
        File cacheDir = AppSwift.getContext().getExternalCacheDir();
        File file = new File(cacheDir, getArticleCacheKey(article));
        FileUtils.delete(file);
        FileIOUtils.writeFileFromString(file, content);
        CnblogsLogger.d("缓存文章：" + article.getPostId() + " > " + file.getPath());
    }
    // endregion
}
