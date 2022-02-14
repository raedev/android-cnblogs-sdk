package com.cnblogs.sdk.api.open;

import com.cnblogs.sdk.base.CnblogsOpenApiTest;

import org.junit.Test;

/**
 * Token Test
 * @author RAE
 * @date 2021/12/30
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class TokenApiTest extends CnblogsOpenApiTest {

    private ITokenApi mTokenApi;

    @Override
    public void setup() {
        super.setup();
        mTokenApi = mProvider.getTokenApi();
    }

    @Test
    public void testToken() {
        String clientId = "";
        String clientSecret = "";
        String grantType = "";
        runTest(mTokenApi.getToken(clientId, clientSecret, grantType));
    }
}
