package com.cnblogs.sdk.internal.annotation

import kotlin.reflect.KClass

/**
 * HTML解析器注解，用于标注接口采用哪个解析器进行解析
 * @author RAE
 * @date 2022/10/04
 * @copyright Copyright (c) https://github.com/raedev All rights reserved.
 */
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class HTML(
    val value: KClass<*>
)
