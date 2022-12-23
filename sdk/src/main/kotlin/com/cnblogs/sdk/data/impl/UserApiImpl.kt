package com.cnblogs.sdk.data.impl

import com.cnblogs.sdk.data.UserApi
import com.cnblogs.sdk.provider.WebApiProvider

/**
 * 用户接口实现
 * @author RAE
 * @date 2022/10/04
 * @copyright Copyright (c) https://github.com/raedev All rights reserved.
 */
internal class UserApiImpl(provider: WebApiProvider) : DataApiImpl(provider), UserApi