package com.cnblogs.sdk.internal.http;

/**
 * 空数据，业务不关注结果，只要是没报错就可以继续，等同于{@link Void}。
 * 通常用在PUT、DELETE 等相关的请求。
 * @author RAE
 * @date 2022/02/20
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public final class Empty {
    private final static Empty VALUE = new Empty();
    private final String name = "empty";

    private Empty() {
    }

    public static Empty value() {
        return VALUE;
    }

}
