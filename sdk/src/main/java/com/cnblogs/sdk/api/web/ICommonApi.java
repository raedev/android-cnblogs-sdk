package com.cnblogs.sdk.api.web;

import com.cnblogs.sdk.internal.http.annotation.HTML;
import com.cnblogs.sdk.internal.parser.common.CategoryParser;
import com.cnblogs.sdk.model.Category;
import com.cnblogs.sdk.model.UploadFileTokenBean;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 博客园公共接口
 * @author RAE
 * @date 2022/02/14
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public interface ICommonApi {

    /**
     * 查询博客分类类别
     * @return Observable
     */
    @GET("https://www.cnblogs.com/aggsite/allsitecategories")
    @HTML(CategoryParser.class)
    Observable<List<Category>> categoryList();

    // region 上传文件相关

    /**
     * 请求上传文件Token
     * @param fileName 文件名称
     * @param size 文件大小
     * @param file 文件
     * @return Observable
     */
    @POST("https://i.cnblogs.com/api/files")
    Observable<UploadFileTokenBean> requestUploadFileToken(@Query("file") String fileName,
                                                           @Query("size") Long size,
                                                           @Body RequestBody file);

    // endregion

}
