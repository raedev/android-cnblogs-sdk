package com.cnblogs.sdk.exception

/**
 * 博客园接口运行时异常
 * @author RAE
 * @date 2022/10/04
 * @copyright Copyright (c) https://github.com/raedev All rights reserved.
 */
class CnblogsException(message: String, cause: Throwable?) : RuntimeException(message, cause)