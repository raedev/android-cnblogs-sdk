package com.cnblogs.sdk.base;

import com.cnblogs.sdk.internal.utils.Logger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

/**
 * 执行测试
 * @author RAE
 * @date 2022/02/16
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public final class TestExecutor {


    /**
     * 执行测试
     */
    public static <T> T exec(Observable<T> observable) throws Throwable {
        String testMethod = getTestMethod();
        Logger.d("Test", "ExecTest at " + testMethod);
        T data = observable.blockingFirst();
        printObject(testMethod, data);
        return data;
    }

    /**
     * 打印对象
     */
    private static <T> void printObject(String testMethod, T data) {
//        if (data instanceof Response) {
//            Logger.w("Test.Result", "无内容响应");
//            return;
//        }
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapterFactory(new TypeAdapterFactory() {
                    @Override
                    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
                        return null;
                    }
                })
                .create();
        String json = gson.toJson(data);
        Logger.w("Test.Result", "Test Result For: " + testMethod);
        Logger.i("Test.Result", json);
    }

    private static String getTestMethod() throws ClassNotFoundException {
        StackTraceElement[] traceElements = Thread.currentThread().getStackTrace();
        int index = 0;
        for (int i = 0; i < traceElements.length; i++) {
            StackTraceElement traceElement = traceElements[i];
            if (traceElement.getClassName().endsWith("TestExecutor") && "exec".equals(traceElement.getMethodName())) {
                index = i + 2;
                break;
            }
        }
        StackTraceElement traceElement = traceElements[index];
        return traceElement.getClassName() + "." + traceElement.getMethodName() + "(" + Class.forName(traceElement.getClassName()).getSimpleName() + ".java:" + traceElement.getLineNumber() + ")";
    }

    public static <T> void exec(Completable observable) throws Throwable {
        String testMethod = getTestMethod();
        Logger.d("Test", "Exec Test at " + testMethod);
        observable.blockingSubscribe();
        Logger.i("Test", "Test is Finished: " + testMethod);
    }
}
