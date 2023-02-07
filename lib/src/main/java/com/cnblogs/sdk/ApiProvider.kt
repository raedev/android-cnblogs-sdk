package com.cnblogs.sdk

import android.content.Context
import com.cnblogs.sdk.http.CnblogsConverterFactory
import com.cnblogs.sdk.http.RequestInterceptor
import com.cnblogs.sdk.http.ResponseInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

/**
 * 接口提供者
 * @author RAE
 * @date 2023/02/08
 * @copyright Copyright (c) https://github.com/raedev All rights reserved.
 */
abstract class ApiProvider(val context: Context) {
    protected val retrofit: Retrofit

    init {
        val client = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(180, TimeUnit.SECONDS)
            .addInterceptor(RequestInterceptor.create())
            .addInterceptor(ResponseInterceptor.create())
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl("https://api.cnblogs.com")
            .addConverterFactory(CnblogsConverterFactory.create())
            .client(client)
            .build()
    }
}

class ApiProviderImpl internal constructor(context: Context) : ApiProvider(context) {

}