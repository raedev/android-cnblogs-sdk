package com.cnblogs.api.param;

/**
 * 更新昵称的参数
 * Created by rae on 2020-01-08.
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class UpdateAccountParam {

    private String sameAsEmail;
    private String loginName;

    public UpdateAccountParam(String loginName) {
        this.loginName = loginName;
    }

    public String getSameAsEmail() {
        return sameAsEmail;
    }

    public void setSameAsEmail(String sameAsEmail) {
        this.sameAsEmail = sameAsEmail;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
}
