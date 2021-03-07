//package com.cnblogs.sdk.model;
//
//import android.os.Parcel;
//import android.os.Parcelable;
//
//import com.google.gson.annotations.SerializedName;
//
///**
// * 授权信息
// * @author RAE
// * @date 2021/02/17
// */
//public class AuthTokenInfo implements Parcelable {
//
//    public static final Creator<AuthTokenInfo> CREATOR = new Creator<AuthTokenInfo>() {
//        @Override
//        public AuthTokenInfo createFromParcel(Parcel in) {
//            return new AuthTokenInfo(in);
//        }
//
//        @Override
//        public AuthTokenInfo[] newArray(int size) {
//            return new AuthTokenInfo[size];
//        }
//    };
//    @SerializedName("access_token")
//    private String accessToken;
//    @SerializedName("refresh_token")
//    private String refreshToken;
//    @SerializedName("expires_in")
//    private long expiresTime;
//
//    public AuthTokenInfo() {
//    }
//
//    protected AuthTokenInfo(Parcel in) {
//        accessToken = in.readString();
//        refreshToken = in.readString();
//        expiresTime = in.readLong();
//    }
//
//    public String getAccessToken() {
//        return accessToken;
//    }
//
//    public void setAccessToken(String accessToken) {
//        this.accessToken = accessToken;
//    }
//
//    public String getRefreshToken() {
//        return refreshToken;
//    }
//
//    public void setRefreshToken(String refreshToken) {
//        this.refreshToken = refreshToken;
//    }
//
//    public long getExpiresTime() {
//        return expiresTime;
//    }
//
//    public void setExpiresTime(long expiresTime) {
//        this.expiresTime = expiresTime;
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(accessToken);
//        dest.writeString(refreshToken);
//        dest.writeLong(expiresTime);
//    }
//}
