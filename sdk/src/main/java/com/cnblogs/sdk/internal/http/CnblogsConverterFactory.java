package com.cnblogs.sdk.internal.http;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 请求/响应转换器
 * HTML解析器创建的地方
 * @author RAE
 * @date 2021/02/15
 */
public final class CnblogsConverterFactory extends Converter.Factory {

    /**
     * Gson代理工厂
     */
    @NonNull
    private final GsonConverterFactory mGsonConverterFactory;

    private CnblogsConverterFactory() {
        mGsonConverterFactory = GsonConverterFactory.create();
    }

    public static Converter.Factory create() {
        return new CnblogsConverterFactory();
    }

    @NonNull
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(@NonNull Type type, @NonNull Annotation[] annotations, @NonNull Retrofit retrofit) {
        return mGsonConverterFactory.responseBodyConverter(type, annotations, retrofit);
    }

    @NonNull
    @Override
    public Converter<?, RequestBody> requestBodyConverter(@NonNull Type type, @NonNull Annotation[] parameterAnnotations, @NonNull Annotation[] methodAnnotations, @NotNull Retrofit retrofit) {
        return mGsonConverterFactory.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);
    }

    @Nullable
    @Override
    public Converter<?, String> stringConverter(@NonNull Type type, @NonNull Annotation[] annotations, @NonNull Retrofit retrofit) {
        return mGsonConverterFactory.stringConverter(type, annotations, retrofit);
    }
}
