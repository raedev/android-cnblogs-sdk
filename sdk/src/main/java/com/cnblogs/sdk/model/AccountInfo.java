package com.cnblogs.sdk.model;

import com.google.gson.annotations.SerializedName;

/**
 * 账号信息
 * @author RAE
 * @date 2022/02/18
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class AccountInfo {
    /**
     * 登录账号
     */
    @SerializedName("loginName")
    private String account;

    /**
     * 昵称
     */
    @SerializedName("displayName")
    private String nickname;

    /**
     * 邮件
     */
    private String email;

    /**
     * 手机号
     */
    @SerializedName("phoneNum")
    private String phone;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
