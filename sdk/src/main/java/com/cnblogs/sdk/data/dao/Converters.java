package com.cnblogs.sdk.data.dao;

import androidx.room.TypeConverter;

import com.cnblogs.sdk.model.AuthorInfo;
import com.github.raedev.swift.utils.JsonUtils;

/**
 * 数据库类型转换
 * @author RAE
 * @date 2021/03/01
 */
public final class Converters {

    @TypeConverter
    public static String authorToJson(AuthorInfo authorInfo) {
        return JsonUtils.toJson(authorInfo);
    }

    @TypeConverter
    public static AuthorInfo fromAuthorJson(String json) {
        return JsonUtils.fromJson(json, AuthorInfo.class);
    }
}
