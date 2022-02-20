package com.cnblogs.sdk.api.web;

import android.os.Environment;

import com.cnblogs.sdk.base.BaseApiTest;
import com.cnblogs.sdk.base.CnblogsAndroidRunner;
import com.cnblogs.sdk.base.WebApi;
import com.cnblogs.sdk.internal.utils.CnblogsUtils;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

import okhttp3.RequestBody;

/**
 * 公共接口测试
 * @author RAE
 * @date 2022/02/15
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
@RunWith(CnblogsAndroidRunner.class)
public class CommonApiTest extends BaseApiTest {

    @WebApi
    private ICommonApi mApi;

    /**
     * 测试分类列表
     */
    @Test
    public void testCategoryList() {
        exec(mApi.categoryList());
    }

    @Test
    public void testUploadFile() {
        File file = new File(Environment.getExternalStorageDirectory(), "11.bmp");
        RequestBody body = CnblogsUtils.createFileBody("file", file);
        exec(mApi.requestUploadFileToken(file.getName(), file.length(), body));
    }
}
