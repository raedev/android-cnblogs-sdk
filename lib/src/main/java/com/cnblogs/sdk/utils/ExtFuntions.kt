/**
 * 扩展函数
 * @author RAE
 * @date 2023/02/07
 * @copyright Copyright (c) https://github.com/raedev All rights reserved.
 */
package com.cnblogs.sdk.utils

import com.cnblogs.sdk.exception.CnblogsException
import com.cnblogs.sdk.exception.CnblogsHttpException
import com.cnblogs.sdk.exception.CnblogsIOException
import okhttp3.Request
import okhttp3.Response

/**
 * throw the CnblogsException
 */
@Throws(CnblogsException::class)
fun cnblogsError(message: String, throwable: Throwable? = null): Nothing =
    throw CnblogsException(message, throwable)

/**
 * throw the CnblogsIOException
 */
@Throws(CnblogsException::class)
fun ioError(message: String, throwable: Throwable? = null): Nothing =
    throw CnblogsIOException(message, throwable)

/**
 * throw the CnblogsHttpException
 */
@Throws(CnblogsHttpException::class)
fun httpError(
    stateCode: Int,
    message: String,
    errorCode: Int = 500,
    request: Request? = null,
    response: Response? = null,
): Nothing = throw CnblogsHttpException(stateCode, message, errorCode, request, response)

