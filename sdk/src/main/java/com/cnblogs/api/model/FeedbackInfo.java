package com.cnblogs.api.model;

import android.os.Parcel;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rae.cnblogs.sdk.model.CnblogFeedbackInfo;

import java.util.List;

/**
 * Created by rae on 2020/2/27.
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class FeedbackInfo extends CnblogFeedbackInfo {

    public static final Creator<FeedbackInfo> CREATOR = new Creator<FeedbackInfo>() {
        @Override
        public FeedbackInfo createFromParcel(Parcel source) {
            return new FeedbackInfo(source);
        }

        @Override
        public FeedbackInfo[] newArray(int size) {
            return new FeedbackInfo[size];
        }
    };

    private List<String> attachments;

    public FeedbackInfo(CnblogFeedbackInfo m) {
        this.setAttachment(m.getAttachment());
        this.setAvatar(m.getAvatar());
        this.setCommentCount(m.getCommentCount());
        this.setContent(m.getContent());
        this.setDate(m.getDate());
        this.setId(m.getId());
        this.setIsRead(m.getIsRead());
        this.setStatus(m.getStatus());
        this.setUserId(m.getUserId());
        this.setUserName(m.getUserName());

        Gson gson = new Gson();
        attachments = gson.fromJson(m.getAttachment(), new TypeToken<List<String>>() {
        }.getType());
        this.setCount(attachments == null ? 0 : attachments.size());
    }

    public FeedbackInfo(Parcel parcel) {
        super(parcel);
        this.attachments = parcel.createStringArrayList();
    }

    public List<String> getAttachments() {
        return attachments;
    }

    public boolean isSystem() {
        return "SYSTEM".equalsIgnoreCase(this.userId);
    }

    @Override
    public String getUserName() {
        return TextUtils.isEmpty(this.userName) ? "热心园友" : this.userName;
    }


    public String getStatusText() {
        return this.status == 0 ? "处理中" : "已完成";
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeStringList(this.attachments);
    }
}
