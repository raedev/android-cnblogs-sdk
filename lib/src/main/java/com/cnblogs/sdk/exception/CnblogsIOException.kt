package com.cnblogs.sdk.exception

import java.io.IOException

/**
 * IO异常
 * @author RAE
 * @date 2022/10/04
 * @copyright Copyright (c) https://github.com/raedev All rights reserved.
 */
open class CnblogsIOException(message: String, cause: Throwable?) : IOException(message, cause)