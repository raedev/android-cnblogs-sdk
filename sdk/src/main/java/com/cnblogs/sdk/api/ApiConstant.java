package com.cnblogs.sdk.api;

/**
 * 接口常量
 * @author RAE
 * @date 2021/02/15
 */
public final class ApiConstant {

    // region HTTP请求相关常量

    /**
     * JSON类型ContentType
     */
    public final static String CONTENT_TYPE_JSON = "application/json";

    /**
     * JSON类型ContentType
     */
    public final static String HEADER_ACCEPT_JSON = "accept: application/json, text/javascript, */*; q=0.01";

    /**
     * 异步请求头
     */
    public final static String HEADER_XHR = "X-Requested-With:XMLHttpRequest";

    /**
     * 默认UA
     */
    public static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 11_2_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.141 Safari/537.36";

    // endregion

    /**
     * 基础路径
     */
    public final static String BASE_URL = "https://api.cnblogs.com/";

    /**
     * 网页授权登录地址
     */
    public static final String WEB_AUTHORIZE_URL = "https://account.cnblogs.com/signin";

    /**
     * 用户信息接口
     */
    public static final String API_ACCOUNT_INFO = "https://account.cnblogs.com/user/userinfo";

    /**
     * 个人中心
     */
    public static final String API_USER_PROFILE_INFO = "https://home.cnblogs.com/u/{blogApp}";

    /**
     * 关注列表
     */
    public static final String API_USER_FOLLOW = "https://home.cnblogs.com/u/{blogApp}/relation/following";

    /**
     * 粉丝列表
     */
    public static final String API_USER_FANS = "https://home.cnblogs.com/u/{blogApp}/relation/followers";

    /**
     * 首页分类
     */
    public static final String API_CATEGORY_HOME = "https://www.cnblogs.com/legacy";

    /**
     * 首页二级分类
     */
    public static final String API_CATEGORY_HOME_SUB = "https://www.cnblogs.com/legacy/aggsite/SubCategories";

    /**
     * 博客列表
     */
    public static final String API_BLOG_LIST = "https://www.cnblogs.com/legacy/aggsite/AggSitePostList";
}
