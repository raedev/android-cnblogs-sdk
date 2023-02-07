package com.cnblogs.sdk.http

import com.cnblogs.sdk.annotation.HTML
import com.cnblogs.sdk.html.HtmlParser
import com.cnblogs.sdk.utils.ioError
import com.google.gson.Gson
import com.google.gson.JsonIOException
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import okio.Buffer
import org.jsoup.Jsoup
import retrofit2.Converter
import retrofit2.Retrofit
import java.io.OutputStreamWriter
import java.io.Reader
import java.lang.reflect.Type
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets


/**
 * 全局请求响应转换器，这是一个非常重要的类。
 * @author RAE
 * @date 2023/02/08
 * @copyright Copyright (c) https://github.com/raedev All rights reserved.
 */
class CnblogsConverterFactory private constructor() : Converter.Factory() {

    private val gson = Gson()

    companion object {
        fun create() = CnblogsConverterFactory()
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<out Annotation>,
        methodAnnotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody> {
        val adapter = gson.getAdapter(TypeToken.get(type))
        return CnblogsRequestBodyConverter(gson, adapter)
    }

    override fun responseBodyConverter(
        type: Type, annotations: Array<out Annotation>, retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        val html: HTML? = annotations.find { it is HTML } as HTML?
        val adapter = gson.getAdapter(TypeToken.get(type))
        return CnblogsResponseBodyConverter(gson, adapter, html)
    }
}

/**
 * 博客园@Body转换器，转换请求体为JSON对象。
 */
class CnblogsRequestBodyConverter<T> internal constructor(
    private val gson: Gson,
    private val adapter: TypeAdapter<T>
) : Converter<T, RequestBody> {

    companion object {
        private val MEDIA_TYPE: MediaType = "application/json; charset=UTF-8".toMediaType()
        private val UTF_8: Charset = StandardCharsets.UTF_8
    }

    override fun convert(value: T): RequestBody {
        val buffer = Buffer()
        var writer: JsonWriter? = null
        try {
            writer = gson.newJsonWriter(OutputStreamWriter(buffer.outputStream(), UTF_8))
            this.adapter.write(writer, value)
        } catch (ex: Exception) {
            ioError("Send the request body error: ${ex.message}")
        } finally {
            writer?.close()
        }
        return buffer.readByteString().toRequestBody(MEDIA_TYPE)
    }
}


/**
 * 博客园请求响应转换器，自动将HTML或者JSON转为实体数据。
 */
class CnblogsResponseBodyConverter<T> internal constructor(
    private val gson: Gson,
    private val adapter: TypeAdapter<T>,
    private val html: HTML?
) : Converter<ResponseBody, T> {

    private var parser: HtmlParser<T>? = null

    override fun convert(value: ResponseBody): T {
        try {
            // 根据返回的格式自动解析
            val type = value.contentType()?.type
            if (type != null && type.contains("html") && html != null) {
                return convertFromHtml(html, value.string())
            }
            return convertFromJson(value.charStream())
        } catch (ex: Exception) {
            ioError("Exception on parse data: ${ex.message}")
        } finally {
            value.close()
        }
    }

    private fun convertFromJson(stream: Reader): T {
        val reader = gson.newJsonReader(stream)
        val read = adapter.read(reader)
        if (reader.peek() === JsonToken.END_DOCUMENT) {
            return read
        }
        throw JsonIOException("JSON document was not fully consumed.")
    }

    @Suppress("UNCHECKED_CAST")
    private fun convertFromHtml(annotation: HTML, text: String): T {
        if (parser == null) {
            // 实例化
            parser = annotation.value.java.getConstructor().newInstance() as HtmlParser<T>
        }
        return parser!!.parse(Jsoup.parse(text), text)
    }

}