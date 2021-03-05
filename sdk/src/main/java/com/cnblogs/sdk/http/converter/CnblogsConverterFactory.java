package com.cnblogs.sdk.http.converter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cnblogs.sdk.internal.CnblogsLogger;
import com.cnblogs.sdk.parser.HtmlParser;
import com.cnblogs.sdk.internal.ObjectCacheHashMap;
import com.cnblogs.sdk.parser.IHtmlParser;

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
    private final ObjectCacheHashMap<String, IHtmlParser<?>> mHtmlParserCache = new ObjectCacheHashMap<>(20);

    private CnblogsConverterFactory() {
        mGsonConverterFactory = GsonConverterFactory.create();
    }

    public static Converter.Factory create() {
        return new CnblogsConverterFactory();
    }

    @NonNull
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(@NonNull Type type, @NonNull Annotation[] annotations, @NonNull Retrofit retrofit) {
        for (Annotation annotation : annotations) {
            if (annotation instanceof HtmlParser) {
                try {
                    HtmlParser parserAnnotation = (HtmlParser) annotation;
                    Class<?> cls = parserAnnotation.value();
                    String name = cls.getName();
                    IHtmlParser<?> htmlParser = mHtmlParserCache.get(name);
                    if (htmlParser == null) {
                        htmlParser = parserAnnotation.value().newInstance();
                        mHtmlParserCache.put(name, htmlParser);
                    }
                    return new HtmlConverter<>(htmlParser);
                } catch (Exception e) {
                    CnblogsLogger.e("初始化HTML解析器异常，请检查接口注解！", e);
                }
            }
        }
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
