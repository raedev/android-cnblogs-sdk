package com.cnblogs.sdk.internal.parser.blog

import com.cnblogs.sdk.internal.parser.HtmlParser
import com.cnblogs.sdk.internal.utils.HtmlUtils.parseNumber
import com.cnblogs.sdk.model.ArticleInfo
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

/**
 * 博客列表解析
 * @author RAE
 * @date 2022/10/04
 * @copyright Copyright (c) https://github.com/raedev All rights reserved.
 */
class BlogListParser : HtmlParser<List<ArticleInfo>> {

    override fun parse(doc: Document, html: String): List<ArticleInfo> {
        val result = mutableListOf<ArticleInfo>()
        doc.select(".post-item").forEach {
            ArticleInfo().apply {
                val ids = parseBlogId(it)
                if (ids.size == 3) {
                    blogApp = ids[0]
                    postId = ids[1]
                    blogId = ids[2]
                }
                val id = postId ?: return@forEach
                title = it.select(".post-item-title").text()
                summary = it.select(".post-item-summary").text()
                url = it.select(".post-item-title").attr("href")
                title = it.select(".post-item-title").text()
                val counts = parseCount(id, url!!, it)
                viewCount = counts[0]
                likeCount = counts[1]
                commentCount = counts[2]
                result.add(this)
            }
        }
        return result
    }


    /**
     * 解析Id
     * @return [blogApp, postId, blogId]
     */
    private fun parseBlogId(element: Element): Array<String> {
        // 解析：blogApp、postId、blogId
        // DiggPost('sheng-jie',14420239,326932,1)
        val temp = element.select(".post-meta-item").attr("onclick")
        val start = temp.indexOf("(")
        val end = temp.lastIndexOf(")")
        if (start < 0 || end < 0) {
            return emptyArray()
        }
        val arr = arrayOf("", "", "")

        temp.substring(start + 1, end).split(",").forEachIndexed { i, item ->
            val text = item.trim()
            when (i) {
                0 -> arr[0] = text.replace("'", "")
                1 -> arr[1] = text
                2 -> arr[2] = text
            }
        }
        return arr
    }


    /**
     * 解析数量
     * @return [like, comment, view]
     */
    private fun parseCount(postId: String, url: String, element: Element): IntArray {
        // 点赞数量
        val likeCount = element.select("#digg_control_$postId")
            .select("span")
            .text().toIntOrNull() ?: 0

        var commentCount = 0
        var viewCount = 0

        element.select(".post-item-foot >a").forEach {
            val href = it.attr("href")
            if (href.contains("#commentform")) {
                // 评论数量
                commentCount = it.text().parseNumber()
            }
            if (url == href) {
                // 阅读数量
                viewCount = it.text().parseNumber()
            }
        }

        return intArrayOf(viewCount, likeCount, commentCount)
    }

}