package com.cnblogs.sdk.internal.parser

import org.jsoup.nodes.Document

/**
 * HTML解析器接口
 * @author RAE
 * @date 2022/10/04
 * @copyright Copyright (c) https://github.com/raedev All rights reserved.
 */
interface HtmlParser<T> {

    /**
     * 解析HTML，将HTML解析为实体类
     */
    fun parse(doc: Document, html: String): T
}