package com.cnblogs.sdk.data.impl;

import com.cnblogs.sdk.api.gateway.IUserApi;
import com.cnblogs.sdk.data.api.CategoryDataApi;
import com.cnblogs.sdk.data.dao.CategoryDao;
import com.cnblogs.sdk.exception.CnblogsRuntimeException;
import com.cnblogs.sdk.internal.CnblogsLogger;
import com.cnblogs.sdk.model.CategoryInfo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;

/**
 * 分类接口
 * @author RAE
 * @date 2021/02/26
 * @
 */
public class CategoryDataImpl extends BaseDataApi implements CategoryDataApi {

    private final CategoryDao mDao;
    private final IUserApi mUserApi;

    public CategoryDataImpl() {
        mDao = getDatabase().getCategoryDao();
        mUserApi = getGatewayApiProvider().getUserApi();
    }

    @Override
    public Observable<List<CategoryInfo>> queryHomeCategory() {
        return Observable.create(emitter -> {
            List<CategoryInfo> result = null;
            try {
                // 查询数据库中是否有数据
                result = mDao.queryHome();
                if (result.size() <= 0) {
                    // 没有从接口获取
                    result = mUserApi.getUserCategory().blockingFirst();
                    // 保存到数据库
                    mDao.insertAll(result);
                }
            } catch (Exception e) {
                // 加载默认的分类
                CnblogsLogger.e("加载分类异常！", e);
                result = new ArrayList<>();
                attachDefaultCategory(result);
            }
            // TODO: 更新策略
            emitter.onNext(result);
            emitter.onComplete();
        });
    }

    @Override
    public Observable<CategoryInfo> find(String categoryId) {
        return Observable.create(emitter -> {
            CategoryInfo data = mDao.find(categoryId);
            if (data == null) {
                throw new CnblogsRuntimeException("本地没有该分类：" + categoryId);
            }
            emitter.onNext(data);
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
