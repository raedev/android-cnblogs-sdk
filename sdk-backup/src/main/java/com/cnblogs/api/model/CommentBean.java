package com.cnblogs.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

/**
 * 博客评论实体
 * Created by ChenRui on 2016/12/10 18:00.
 */
public class CommentBean implements Parcelable {

    private String id;
    private String date;
    private String content;
    private AuthorBean author;
    private CommentBean quote; // 引用的评论

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public AuthorBean getAuthor() {
        return author;
    }

    public void setAuthor(AuthorBean author) {
        this.author = author;
    }

    public CommentBean getQuote() {
        return quote;
    }

    public void setQuote(CommentBean quote) {
        this.quote = quote;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.date);
        dest.writeString(this.content);
        dest.writeParcelable(this.author, flags);
        dest.writeParcelable(this.quote, flags);
    }

    public CommentBean() {
    }

    protected CommentBean(Parcel in) {
        this.id = in.readString();
        this.date = in.readString();
        this.content = in.readString();
        this.author = in.readParcelable(AuthorBean.class.getClassLoader());
        this.quote = in.readParcelable(CommentBean.class.getClassLoader());
    }

    public static final Creator<CommentBean> CREATOR = new Creator<CommentBean>() {
        @Override
        public CommentBean createFromParcel(Parcel source) {
            return new CommentBean(source);
        }

        @Override
        public CommentBean[] newArray(int size) {
            return new CommentBean[size];
        }
    };
}
