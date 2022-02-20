package com.cnblogs.sdk.api.param;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okio.BufferedSink;

/**
 * 头像上传参数
 * @author RAE
 * @date 2022/02/20
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class AvatarUploadParam extends RequestBody {

    private final MultipartBody source;

    public AvatarUploadParam(File file) {
        source = new MultipartBody.Builder()
                .setType(Objects.requireNonNull(MediaType.parse("multipart/form-data")))
                .addFormDataPart("avatar", "blob", RequestBody.create(file, MediaType.parse("image/png")))
                .build();
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return source.contentType();
    }

    @Override
    public void writeTo(@NonNull BufferedSink bufferedSink) throws IOException {
        source.writeTo(bufferedSink);
    }
}
