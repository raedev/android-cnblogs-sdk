package com.cnblogs.sdk.http

/**
 * 请求头定义
 * @author RAE
 * @date 2022/09/18
 * @copyright Copyright (c) https://github.com/raedev All rights reserved.
 */
object HttpHead {

    /**
     * XMLHttpRequest 异步请求
     */
    const val XHR = "X-Requested-With:XMLHttpRequest"

    /**
     * 请求体格式为JSON类型
     */
    const val JSON_CONTENT_TYPE = "Content-Type:application/json; charset=UTF-8"

    /**
     * 请求需要接收JSON类型数据
     */
    const val ACCEPT_TYPE_JSON = "accept:application/json;"

    /**
     * 浏览器引擎
     */
    const val USER_AGENT =
        "Mozilla/5.0 (Macintosh; Intel Mac OS X 11_2_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.141 Safari/537.36"

    /**
     * 将Form-Data参数转换为JSON格式
     */
    const val JSON_PARAM = "param:application/json;"
}