//package com.cnblogs.sdk.param;
//
//import com.cnblogs.sdk.CnblogsApiFactory;
//import com.cnblogs.sdk.http.FormParam;
//import com.cnblogs.sdk.provider.OAuthApiProvider;
//
///**
// * Token参数，无需再赋值，因为：
// * ClientId 参数来自：{@link OAuthApiProvider#getClientId()}
// * ClientSecret 参数来自：{@link OAuthApiProvider#getClientSecret()} ()}
// * @author RAE
// * @date 2021/02/20
// */
//public class TokenParam extends FormParam {
//
//    @Override
//    protected void onWriteToBody() {
//        OAuthApiProvider provider = CnblogsApiFactory.getInstance().getOAuthApiProvider();
//        addQuery("client_id", provider.getClientId());
//        addQuery("client_secret", provider.getClientSecret());
//        addQuery("grant_type", "client_credentials");
//    }
//}
