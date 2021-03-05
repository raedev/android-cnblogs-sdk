package com.cnblogs.sdk.demo.activity;

import android.util.Log;

import com.blankj.utilcode.util.FileIOUtils;
import com.cnblogs.sdk.CnblogsSdk;
import com.cnblogs.sdk.api.ICategoryApi;
import com.cnblogs.sdk.data.api.CategoryDataApi;
import com.cnblogs.sdk.demo.ApiListAdapter;
import com.cnblogs.sdk.model.CategoryInfo;
import com.cnblogs.sdk.http.body.SubCategoryRequestBody;
import com.github.raedev.swift.AppSwift;
import com.github.raedev.swift.utils.JsonUtils;

import java.io.File;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;

/**
 * 分类接口
 * @author RAE
 * @date 2021/02/21
 */
public class CategoryApiActivity extends ApiListActivity {

    CategoryDataApi mDataApi;
    ICategoryApi mCategoryApi;

    @Override
    public void onLoadApiItems(ApiListAdapter adapter) {
        mDataApi = CnblogsSdk.getInstance().getDataProvider().getCategoryDataApi();
        mCategoryApi = CnblogsSdk.getInstance().getWebApiProvider().getCategoryApi();
        adapter.addItem("首页分类(动态)", "queryHomeCategory");
        adapter.addItem("首页分类(本地)", "queryHomeCategory2");
    }

    public Observable<List<CategoryInfo>> queryHomeCategory() {
        return mCategoryApi.getHomeCategory().flatMap(data -> {
            SubCategoryRequestBody param = new SubCategoryRequestBody(data);
            return mCategoryApi.getHomeSubCategory(param).map(res -> {
                data.addAll(res);
                return data;
            });
        }).doOnNext(data -> {
            // 写文件
            File file = new File(AppSwift.getApplication().getExternalCacheDir(), "category.json");
            FileIOUtils.writeFileFromString(file, JsonUtils.toJson(data));
            Log.i("rae", "写入文件路径：" + file);
        });
    }

    public Observable<List<CategoryInfo>> queryHomeCategory2() {
        return mDataApi.queryHomeCategory();
    }
}
