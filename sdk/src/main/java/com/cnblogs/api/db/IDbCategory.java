package com.cnblogs.api.db;

import androidx.annotation.NonNull;

import com.cnblogs.api.db.model.CategoryDbModel;

import java.util.List;

import io.reactivex.Observable;

/**
 * 分类保存
 * Created by rae on 2019-10-26.
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public interface IDbCategory {

    /**
     * 获取分类
     */
    @NonNull
    Observable<List<CategoryDbModel>> getCategory();

    /**
     * 保存分类
     */
    void saveCategory(List<CategoryDbModel> data);

    /**
     * 获取默认的分类
     */
    List<CategoryDbModel> getDefaultCategory();
}
