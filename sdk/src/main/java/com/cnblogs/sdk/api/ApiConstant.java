package com.cnblogs.sdk.api;

/**
 * 接口常量
 * @author RAE
 * @date 2021/02/15
 */
public final class ApiConstant {

    /**
     * JSON类型ContentType
     */
    public final static String CONTENT_TYPE_JSON = "application/json";

    /**
     * 默认UA
     */
    public static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 11_2_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.141 Safari/537.36";

    /**
     * 基础路径
     */
    public final static String BASE_URL = "https://api.cnblogs.com/";

    /**
     * 客户端授权
     */
    public static final String OAUTH_API_AUTH_CLIENT_TOKEN = "/token";

    /**
     * 网页授权登录地址
     */
    public static final String WEB_AUTHORIZE_URL = "https://account.cnblogs.com/signin";

    public static final String OAUTH_WEB_AUTHORIZE_CALLBACK_URL = "https://oauth.cnblogs.com/auth/callback";

    /**
     * 首页地址
     */
    public static final String HOME_URL = "https://www.cnblogs.com";
}
