package com.cnblogs.sdk.http;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

/**
 * Form表单参数
 * @author RAE
 * @date 2021/02/20
 */
public class FormParam extends RequestBody {
    private final FormBody.Builder mFormBody;

    public FormParam() {
        mFormBody = new FormBody.Builder();
    }

    public FormParam addQuery(String name, String value) {
        mFormBody.add(name, value);
        return this;
    }

    public FormParam addEncodeQuery(String name, String value) {
        mFormBody.addEncoded(name, value);
        return this;
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return MediaType.parse("application/x-www-form-urlencoded");
    }

    @Override
    public void writeTo(@NotNull BufferedSink bufferedSink) throws IOException {
        onWriteToBody();
        mFormBody.build().writeTo(bufferedSink);
    }

    protected void onWriteToBody() {

    }
}
