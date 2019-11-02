package com.cnblogs.api.db.table;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.cnblogs.api.BuildConfig;
import com.cnblogs.api.CLog;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by rae on 2019-10-26.
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
@SuppressWarnings("WeakerAccess")
public class CnblogsDBHelper extends SQLiteOpenHelper {

    public CnblogsDBHelper(@Nullable Context context, @Nullable String name) {
        super(context, name, null, BuildConfig.CNBLOGS_DB_VERSION);
    }

    public <E> void checkTableAndCreate(Class<E> cls, String tableName) {
        boolean tableExists = checkTableExists(tableName);
        if (!tableExists) {
            CLog.w("表不存在，重新建表：" + tableName);
            createTable(cls, tableName);
        }
    }

    /**
     * 查询表是否存在
     *
     * @param name 表名
     */
    public boolean checkTableExists(String name) {
        String sql = "SELECT COUNT(*) FROM sqlite_master WHERE TYPE='table' AND name=?";
        int count = queryCount(sql, new String[]{name});
        return count > 0;
    }

    /**
     * 根据实体来创建表
     *
     * @param cls 实体
     */
    public <E> void createTable(Class<E> cls, String tableName) {
        try {
            SQLiteDatabase database = getWritableDatabase();
            StringBuilder sb = new StringBuilder();
            sb.append("CREATE TABLE IF NOT EXISTS ");
            sb.append(tableName);
            sb.append("( ");
            sb.append("\"ID\" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT");
            List<Field> fields = new ArrayList<>(Arrays.asList(cls.getDeclaredFields()));
            Class<?> parentClass = cls.getSuperclass();
            if (parentClass != null) {
                fields.addAll(Arrays.asList(parentClass.getDeclaredFields()));
            }

            for (Field field : fields) {
                String name = field.getName();
                if ("CREATOR".equals(name)) continue;
                if ("serialVersionUID".equals(name)) continue;
                if (name.startsWith("$")) continue;

                sb.append(",\"");
                sb.append(name);
                sb.append("\"");
                sb.append(" TEXT");
            }
            sb.append(")");

            database.execSQL(sb.toString());

            CLog.d("建表SQL语句：" + sb.toString());
        } catch (Exception ex) {
            CLog.e("建表异常：" + ex.getMessage(), ex);
        }

        checkTableExists(tableName);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 初始化操作
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 升级操作
    }

    public int queryCount(String sql, String[] args) {
        Cursor cursor = null;
        try {
            SQLiteDatabase database = getReadableDatabase();
            cursor = database.rawQuery(sql, args);
            if (cursor.moveToNext()) {
                return cursor.getInt(0);
            }
        } catch (Exception e) {
            CLog.e("数据查询COUNT异常！", e);
        } finally {
            if (cursor != null) cursor.close();
        }
        return 0;
    }

}
