package com.cnblogs.sdk

import com.cnblogs.sdk.api.IUserApi
import org.junit.Test

/**
 * 用户接口测试
 * @author RAE
 * @date 2022/09/27
 * @copyright Copyright (c) https://github.com/raedev All rights reserved.
 */
class UserApiTest : CnblogsTest() {

    val api: IUserApi
        get() = webApiProvider.userApi

    @Test
    fun testUserInfo() {
        api.getUserInfo().run()
    }

}