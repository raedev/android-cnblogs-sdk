package com.cnblogs.api.db.table;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.cnblogs.api.parser.ParseUtils;
import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 数据库访问类
 * Created by rae on 2019-10-26.
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
@SuppressWarnings("WeakerAccess")
public abstract class DbCnblogs {

    protected final CnblogsDBHelper mHelper;
    private final Gson mGson = new Gson();


    public <T> DbCnblogs(CnblogsDBHelper helper, Class<T> cls) {
        mHelper = helper;
        // 检查表
        mHelper.checkTableAndCreate(cls, tableName());
    }

    /**
     * 表名
     */
    protected abstract String tableName();

    protected SQLiteDatabase getReadableDatabase() {
        return mHelper.getReadableDatabase();
    }

    protected SQLiteDatabase getWritableDatabase() {
        return mHelper.getWritableDatabase();
    }

    protected <E> String object2String(E data) {
        return mGson.toJson(data);
    }

    protected int readInt(Cursor cursor, String name) {
        int index = cursor.getColumnIndex(name);
        if (index < 0) return 0;
        String text = cursor.getString(index);
        return ParseUtils.parseInt(text);
    }

    protected <E> List<E> readList(Cursor cursor, String name, final Class<E> cls) {
        int index = cursor.getColumnIndex(name);
        if (index < 0 || cursor.isNull(index)) return null;
        String text = cursor.getString(index);
        return mGson.fromJson(text, new ListParameterizedType(cls));
    }

    protected String readString(Cursor cursor, String name) {
        int index = cursor.getColumnIndex(name);
        if (index < 0 || cursor.isNull(index)) return null;
        return cursor.getString(index);
    }


    private class ListParameterizedType implements ParameterizedType {
        private Type cls;

        public ListParameterizedType(Type cls) {
            this.cls = cls;
        }

        @Override
        @NonNull
        public Type[] getActualTypeArguments() {
            return new Type[]{cls};
        }

        @Override
        @NonNull
        public Type getRawType() {
            return List.class;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }
    }

}
