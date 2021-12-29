//package com.cnblogs.sdk.api;
//
//import com.cnblogs.sdk.data.api.CategoryDataApi;
//import com.cnblogs.sdk.internal.ApiConstant;
//import com.cnblogs.sdk.parser.HtmlParser;
//import com.cnblogs.sdk.model.CategoryInfo;
//import com.cnblogs.sdk.http.body.SubCategoryRequestBody;
//import com.cnblogs.sdk.parser.category.CategoryParser;
//
//import java.util.List;
//
//import io.reactivex.rxjava3.core.Observable;
//import retrofit2.http.Body;
//import retrofit2.http.GET;
//import retrofit2.http.POST;
//
///**
// * 分类接口
// * @author RAE
// * @date 2021/02/26
// */
//public interface ICategoryWebApi {
//
//    /**
//     * 获取首页分类
//     * @return 分类列表
//     */
//    @GET(ApiConstant.API_CATEGORY_HOME)
//    @HtmlParser(CategoryParser.HomeCategoryParser.class)
//    Observable<List<CategoryInfo>> getHomeCategory();
//
//    /**
//     * 首页二级分类【尽可能只调用一次】
//     * @param param 参数
//     * @return 所有二级分类列表
//     * @see CategoryDataApi#queryHomeCategory() App调用推荐使用该接口
//     * @deprecated <p>该接口内部会发多次请求去解析每个二级分类，所以APP只需要调用一次保存到本地即可；</p>
//     * <p>鉴于分类变动不会很频繁，所以实现逻辑是：请求一次后生成.json文件保存到asset中去解析；</p>
//     * <p>如果在打开APP时候去获取，则请求时间过长(大概20s-30s左右)，导致用户体验较差；</p>
//     * <p>因此不太建议在APP中直接调用该接口</p>
//     */
//    @POST(ApiConstant.API_CATEGORY_HOME_SUB)
//    @HtmlParser(CategoryParser.SubCategoryParser.class)
//    Observable<List<CategoryInfo>> getHomeSubCategory(@Body SubCategoryRequestBody param);
//}
