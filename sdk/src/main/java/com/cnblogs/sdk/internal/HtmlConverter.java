package com.cnblogs.sdk.internal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cnblogs.sdk.parser.IHtmlParser;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * HTML转换器
 * @author RAE
 * @date 2021/02/20
 */
public class HtmlConverter<T> implements Converter<ResponseBody, T> {

    private final IHtmlParser<T> mHtmlParser;

    public HtmlConverter(IHtmlParser<T> htmlParser) {
        mHtmlParser = htmlParser;
    }

    @Nullable
    @Override
    public T convert(@NonNull ResponseBody value) throws IOException {
        return mHtmlParser.parseHtml(value.string());
    }
}
