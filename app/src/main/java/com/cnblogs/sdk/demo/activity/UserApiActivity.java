package com.cnblogs.sdk.demo.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.cnblogs.sdk.demo.R;
import com.cnblogs.sdk.demo.entity.ApiDetailInfo;
import com.cnblogs.sdk.model.AuthTokenInfo;

/**
 * 用户接口
 * @author RAE
 * @date 2021/02/21
 */
public class UserApiActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_detail);
        AuthTokenInfo obj = new AuthTokenInfo();
        obj.setAccessToken("abc");
        obj.setExpiresTime(20000);
        obj.setRefreshToken("eeee");

        ApiDetailInfo detailInfo = new ApiDetailInfo("登录接口", "LoginToken", obj);
        ApiDetailActivity.routeTo(this, detailInfo);
    }

}
