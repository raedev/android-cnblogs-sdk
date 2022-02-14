package com.cnblogs.sdk.base;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.cnblogs.sdk.CnblogsFactory;
import com.cnblogs.sdk.provider.OpenApiProvider;

import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class CnblogsOpenApiTest extends BaseApiTest {

    protected OpenApiProvider mProvider;

    @Override
    public void setup() {
        super.setup();
        mProvider = CnblogsFactory.getInstance().getOpenApiProvider();
    }

}