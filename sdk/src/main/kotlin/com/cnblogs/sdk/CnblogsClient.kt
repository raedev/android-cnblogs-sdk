package com.cnblogs.sdk

import android.content.Context
import androidx.swift.AndroidSwift
import androidx.swift.session.SessionDelegate
import androidx.swift.session.SessionManager
import com.cnblogs.sdk.model.UserInfo
import com.cnblogs.sdk.provider.DataProvider

/**
 * 博客园客户端SDK入口
 * @author RAE
 * @date 2022/10/18
 * @copyright Copyright (c) https://github.com/raedev All rights reserved.
 */
class CnblogsClient {

    private object Inner {
        // 饿汉单例
        val client: CnblogsClient = CnblogsClient()
    }

    companion object {
        fun getInstance() = Inner.client
    }

    /** application Context */
    private val context: Context
        get() = AndroidSwift.context

    /**
     * 数据接口提供者，提供的接口处理了基础的业务逻辑，包括网络请求、本地数据库访问。
     */
    val dataApi: DataProvider by lazy { DataProvider() }

    /** 用户管理器 */
    val userManager: SessionDelegate by lazy {
        SessionManager.Builder(context, UserInfo::class.java).build()
    }
}