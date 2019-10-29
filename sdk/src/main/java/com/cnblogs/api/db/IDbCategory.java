package com.cnblogs.api.db;

import android.support.annotation.NonNull;

import com.cnblogs.api.model.CategoryBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by rae on 2019-10-26.
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public interface IDbCategory {

    @NonNull
    Observable<List<CategoryBean>> getCategory();

    void setCategory(List<CategoryBean> data);
}
