package com.cnblogs.sdk.base;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author RAE
 * @date 2022/02/17
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RUNTIME)
public @interface OpenApi {
}
