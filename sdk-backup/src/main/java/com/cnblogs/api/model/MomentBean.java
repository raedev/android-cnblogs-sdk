package com.cnblogs.api.model;

import java.util.List;

/**
 * 闪存
 * Created by ChenRui on 2017/9/25 0025 17:03.
 */
public class MomentBean {
    // 标志
    public String id;
    // 用户ID
    public String userId;
    // 作者昵称
    public String nickName;
    // 作者头像
    public String avatar;
    // blogAPP
    public String blogApp;
    // 发布时间
    public String postTime;
    // 处理过后的内容
    public String content;
    // 原文包含HTML
    public String sourceContent;
    // 原文文本
    public String sourceTextContent;
    // 详情地址
    public String detailUrl;
    // 是否为幸运闪
    public boolean isLuckyStar;
    // 评论数据量
    public String commentCount;
    // 评论列表
    public List<MomentCommentBean> comments;
    // 图片列表
    public List<String> images;
    // 标签类别
    public List<String> topics;
    // 链接
    public List<String> links;
    // 是否可以删除
    public boolean canDelete;
}
