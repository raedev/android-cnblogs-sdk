package com.cnblogs.api.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 闪存评论
 * Created by ChenRui on 2017/11/2 0002 15:10.
 */
public class MomentCommentBean implements Parcelable {
    // 闪存ID
    public String ingId;
    // 评论ID
    public String id;
    // 作者昵称
    public String nickName;
    // BlogAPP
    public String blogApp;
    // 评论内容
    public String content;
    // 用户ID
    public String userId;
    // 作者头像
    public String avatar;
    // 发布时间
    public String postTime;
    // 引用的评论
    public MomentCommentBean quote;
    // 是否可以删除
    public boolean canDelete;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ingId);
        dest.writeString(this.id);
        dest.writeString(this.nickName);
        dest.writeString(this.blogApp);
        dest.writeString(this.content);
        dest.writeString(this.userId);
        dest.writeString(this.avatar);
        dest.writeString(this.postTime);
        dest.writeParcelable(this.quote, flags);
        dest.writeByte(this.canDelete ? (byte) 1 : (byte) 0);
    }

    public MomentCommentBean() {
    }

    protected MomentCommentBean(Parcel in) {
        this.ingId = in.readString();
        this.id = in.readString();
        this.nickName = in.readString();
        this.blogApp = in.readString();
        this.content = in.readString();
        this.userId = in.readString();
        this.avatar = in.readString();
        this.postTime = in.readString();
        this.quote = in.readParcelable(MomentCommentBean.class.getClassLoader());
        this.canDelete = in.readByte() != 0;
    }

    public static final Parcelable.Creator<MomentCommentBean> CREATOR = new Parcelable.Creator<MomentCommentBean>() {
        @Override
        public MomentCommentBean createFromParcel(Parcel source) {
            return new MomentCommentBean(source);
        }

        @Override
        public MomentCommentBean[] newArray(int size) {
            return new MomentCommentBean[size];
        }
    };
}
