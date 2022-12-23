package com.cnblogs.sdk.model

/**
 * 文章信息
 * @author RAE
 * @date 2022/06/21
 * @copyright Copyright (c) https://github.com/raedev All rights reserved.
 */
data class ArticleInfo(

    /**
     * 文章Id
     */
    var articleId: String? = null,

    /**
     * 标题
     */
    var title: String? = null,

    /**
     * 简要
     */
    var summary: String? = null

) {

    var postId: String? = null
    var blogId: String? = null
    var blogApp: String? = null

    /**
     * 所属分类
     */
    var categoryId: String? = null

    /**
     * 评论数量
     */
    var commentCount: Int = 0

    /**
     * 查看数量
     */
    var viewCount: Int = 0

    /**
     * 点赞数量
     */
    var likeCount: Int = 0

    /**
     * 发表时间
     */
    var postDate: String? = null

    /**
     * 原文地址
     */
    var url: String? = null

    /**
     * 缩略图
     */
    var thumbUrl: String? = null

}