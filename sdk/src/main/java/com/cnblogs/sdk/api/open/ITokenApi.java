package com.cnblogs.sdk.api.open;

import com.cnblogs.sdk.model.TokenInfo;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 授权接口
 * @author RAE
 * @date 2021/12/30
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public interface ITokenApi {

    /**
     * Client_Credentials授权
     * @param clientId 客户端ID
     * @param clientSecret 客户端密钥
     * @param grantType 授权类型，默认传client_credentials
     * @return 授权信息
     */
    @POST("/token")
    @FormUrlEncoded
    Observable<TokenInfo> getToken(@Field("client_id") String clientId,
                                   @Field("client_secret") String clientSecret,
                                   @Field("grant_type") String grantType);
}
