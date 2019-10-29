package com.cnblogs.api.db.table;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.cnblogs.api.CLog;
import com.cnblogs.api.db.IDbCategory;
import com.cnblogs.api.model.CategoryBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by rae on 2019-10-26.
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class TbCategory extends CnblogsTable implements IDbCategory {

    public TbCategory(CnblogsDBHelper helper) {
        super(helper);
    }

    @NonNull
    @Override
    public Observable<List<CategoryBean>> getCategory() {
        return Observable.create(new ObservableOnSubscribe<List<CategoryBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<CategoryBean>> emitter) throws Exception {
                try {
                    List<CategoryBean> data = new ArrayList<>();
                    SQLiteDatabase database = getReadableDatabase();
                    Cursor cursor = database.query(tableName(), new String[]{"*"}, null, null, null, null, null);
                    while (cursor.moveToNext()) {
                        CategoryBean m = new CategoryBean();
                        m.setName(readString(cursor, "name"));
                        m.setParentId(readInt(cursor, "parentId"));
                        m.setCategoryId(readInt(cursor, "categoryId"));
                        m.setType(readString(cursor, "type"));
                        m.setSubCategories(readList(cursor, "subCategories", CategoryBean.class));
                        data.add(m);
                    }
                    cursor.close();
                    if (!data.isEmpty()) {
                        emitter.onNext(data);
                        emitter.onComplete();
                    } else {
                        emitter.onError(new NullPointerException());
                    }
                } catch (Exception ex) {
                    CLog.e("[DB]读取分类异常", ex);
                    emitter.onError(ex);
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public void setCategory(List<CategoryBean> data) {
        try {
            SQLiteDatabase database = getWritableDatabase();
            database.beginTransaction();
            for (CategoryBean item : data) {
                ContentValues values = new ContentValues();
                values.put("name", item.getName());
                values.put("parentId", item.getParentId());
                values.put("categoryId", item.getCategoryId());
                values.put("type", item.getType());
                values.put("subCategories", object2String(item.getSubCategories()));
                database.insert(tableName(), null, values);
            }
            database.setTransactionSuccessful();
            database.endTransaction();
        } catch (Exception ex) {
            CLog.e("[DB]保存分类异常", ex);
        }
    }

    @Override
    protected String tableName() {
        return "category";
    }
}
