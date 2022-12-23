package com.cnblogs.sdk

import android.content.Context
import android.util.Log
import androidx.swift.util.BeanUtils
import androidx.test.platform.app.InstrumentationRegistry
import com.cnblogs.sdk.provider.WebApiProvider
import io.reactivex.rxjava3.core.Observable

/**
 * 接口测试
 * @author RAE
 * @date 2022/09/27
 * @copyright Copyright (c) https://github.com/raedev All rights reserved.
 */
abstract class CnblogsTest {

    protected val context: Context
        get() {
            return InstrumentationRegistry.getInstrumentation().targetContext
        }

    protected val webApiProvider: WebApiProvider
        get() = Cnblogs.webApi

    protected fun <T : Any> Observable<T>.run() {
        Log.d("rae", "测试开始")
        val start = System.currentTimeMillis()
        val data = this.blockingFirst()
        Log.d("rae", "接口结果[${System.currentTimeMillis() - start}ms]: ${BeanUtils.toJson(data)}")
    }
}