package com.cnblogs.api.entity;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

import androidx.annotation.NonNull;

import com.cnblogs.api.RaeLog;

import java.util.ArrayList;
import java.util.List;

/**
 * 闪存文本内容实体
 * Created by rae on 2020/4/9.
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class MomentTextContent {
    private CharSequence content = ""; // 用户输入的原文
    private final List<String> links = new ArrayList<>(); // 链接地址，节省字数只能加一条链接
    private final List<String> tags = new ArrayList<>(); // 闪存标签
    private final List<String> images = new ArrayList<>(); // 图片地址
    private final List<String> localImages = new ArrayList<>(); // 本地的图片地址


    /**
     * 获取当前闪存字数，采用博客园官网的计算方式，网址不算在内
     */
    public int length() {
        String urlRegex = "(http|ftp|https)://([^/:,，]+)(:\\d+)?(/[^\\u0391-\\uFFE5\\s,]*)?";
        String content = this.toString();
        RaeLog.e(content);
        content = content.replaceAll(urlRegex, "").replaceAll("\\s+", "");
        RaeLog.d(content);
        return content.length();
    }

    /**
     * 是否为空文本
     */
    public boolean isEmpty() {
        return TextUtils.isEmpty(this.toString());
    }

    /**
     * 闪存支持的最大长度
     */
    public int maxLength() {
        return 300;
    }

    /**
     * 可用的长度
     */
    public int available() {
        return Math.max(0, maxLength() - length());
    }

    public void setContent(CharSequence text) {
        this.content = text;
    }

    public List<String> getLinks() {
        return links;
    }

    public List<String> getTags() {
        return tags;
    }

    public boolean addLink(String link) {
        if (!this.links.contains(link) && available() > link.length()) {
            this.links.add(0, link);
            return true;
        }
        return false;
    }

    /**
     * 添加提到的人
     */
    public boolean addMention(String name, int start) {
        CharSequence text = "@" + name + " ";
        return appendText(text, start);
    }

    /**
     * 添加文本
     *
     * @param text  文本
     * @param start 插入位置
     */
    private boolean appendText(CharSequence text, int start) {
        // 长度判断
        if (available() - text.length() < 0) return false;
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#0087FF"));
        SpannableStringBuilder sb;
        if (this.content instanceof SpannableStringBuilder) {
            sb = (SpannableStringBuilder) this.content;
        } else {
            sb = new SpannableStringBuilder(this.content);
            this.content = sb;
        }
        sb.insert(start, text);
        sb.setSpan(colorSpan, start, start + text.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return true;
    }

    public CharSequence getContent() {
        return this.content;
    }

    /**
     * 添加话题
     */
    public boolean addTopic(String tag) {
        // 判断长度是否超过
        if (available() < tag.length()) return false;
        if (!this.tags.contains(tag)) {
            this.tags.add(0, tag);
            return true;
        }
        return false;
    }

    /**
     * 移除话题
     */
    public void removeTopic(String tag) {
        this.tags.remove(tag);
    }

    /**
     * 移除链接
     */
    public void removeLink(String link) {
        this.links.remove(link);
    }

    /**
     * 添加图片
     *
     * @param url 先上传图片，图片地址真实地址
     */
    public void addImage(String url) {
        if (!this.images.contains(url) && available() > 0) {
            this.images.add(url);
        }
    }

    @NonNull
    @Override
    public String toString() {

        // [标签] + 内容 + 链接 + #img 图片 #end

        StringBuilder sb = new StringBuilder();

        // 处理标签
        for (String tag : tags) {
            sb.append(String.format("[%s]", tag));
        }

        // 处理文本
        sb.append(content);

        // 处理链接
        for (String link : links) {
            sb.append(link);
            sb.append(" ");
        }

        // 处理图片
        if (images.size() > 0) {
            sb.append("#img ");
            for (String image : images) {
                sb.append(image);
                sb.append(" ");
            }
            sb.append("#end");
        }

        return sb.toString();
    }
}
