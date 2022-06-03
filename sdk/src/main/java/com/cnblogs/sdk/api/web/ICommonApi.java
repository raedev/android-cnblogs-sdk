package com.cnblogs.sdk.api.web;

import com.cnblogs.sdk.internal.http.Empty;
import com.cnblogs.sdk.internal.http.HttpHeader;
import com.cnblogs.sdk.internal.http.annotation.HTML;
import com.cnblogs.sdk.internal.parser.common.CategoryParser;
import com.cnblogs.sdk.model.Category;
import com.cnblogs.sdk.model.UploadFileTokenBean;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
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
     * 【1/3】准备上传，上传先请求一下这个接口自动获取cookie验证信息
     * @return 空数据
     */
    @GET("https://i.cnblogs.com/files")
    Observable<Empty> prepareUploadFile();

    /**
     * 【2/3】请求上传文件Token
     * @param fileName 文件名称
     * @param size 文件大小
     * @param file 文件
     * @return Observable
     */
    @POST("https://i.cnblogs.com/api/files")
    Observable<UploadFileTokenBean> requestUploadFileToken(@Query("file") String fileName,
                                                           @Query("size") Long size);

    /**
     * 【3/3】上传文件
     * @param token 第二步获取的Token
     * @param contentType 内容类型，比如image/bmp
     * @param file 文件
     * @return Observable
     */
    @POST("https://upload.cnblogs.com/api/v1/files/")
    @Headers({HttpHeader.ACCEPT_TYPE_JSON})
    Completable uploadFile(@Query("token") String token,
                           @Header("content-type") String contentType,
                           @Body RequestBody file);

    /**
     * 删除文件
     * @param fileName 文件名
     * @return Observable
     */
    @DELETE("https://i.cnblogs.com/api/files/{fileName}")
    @Headers({HttpHeader.ACCEPT_TYPE_JSON})
    Completable deleteFile(@Path("fileName") String fileName);

    // endregion

}
