package com.cnblogs.sdk.internal.interceptor

import com.cnblogs.sdk.internal.http.HttpHeader.USER_AGENT
import okhttp3.Interceptor
import okhttp3.Response

/**
 * 请求拦截器
 * @author RAE
 * @date 2022/10/04
 * @copyright Copyright (c) https://github.com/raedev All rights reserved.
 */
internal class RequestInterceptor : Interceptor {

    companion object {
        fun create(): Interceptor = RequestInterceptor()
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val build = chain.request().newBuilder()
        // 处理请求头
        build.addHeader("user-agent", USER_AGENT)
        return chain.proceed(build.build())
    }
}