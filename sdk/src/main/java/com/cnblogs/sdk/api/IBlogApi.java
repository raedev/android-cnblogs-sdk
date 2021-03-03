package com.cnblogs.sdk.api;

import com.cnblogs.sdk.internal.HtmlParser;
import com.cnblogs.sdk.model.ArticleInfo;
import com.cnblogs.sdk.param.BlogParam;
import com.cnblogs.sdk.parser.blog.BlogListParser;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * 博客接口
 * @author RAE
 * @date 2021/03/01
 */
public interface IBlogApi {

    /**
     * 获取博客列表
     * @param param 博客参数
     * @return 博客列表
     */
    @POST(ApiConstant.API_BLOG_LIST)
    @Headers(ApiConstant.HEADER_XHR)
    @HtmlParser(BlogListParser.class)
    Observable<List<ArticleInfo>> getBlogs(@Body BlogParam param);
}
