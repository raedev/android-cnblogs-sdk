package com.cnblogs.sdk.exception

import okhttp3.Request
import okhttp3.Response

/**
 * 博客园HTTP类型异常，属于CnblogsIOException
 * @author RAE
 * @date 2022/10/04
 * @copyright Copyright (c) https://github.com/raedev All rights reserved.
 */
class CnblogsHttpException constructor(
    /** HTTP状态码 */
    val stateCode: Int,
    /** 错误消息*/
    message: String,
    /** 业务错误代码 */
    val errorCode: Int = 0,
    /** HTTP请求 */
    request: Request? = null,
    /** 请求响应内容 */
    response: Response? = null
) : CnblogsIOException(message, null)