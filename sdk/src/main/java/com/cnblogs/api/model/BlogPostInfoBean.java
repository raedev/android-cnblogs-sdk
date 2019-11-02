package com.cnblogs.api.model;

/**
 * 博客信息，只是扩展的信息，详情实体为：{@link BlogBean}
 */
public class BlogPostInfoBean {

    // 作者信息
    private AuthorBean author;

    // 推荐数量
    private String diggCount;

    public AuthorBean getAuthor() {
        return author;
    }

    public void setAuthor(AuthorBean author) {
        this.author = author;
    }

    public String getDiggCount() {
        return diggCount;
    }

    public void setDiggCount(String diggCount) {
        this.diggCount = diggCount;
    }
}
