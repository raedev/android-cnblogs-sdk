package com.cnblogs.sdk.api.web;

import android.os.Environment;

import com.cnblogs.sdk.base.BaseApiTest;
import com.cnblogs.sdk.base.CnblogsAndroidRunner;
import com.cnblogs.sdk.base.WebApi;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileNotFoundException;

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
    public void testUploadFile() throws Exception {
        // 1.预先请求
        exec(mApi.prepareUploadFile());

        // 2.获取上传文件的Token
        File file = new File(Environment.getExternalStorageDirectory(), "11.bmp");
        // 删除文件
        exec(mApi.deleteFile(file.getName()));

        if (!file.exists()) {
            throw new FileNotFoundException("测试文件不存在:" + file.getPath());
        }
        String token = exec(mApi.requestUploadFileToken(file.getName(), file.length())).getUploadToken();

        // 3.调用上传
        RequestBody body = RequestBody.create(file, null);
//        exec(mApi.uploadFile(token, "image/bmp", body));

    }
}
