package com.cnblogs.sdk.api;

import com.cnblogs.sdk.http.body.BlogRequestBody;
import com.cnblogs.sdk.internal.ApiConstant;
import com.cnblogs.sdk.model.ArticleInfo;
import com.cnblogs.sdk.parser.HtmlParser;
import com.cnblogs.sdk.parser.blog.BlogContentParser;
import com.cnblogs.sdk.parser.blog.BlogListParser;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * 博客接口
 * @author RAE
 * @date 2021/03/01
 */
public interface IBlogWebApi {

    /**
     * 获取博客列表
     * @param param 博客参数
     * @return 博客列表
     */
    @POST(ApiConstant.API_BLOG_LIST)
    @Headers(ApiConstant.HEADER_XHR)
    @HtmlParser(BlogListParser.class)
    Observable<List<ArticleInfo>> getBlogs(@Body BlogRequestBody param);

    /**
     * 获取博文
     * @return 内容
     */
    @GET("https://api.cnblogs.com/api/blogposts/{id}/body")
    @HtmlParser(BlogContentParser.class)
    Observable<String> getBlogContent(@Path("id") String id);
}
