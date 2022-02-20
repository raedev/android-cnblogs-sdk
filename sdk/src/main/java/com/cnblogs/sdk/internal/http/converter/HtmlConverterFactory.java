package com.cnblogs.sdk.internal.http.converter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cnblogs.sdk.exception.CnblogsException;
import com.cnblogs.sdk.internal.http.annotation.HTML;
import com.cnblogs.sdk.internal.loader.CnblogsClassLoader;
import com.cnblogs.sdk.internal.parser.HtmlParser;
import com.cnblogs.sdk.internal.utils.WeakObjectMap;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Html类型转换工厂
 * @author RAE
 * @date 2022/02/15
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class HtmlConverterFactory extends Converter.Factory {

    private final CnblogsClassLoader mLoader;
    private final WeakObjectMap<String, HtmlParser<?>> mObjectMap = new WeakObjectMap<>(20);

    HtmlConverterFactory(CnblogsClassLoader classLoader) {
        mLoader = classLoader;
    }

    public static HtmlConverterFactory create(CnblogsClassLoader classLoader) {
        return new HtmlConverterFactory(classLoader);
    }

    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(@NonNull Type type, @NonNull Annotation[] annotations, @NonNull Retrofit retrofit) {
        for (Annotation annotation : annotations) {
            if (annotation instanceof HTML) {
                return new HtmlResponseConverter<>(createHtmlParser((HTML) annotation));
            }
        }
        return null;
    }

    /**
     * 创建HTML解析器
     * @throws CnblogsException 创建失败异常
     */
    private <T> T createHtmlParser(HTML annotation) {
        try {
            Class<? extends HtmlParser<?>> cls = annotation.value();
            String name = cls.getSimpleName();
            HtmlParser<?> parser = mObjectMap.get(name);
            // 从Loader中实例化解析器
            if (parser == null) {
                parser = mLoader.newInstance(cls);
                // 缓存对象
                mObjectMap.put(name, parser);
            }
            //noinspection unchecked
            return (T) parser;
        } catch (Exception e) {
            throw new CnblogsException("ResponseBody转换器执行异常！" + e.getMessage(), e);
        }
    }


    private static class HtmlResponseConverter<T> implements Converter<ResponseBody, T> {

        HtmlParser<T> mHtmlParser;

        public HtmlResponseConverter(HtmlParser<T> htmlParser) {
            mHtmlParser = htmlParser;
        }

        @Nullable
        @Override
        public T convert(@NonNull ResponseBody value) throws IOException {
            return mHtmlParser.parseHtml(value.string());
        }
    }
}
