//package com.cnblogs.sdk.internal.handler;
//
//import com.cnblogs.sdk.exception.CnblogsIOException;
//import com.google.gson.Gson;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.IOException;
//
///**
// * 错误的处理器
// * 处理格式：{"errors":["会话校验失败，请刷新页面重试"],"type":4}
// * @author RAE
// * @date 2022/02/20
// * Copyright (c) https://github.com/raedev All rights reserved.
// */
//public class ErrorJsonHandler extends BaseJsonHandler {
//
//    @Override
//    protected void handle(Gson gson, String json) throws IOException, JSONException {
//        JSONObject object = new JSONObject(json);
//        if (object.has("errors")) {
//            String message = object.getString("errors");
//            throw new CnblogsIOException(message);
//        }
//    }
//}
