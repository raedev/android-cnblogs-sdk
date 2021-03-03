package com.cnblogs.sdk.demo.activity;

import com.cnblogs.sdk.CnblogsSdk;
import com.cnblogs.sdk.data.api.IBlogDataApi;
import com.cnblogs.sdk.demo.ApiListAdapter;
import com.cnblogs.sdk.model.ArticleInfo;
import com.cnblogs.sdk.model.CategoryInfo;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

/**
 * 分类接口
 * @author RAE
 * @date 2021/02/21
 */
public class BlogApiActivity extends ApiListActivity {

    IBlogDataApi mDataApi;

    @Override
    public void onLoadApiItems(ApiListAdapter adapter) {
        mDataApi = CnblogsSdk.getInstance().getDataProvider().getBlogDataApi();
        adapter.addItem("博客列表", "queryBlogs");
    }

    public Observable<List<ArticleInfo>> queryBlogs() {
        CategoryInfo home = new CategoryInfo();
        home.setName("首页");
        home.setCategoryId("808");
        home.setTypeName("SiteHome");
        home.setParentId("0");

        CategoryInfo picked = new CategoryInfo();
        picked.setName("推荐");
        picked.setCategoryId("-2");
        picked.setTypeName("Picked");
        picked.setParentId("0");

        return mDataApi.queryBlogs(picked, 1);
    }

}
