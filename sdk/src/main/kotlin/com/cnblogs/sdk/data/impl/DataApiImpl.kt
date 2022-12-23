package com.cnblogs.sdk.data.impl

import android.content.Context
import androidx.swift.AndroidSwift
import com.cnblogs.sdk.provider.WebApiProvider

/**
 * 数据接口基类
 * @author RAE
 * @date 2022/10/04
 * @copyright Copyright (c) https://github.com/raedev All rights reserved.
 */
internal abstract class DataApiImpl(protected val provider: WebApiProvider) {

    /** Application Context */
    protected val context: Context
        get() = AndroidSwift.context


}