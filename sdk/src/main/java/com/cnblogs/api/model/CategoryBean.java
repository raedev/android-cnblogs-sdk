package com.cnblogs.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 分类
 * Created by ChenRui on 2016/11/30 0030 17:20.
 */
@SuppressWarnings({"unused"})
public class CategoryBean implements Parcelable {

    public static final Creator<CategoryBean> CREATOR = new Creator<CategoryBean>() {
        @Override
        public CategoryBean createFromParcel(Parcel source) {
            return new CategoryBean(source);
        }

        @Override
        public CategoryBean[] newArray(int size) {
            return new CategoryBean[size];
        }
    };
    private int categoryId;
    private int parentId;
    private String name;
    private String type;
    private List<CategoryBean> subCategories;

    public CategoryBean() {
    }

    public CategoryBean(String type, String name, int id) {
        this.type = type;
        this.name = name;
        this.categoryId = id;
    }

    protected CategoryBean(Parcel in) {
        this.categoryId = in.readInt();
        this.parentId = in.readInt();
        this.name = in.readString();
        this.type = in.readString();
        this.subCategories = in.createTypedArrayList(CategoryBean.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public List<CategoryBean> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<CategoryBean> subCategories) {
        this.subCategories = subCategories;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.categoryId);
        dest.writeInt(this.parentId);
        dest.writeString(this.name);
        dest.writeString(this.type);
        dest.writeTypedList(this.subCategories);
    }
}
