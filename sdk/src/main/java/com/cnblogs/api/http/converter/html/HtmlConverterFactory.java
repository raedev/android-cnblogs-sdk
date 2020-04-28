package com.cnblogs.api.http.converter.html;

import com.cnblogs.api.http.HtmlParser;
import com.cnblogs.api.http.IHtmlParser;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * 将HTML解析转换为对象
 * Created by rae on 2019-10-21.
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class HtmlConverterFactory extends Converter.Factory {

    public static Converter.Factory create() {
        return new HtmlConverterFactory();
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        IHtmlParser<?> parser = null;

        for (Annotation annotation : annotations) {
            if (annotation instanceof HtmlParser) {
                Class<? extends IHtmlParser> cls = ((HtmlParser) annotation).value();
                try {
                    parser = cls.newInstance();
                } catch (IllegalAccessException | InstantiationException e) {
                    e.printStackTrace();
                }
                break;
            }
        }

        return parser == null ? null : new HtmlConverter<>(parser);
    }

}
