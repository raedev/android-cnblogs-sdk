package com.cnblogs.api.model;

/**
 * 闪存评论
 * Created by ChenRui on 2017/11/2 0002 15:10.
 */
public class MomentCommentBean {
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
}
