package com.cnblogs.api.http.converter.gson;

import com.cnblogs.api.JsonParser;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by rae on 2019-10-22.
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class GsonConverterFactory extends Converter.Factory {

    private final Gson gson = new Gson();

    public static GsonConverterFactory create() {
        return new GsonConverterFactory();
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new GsonRequestBodyConverter<>(gson, adapter);
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        for (Annotation annotation : annotations) {
            if (annotation instanceof JsonParser) {
                TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
                return new GsonResponseBodyConverter<>(gson, adapter, (JsonParser)annotation);
            }
        }
        return null;
    }
}
