package com.cnblogs.sdk.data.api;

import com.cnblogs.sdk.model.CategoryInfo;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

/**
 * 分类接口
 * @author RAE
 * @date 2021/02/25
 */
public interface ICategoryDataApi {

    /**
     * 获取首页分类接口
     * @return 分类
     */
    Observable<List<CategoryInfo>> queryHomeCategory();
}
