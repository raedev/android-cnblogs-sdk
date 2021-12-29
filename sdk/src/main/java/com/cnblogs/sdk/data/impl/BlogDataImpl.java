package com.cnblogs.sdk.data.impl;

import com.cnblogs.sdk.data.api.IBlogDataApi;
import com.cnblogs.sdk.http.body.BlogRequestBody;
import com.cnblogs.sdk.internal.CnblogsLogger;
import com.cnblogs.sdk.model.ArticleInfo;
import com.cnblogs.sdk.model.CategoryInfo;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * 博客数据接口实现
 * @author RAE
 * @date 2021/03/01
 */
@SuppressWarnings("AlibabaThreadPoolCreation")
public class BlogDataImpl extends IArticleDataImpl implements IBlogDataApi {

    /**
     * 博文请求线程池
     */
    private final ExecutorService mExecutor = Executors.newFixedThreadPool(6);


    @Override
    public Observable<List<ArticleInfo>> queryBlogs(CategoryInfo categoryInfo, int page) {
        return mBlogApi
                .getBlogs(new BlogRequestBody(categoryInfo, page))
                .flatMap(m -> Observable.fromIterable(m).subscribeOn(Schedulers.from(mExecutor)))
                // 请求博客内容
                .map(m -> {
                    // 检查缓存
                    if (hasCache(m)) {
                        return m;
                    }
                    try {
                        CnblogsLogger.d("请求博文：" + m.getPostId() + " > " + m.getTitle());
                        String content = mBlogApi.getBlogContent(m.getPostId()).blockingFirst();
                        // 缓存文章
                        cacheArticleContent(m, content);
                    } catch (Exception e) {
                        CnblogsLogger.e("请求博文异常发生异常：" + m.getTitle(), e);
                    }
                    return m;
                })
                .toList()
                .toObservable();
    }

}
