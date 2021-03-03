package com.cnblogs.sdk.data.impl;

import com.cnblogs.sdk.api.IBlogApi;
import com.cnblogs.sdk.db.ArticleDao;
import com.cnblogs.sdk.model.ArticleInfo;
import com.cnblogs.sdk.model.CategoryInfo;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;

/**
 * 文章数据库接口
 * @author RAE
 * @date 2021/03/01
 */
abstract class ArticleDataApi extends BaseDataApi {
    protected final IBlogApi mBlogApi;
    protected final ArticleDao mArticleDao;

    public ArticleDataApi() {
        mBlogApi = getWebApiProvider().getBlogApi();
        mArticleDao = getDatabase().getArticleDao();
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
                item.setRead(dbItem.isRead());
                item.setCreatedTime(dbItem.getCreatedTime());
            }
            mArticleDao.insertAll(data);
        });
    }
}
