package com.cnblogs.sdk.api.open;

import com.cnblogs.sdk.BuildConfig;
import com.cnblogs.sdk.base.BaseApiTest;
import com.cnblogs.sdk.base.CnblogsAndroidRunner;
import com.cnblogs.sdk.base.OpenApi;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Token 测试
 * @author RAE
 * @date 2021/12/30
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
@RunWith(CnblogsAndroidRunner.class)
public class TokenApiTest extends BaseApiTest {

    @OpenApi
    private ITokenApi mTokenApi;

    @Test
    public void testToken() {
        String clientId = BuildConfig.SDK_CLIENT_ID;
        String clientSecret = BuildConfig.SDK_CLIENT_SECRET;
        String grantType = "client_credentials";
        exec(mTokenApi.getToken(clientId, clientSecret, grantType));
    }
}
