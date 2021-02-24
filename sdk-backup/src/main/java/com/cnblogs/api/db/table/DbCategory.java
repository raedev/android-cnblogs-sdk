package com.cnblogs.api.db.table;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.annotation.NonNull;

import com.cnblogs.api.CLog;
import com.cnblogs.api.db.IDbCategory;
import com.cnblogs.api.db.model.CategoryDbModel;
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
public class DbCategory extends DbCnblogs implements IDbCategory {

    public DbCategory(CnblogsDBHelper helper) {
        super(helper, CategoryDbModel.class);
    }


    @Override
    protected String tableName() {
        return "Category";
    }

    @NonNull
    @Override
    public Observable<List<CategoryDbModel>> getCategory() {
        return Observable.create(new ObservableOnSubscribe<List<CategoryDbModel>>() {
            @Override
            public void subscribe(ObservableEmitter<List<CategoryDbModel>> emitter) throws Exception {
                try {
                    List<CategoryDbModel> data = new ArrayList<>();
                    SQLiteDatabase database = getReadableDatabase();
                    Cursor cursor = database.query(tableName(), new String[]{"*"}, null, null, null, null, null);
                    while (cursor.moveToNext()) {
                        CategoryDbModel m = new CategoryDbModel();
                        m.setName(readString(cursor, "name"));
                        m.setParentId(readInt(cursor, "parentId"));
                        m.setCategoryId(readInt(cursor, "categoryId"));
                        m.setType(readString(cursor, "type"));
                        m.setOrderNo(readInt(cursor, "orderNo"));
                        m.setSubCategories(readList(cursor, "subCategories", CategoryBean.class));
                        data.add(m);
                    }
                    cursor.close();
                    if (data.isEmpty()) {
                        emitter.onError(new NullPointerException("Local category is empty!"));
                    }
                    emitter.onNext(data);
                    emitter.onComplete();
                } catch (Exception ex) {
                    CLog.e("[DB]读取分类异常", ex);
                    emitter.onError(ex);
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public void saveCategory(List<CategoryDbModel> data) {
        try {
            SQLiteDatabase database = getWritableDatabase();
            database.beginTransaction();
            // 清空原来数据
            database.delete(tableName(), null, null);
            // 插入行数据
            for (CategoryDbModel item : data) {
                ContentValues values = new ContentValues();
                values.put("name", item.getName());
                values.put("orderNo", item.getOrderNo());
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
    public List<CategoryDbModel> getDefaultCategory() {

        List<CategoryDbModel> result = new ArrayList<>();

        CategoryDbModel recommend = new CategoryDbModel();
        recommend.setCategoryId(-2);
        recommend.setName("推荐");
        recommend.setType("Picked");
        recommend.setOrderNo(-3);
        result.add(recommend);

        CategoryDbModel home = new CategoryDbModel();
        home.setCategoryId(808);
        home.setParentId(0);
        home.setName("首页");
        home.setType("SiteHome");
        home.setOrderNo(-4);
        result.add(home);

        return result;
    }

}
