package com.cnblogs.api.db.table;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cnblogs.api.model.CategoryBean;
import com.cnblogs.api.parser.ParseUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by rae on 2019-10-26.
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
@SuppressWarnings("WeakerAccess")
public abstract class CnblogsTable {

    protected final CnblogsDBHelper mHelper;
    private final Gson mGson = new Gson();

    public CnblogsTable(CnblogsDBHelper helper) {
        mHelper = helper;
        // 检查表
        if (!mHelper.checkTableExists(tableName())) {
            mHelper.createTable(CategoryBean.class, tableName());
        }
    }

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

    protected <E> List<E> readList(Cursor cursor, String name, Class<E> cls) {
        int index = cursor.getColumnIndex(name);
        if (index < 0) return null;
        String text = cursor.getString(index);
        return mGson.fromJson(text, new TypeToken<E>() {
        }.getType());
    }

    protected String readString(Cursor cursor, String name) {
        int index = cursor.getColumnIndex(name);
        if (index < 0) return null;
        return cursor.getString(index);
    }

    protected abstract String tableName();


}
