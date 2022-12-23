@file:Suppress("UNCHECKED_CAST")

package com.cnblogs.sdk.provider

import androidx.swift.sword.provider.RetrofitFactory
import com.cnblogs.sdk.api.IUserApi
import com.cnblogs.sdk.internal.http.ApiPath
import com.cnblogs.sdk.internal.interceptor.RequestInterceptor
import com.cnblogs.sdk.internal.interceptor.ResponseInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty
import kotlin.reflect.jvm.javaType

/**
 * 网页接口提供者，基于HTML解析获取数据，无需官方授权，完全模拟网页请求实现。
 * @author RAE
 * @date 2022/09/18
 * @copyright Copyright (c) https://github.com/raedev All rights reserved.
 */
internal class WebApiProvider {

    /** Retrofit 实例 */
    private val retrofit: Retrofit = RetrofitFactory.newBuilder(ApiPath.BASE_URL)
        .connectTimeout(20).readTimeout(20).writeTimeout(60)
        .addInterceptor(RequestInterceptor.create())
        .addInterceptor(ResponseInterceptor.create())
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    /** 创建接口 */
    private fun <T> api(): ReadOnlyProperty<WebApiProvider, T> = ApiImpl()

    /** 创建接口实现 */
    private class ApiImpl<T> : ReadOnlyProperty<WebApiProvider, T> {
        private var instance: T? = null
        override fun getValue(thisRef: WebApiProvider, property: KProperty<*>): T {
            return instance ?: let {
                val clazz = property.returnType.javaType as Class<T>
                thisRef.retrofit.create(clazz).also { instance = it }
            }
        }
    }

    /** 获取用户接口 */
    val userApi: IUserApi by api()

}