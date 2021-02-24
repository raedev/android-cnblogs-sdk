package com.cnblogs.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;

/**
 * 博客实体
 * 注意：序列化的时候不要传数据大的这两个字段（summary、content)
 * Created by ChenRui on 2016/11/28 23:45.
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class BlogBean extends ArticleBean implements Parcelable {

    private String blogId; // 博客Id
    private String postId; // 帖子Id

    public String getBlogId() {
        return blogId;
    }

    public void setBlogId(String blogId) {
        this.blogId = blogId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public BlogBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.blogId);
        dest.writeString(this.postId);
    }

    protected BlogBean(Parcel in) {
        super(in);
        this.blogId = in.readString();
        this.postId = in.readString();
    }

    public static final Creator<BlogBean> CREATOR = new Creator<BlogBean>() {
        @Override
        public BlogBean createFromParcel(Parcel source) {
            return new BlogBean(source);
        }

        @Override
        public BlogBean[] newArray(int size) {
            return new BlogBean[size];
        }
    };

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }
}
