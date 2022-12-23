package com.cnblogs.sdk.provider

import com.cnblogs.sdk.data.FeedApi
import com.cnblogs.sdk.data.UserApi
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty
import kotlin.reflect.jvm.javaType

/**
 * 数据接口提供者，接口工厂模式。
 * @author RAE
 * @date 2022/10/04
 * @copyright Copyright (c) https://github.com/raedev All rights reserved.
 */
class DataProvider {

    /** 网页接口 */
    private val webApi: WebApiProvider by lazy { WebApiProvider() }

    /** 创建接口 */
    private fun <T> impl(): ReadOnlyProperty<DataProvider, T> = DataImpl()

    /** 创建接口实现 */
    private class DataImpl<T> : ReadOnlyProperty<DataProvider, T> {
        private var instance: T? = null
        override fun getValue(thisRef: DataProvider, property: KProperty<*>): T {
            return instance ?: let {
                val clazz = property.returnType.javaType as Class<*>
                val impl =
                    Class.forName("com.cnblogs.sdk.data.impl.${clazz.simpleName}Impl") as Class<T>
                impl.getConstructor(WebApiProvider::class.java).newInstance(thisRef.webApi)
            }
        }
    }


    /**
     * 获取用户接口
     */
    val userApi by impl<UserApi>()

    /**
     * 信息流接口
     */
    val feedApi by impl<FeedApi>()

}