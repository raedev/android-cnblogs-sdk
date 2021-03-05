package com.cnblogs.sdk.data.impl;

import com.blankj.utilcode.util.ResourceUtils;
import com.cnblogs.sdk.data.api.CategoryDataApi;
import com.cnblogs.sdk.db.CategoryDao;
import com.cnblogs.sdk.internal.CnblogsLogger;
import com.cnblogs.sdk.model.CategoryInfo;
import com.github.raedev.swift.utils.ArraysUtils;
import com.github.raedev.swift.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;

/**
 * 分类接口
 * @author RAE
 * @date 2021/02/26
 */
class CategoryDataImpl extends BaseDataApi implements CategoryDataApi {

    private final CategoryDao mCategoryDao;

    public CategoryDataImpl() {
        mCategoryDao = getDatabase().getCategoryDao();
    }

    @Override
    public Observable<List<CategoryInfo>> queryHomeCategory() {
        return Observable.create(emitter -> {
            List<CategoryInfo> result;
            // 查询数据库中是否有数据，如果没有从Json中读取并保持到数据库
            if (mCategoryDao.count() <= 0) {
                // 从Json中初始化数据
                String json = ResourceUtils.readAssets2String("category.json");
                List<CategoryInfo> categoryInfoList = JsonUtils.toList(json, CategoryInfo.class);
                attachDefaultCategory(categoryInfoList);
                mCategoryDao.insertAll(categoryInfoList);
                CnblogsLogger.d("分类入库：" + categoryInfoList.size());
            }
            // 查询数据库分类
            result = mCategoryDao.queryHome();
            if (ArraysUtils.isEmpty(result)) {
                result = new ArrayList<>();
            }
            emitter.onNext(result);
            emitter.onComplete();
        });
    }

    /**
     * 默认分类
     * @param categoryInfoList 分类列表
     */
    private void attachDefaultCategory(List<CategoryInfo> categoryInfoList) {
        // 首页默认分类
        CategoryInfo home = new CategoryInfo();
        home.setName("首页");
        home.setCategoryId("808");
        home.setTypeName("SiteHome");
        home.setParentId("0");

        // 首页精华
        CategoryInfo picked = new CategoryInfo();
        picked.setName("推荐");
        picked.setCategoryId("-2");
        picked.setTypeName("Picked");
        picked.setParentId("0");

        // 候选
        CategoryInfo candidate = new CategoryInfo();
        candidate.setName("候选区");
        candidate.setCategoryId("108697");
        candidate.setTypeName("HomeCandidate");
        candidate.setParentId("0");

        categoryInfoList.add(0, home);
        categoryInfoList.add(1, picked);
        categoryInfoList.add(2, candidate);
    }
}
