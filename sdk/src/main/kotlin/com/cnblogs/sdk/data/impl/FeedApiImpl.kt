package com.cnblogs.sdk.data.impl

import com.cnblogs.sdk.data.FeedApi
import com.cnblogs.sdk.provider.WebApiProvider

/**
 * 信息流接口实现
 * @author RAE
 * @date 2022/10/04
 * @copyright Copyright (c) https://github.com/raedev All rights reserved.
 */
internal class FeedApiImpl(provider: WebApiProvider) : DataApiImpl(provider), FeedApi