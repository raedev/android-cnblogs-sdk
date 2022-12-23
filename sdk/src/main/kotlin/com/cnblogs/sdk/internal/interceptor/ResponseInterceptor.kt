package com.cnblogs.sdk.internal.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/**
 * 响应拦截器
 * @author RAE
 * @date 2022/10/04
 * @copyright Copyright (c) https://github.com/raedev All rights reserved.
 */
internal class ResponseInterceptor : Interceptor {

    companion object {
        fun create(): Interceptor = ResponseInterceptor()
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request())
    }
}