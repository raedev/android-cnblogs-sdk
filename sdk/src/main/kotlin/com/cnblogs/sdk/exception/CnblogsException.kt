package com.cnblogs.sdk.exception

/**
 * 博客园SDK异常
 * @author RAE
 * @date 2022/10/04
 * @copyright Copyright (c) https://github.com/raedev All rights reserved.
 */
class CnblogsException : RuntimeException {
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
}