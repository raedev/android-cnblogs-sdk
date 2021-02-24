package com.cnblogs.sdk.demo.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.github.raedev.swift.utils.JsonUtils;

/**
 * @author RAE
 * @date 2021/02/21
 */
public class ApiDetailInfo implements Parcelable {

    public static final Creator<ApiDetailInfo> CREATOR = new Creator<ApiDetailInfo>() {
        @Override
        public ApiDetailInfo createFromParcel(Parcel in) {
            return new ApiDetailInfo(in);
        }

        @Override
        public ApiDetailInfo[] newArray(int size) {
            return new ApiDetailInfo[size];
        }
    };
    private String json;
    private String name;
    private String title;


    public ApiDetailInfo(String title, String name, Object object) {
        this.title = title;
        this.json = JsonUtils.toJson(object);
        this.name = name;
    }

    protected ApiDetailInfo(Parcel in) {
        json = in.readString();
        name = in.readString();
        title = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(json);
        dest.writeString(name);
        dest.writeString(title);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
