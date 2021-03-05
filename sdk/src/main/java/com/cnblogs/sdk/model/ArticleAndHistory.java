package com.cnblogs.sdk.model;

import androidx.room.Embedded;
import androidx.room.Relation;

/**
 * 文章和足迹关联查询实体
 *
 * @author RAE
 * @date 2021/03/04
 */
public class ArticleAndHistory {

    @Embedded
    public ArticleHistoryInfo history;

    @Relation(parentColumn = "POST_ID", entityColumn = "postId")
    public ArticleInfo article;

}
