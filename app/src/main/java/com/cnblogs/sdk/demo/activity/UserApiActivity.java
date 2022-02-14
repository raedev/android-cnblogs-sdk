package com.cnblogs.sdk.demo.activity;

import com.cnblogs.sdk.CnblogsSdk;
import com.cnblogs.sdk.internal.data.api.IUserDataApi;
import com.cnblogs.sdk.demo.ApiListAdapter;
import com.cnblogs.sdk.model.ProfileInfo;
import com.cnblogs.sdk.model.UserInfo;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

/**
 * 用户接口
 * @author RAE
 * @date 2021/02/21
 */
public class UserApiActivity extends ApiListActivity {

    IUserDataApi mUserDataApi;

    @Override
    public void onLoadApiItems(ApiListAdapter adapter) {
        mUserDataApi = CnblogsSdk.getInstance().getDataProvider().getUserDataApi();
        adapter.addItem("用户信息接口", "queryUserInfo", "loadUserInfo");
        adapter.addItem("个人中心接口", "queryProfileInfo", "loadProfileInfo");
        adapter.addItem("已关注园友信息(代码写死)", "queryProfileInfo", "loadProfileInfo2");
        adapter.addItem("未关注园友信息(代码写死)", "queryProfileInfo", "loadProfileInfo3");
        adapter.addItem("粉丝列表", "queryFans", "queryFans");
        adapter.addItem("关注列表", "queryFollows", "queryFollows");
        adapter.addItem("退出登录", "UserInfo", "logout");
    }

    public Observable<UserInfo> loadUserInfo() {
        return mUserDataApi.queryUserInfo();
    }

    public Observable<ProfileInfo> loadProfileInfo() {
        return mUserDataApi.queryProfileInfo(null);
    }

    public Observable<ProfileInfo> loadProfileInfo2() {
        // 根据自己的关注列表修改这里的blogApp
        return mUserDataApi.queryProfileInfo("xdp-gacl");
//        return mUserDataApi.queryProfileInfo("alex3714");
    }

    public Observable<ProfileInfo> loadProfileInfo3() {
        // 根据自己的关注列表修改这里的blogApp
        return mUserDataApi.queryProfileInfo("cxmanito");
    }

    public Observable<List<ProfileInfo>> queryFans() {
        // 根据自己的关注列表修改这里的blogApp
        return mUserDataApi.queryFans(null, 1);
    }

    public Observable<List<ProfileInfo>> queryFollows() {
        // 根据自己的关注列表修改这里的blogApp
        return mUserDataApi.queryFollows(null, 1);
    }

    public Observable<UserInfo> logout() {
        return mUserDataApi.logout();
    }
}
