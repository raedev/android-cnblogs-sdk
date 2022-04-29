package com.cnblogs.sdk.api.web;

import android.os.Environment;

import com.cnblogs.sdk.api.param.AvatarUploadParam;
import com.cnblogs.sdk.base.BaseApiTest;
import com.cnblogs.sdk.base.CnblogsAndroidRunner;
import com.cnblogs.sdk.base.WebApi;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.Map;

/**
 * 用户接口测试
 * @author RAE
 * @date 2022/02/15
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
@RunWith(CnblogsAndroidRunner.class)
public class UserApiTest extends BaseApiTest {

    @WebApi
    private IUserApi mApi;

    /**
     * 测试用户信息
     */
    @Test
    public void testUserInfo() {
        exec(mApi.userInfo());
    }

    /**
     * 测试个人信息
     */
    @Test
    public void testProfile() {
        exec(mApi.profile("chenrui7"));
        exec(mApi.profile("tianqing"));
        exec(mApi.profile("lukelmouse"));
    }

    /**
     * 测试账号信息
     */
    @Test
    public void testAccount() {
        exec(mApi.accountInfo());
    }


    /**
     * 测试更新头像
     */
    @Test
    public void testUploadAvatar() {
        File file = new File(Environment.getExternalStorageDirectory(), "avatar.png");
        exec(mApi.uploadAvatar(new AvatarUploadParam(file)));
    }

    @Test
    public void testCheckDisplayName() {
        // 测试一个不存在的昵称
        exec(mApi.checkDisplayName("测试3点14159"));
        // 测试一个存在的昵称
        exec(mApi.checkDisplayName("金角大王"));
    }

    @Test
    public void testUpdateDisplayName() {
        String name = "金角大王";
        // 先请求用户信息满足Cookie前置条件
        exec(mApi.accountInfo());
        exec(mApi.updateDisplayName(name));
    }

    @Test
    public void testFollows() {
        exec(mApi.followers("alex3714", 1));
    }

    @Test
    public void testFans() {
        exec(mApi.fans("alex3714", 2));
    }

    @Test
    public void testFollow() {
        exec(mApi.follow("5b861abe-ca5e-e611-9fc1-ac853d9f53cc", "测试备注上去的"));
        exec(mApi.unfollow("5b861abe-ca5e-e611-9fc1-ac853d9f53cc", false));
    }


    @Test
    public void testIsFollow() {
        exec(mApi.isFollow("5b861abe-ca5e-e611-9fc1-ac853d9f53cc"));
        exec(mApi.isFollow("9f65360b-63cf-dd11-9e4d-001cf0cd104b"));
    }

    @Test
    public void testIntroFields() {
        Map<String, String> map = exec(mApi.introFields());
        map.put("Introduction", "技术改变世界-" + System.currentTimeMillis());
        exec(mApi.updateIntro(map));
    }
}
