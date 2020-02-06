package com.cnblogs.api.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 账号信息
 * Created by rae on 2020-01-19.
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class AccountInfoBean implements Parcelable {
    private String displayName;
    private String email;
    private String loginName;
    private String publicKey;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public AccountInfoBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.displayName);
        dest.writeString(this.email);
        dest.writeString(this.loginName);
        dest.writeString(this.publicKey);
    }

    protected AccountInfoBean(Parcel in) {
        this.displayName = in.readString();
        this.email = in.readString();
        this.loginName = in.readString();
        this.publicKey = in.readString();
    }

    public static final Creator<AccountInfoBean> CREATOR = new Creator<AccountInfoBean>() {
        @Override
        public AccountInfoBean createFromParcel(Parcel source) {
            return new AccountInfoBean(source);
        }

        @Override
        public AccountInfoBean[] newArray(int size) {
            return new AccountInfoBean[size];
        }
    };
}
