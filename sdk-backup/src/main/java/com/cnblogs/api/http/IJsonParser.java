package com.cnblogs.api.http;

/**
 * Created by rae on 2020-01-08.
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public interface IJsonParser<T> {

    T parse(String json);
}
