package com.cnblogs.sdk.internal.http.converter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cnblogs.sdk.internal.http.annotation.JSON;
import com.cnblogs.sdk.internal.loader.CnblogsClassLoader;
import com.cnblogs.sdk.internal.utils.WeakObjectMap;
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
 * Json类型转换器
 * @author RAE
 * @date 2022/02/19
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class JsonConverterFactory extends Converter.Factory {

    private final Gson gson;
    private final CnblogsClassLoader classLoader;
    private final WeakObjectMap<String, com.cnblogs.sdk.internal.handler.JsonHandler> mObjectMap = new WeakObjectMap<>(6);

    JsonConverterFactory(CnblogsClassLoader loader) {
        this(new Gson(), loader);
    }

    JsonConverterFactory(Gson gson, CnblogsClassLoader loader) {
        this.gson = gson;
        this.classLoader = loader;
    }

    public static JsonConverterFactory create(CnblogsClassLoader classLoader) {
        return new JsonConverterFactory(classLoader);
    }

    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(@NonNull Type type, @NonNull Annotation[] annotations, @NonNull Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        com.cnblogs.sdk.internal.handler.JsonHandler handler = null;
        String dataField = null;
        for (Annotation annotation : annotations) {
            if (annotation instanceof JSON) {
                boolean transitive = ((JSON) annotation).transitive();
                if (!transitive) {
                    dataField = ((JSON) annotation).dataField();
                }
                Class<? extends com.cnblogs.sdk.internal.handler.JsonHandler> aClass = ((JSON) annotation).value();
                String name = aClass.getSimpleName();
                handler = mObjectMap.get(name);
                if (handler == null) {
                    handler = classLoader.newInstance(aClass);
                    mObjectMap.put(name, handler);
                }
                break;
            }
        }
        return new JsonResponseBodyConverter<>(gson, type, adapter, dataField, handler);
    }

    @Nullable
    @Override
    public Converter<?, RequestBody> requestBodyConverter(@NonNull Type type, @NonNull Annotation[] parameterAnnotations, @NonNull Annotation[] methodAnnotations, @NonNull Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new JsonRequestBodyConverter<>(gson, adapter);
    }

    @Nullable
    @Override
    public Converter<?, String> stringConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return super.stringConverter(type, annotations, retrofit);
    }
}
