package com.cnblogs.sdk.param;

import com.cnblogs.sdk.http.JsonRequestBody;
import com.cnblogs.sdk.model.CategoryInfo;
import com.cnblogs.sdk.parser.HtmlParserUtils;

import org.json.JSONObject;

/**
 * 博客参数
 * @author RAE
 * @date 2021/03/01
 */
public class BlogParam extends JsonRequestBody {

    private final int mPage;
    private final CategoryInfo mCategoryInfo;

    /**
     * @param categoryInfo 分类
     * @param page 页码
     */
    public BlogParam(CategoryInfo categoryInfo, int page) {
        mCategoryInfo = categoryInfo;
        mPage = page;
    }

    @Override
    public String toJson() throws Exception {
        JSONObject object = new JSONObject();
        object.put("CategoryId", mCategoryInfo.getCategoryId());
        object.put("CategoryType", mCategoryInfo.getTypeName());
        object.put("ItemListActionName", "AggSitePostList");
        object.put("PageIndex", Math.max(1, mPage));
        object.put("ParentCategoryId", HtmlParserUtils.parseInt(mCategoryInfo.getParentId()));
        object.put("TotalPostCount", 4000);
        return object.toString();
    }


}
