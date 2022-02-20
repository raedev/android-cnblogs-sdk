package com.cnblogs.sdk.base;

import android.app.Application;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.internal.util.AndroidRunnerParams;
import androidx.test.platform.app.InstrumentationRegistry;

import com.cnblogs.sdk.CnblogsFactory;

import org.junit.runners.model.InitializationError;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 测试运行环境
 * @author RAE
 * @date 2022/02/17
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class CnblogsAndroidRunner extends AndroidJUnit4ClassRunner {

    public CnblogsAndroidRunner(Class<?> klass, AndroidRunnerParams runnerParams) throws InitializationError {
        super(klass, runnerParams);
    }

    public CnblogsAndroidRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    protected Object createTest() throws Exception {
        // 初始化工厂
        Application application = (Application) InstrumentationRegistry.getInstrumentation().getContext().getApplicationContext();
        CnblogsFactory.initFactory(application);
        CnblogsFactory.getInstance().openDebug();
        Object obj = super.createTest();
        // 注入实例
        for (Field field : obj.getClass().getDeclaredFields()) {
            for (Annotation annotation : field.getAnnotations()) {
                if (annotation instanceof WebApi) {
                    injectApiField(CnblogsFactory.getInstance().getWebApiProvider(), obj, field);
                }
                if (annotation instanceof OpenApi) {
                    injectApiField(CnblogsFactory.getInstance().getOpenApiProvider(), obj, field);
                }
                if (annotation instanceof GatewayApi) {
                    injectApiField(CnblogsFactory.getInstance().getGatewayApiProvider(), obj, field);
                }
            }
        }
        return obj;
    }

    /**
     * 注入接口实例
     */
    private void injectApiField(Object apiProvider, Object obj, Field field) throws InvocationTargetException, IllegalAccessException {
        Class<?> type = field.getType();
        boolean find = false;
        for (Method method : apiProvider.getClass().getDeclaredMethods()) {
            if (method.getReturnType() == type) {
                field.setAccessible(true);
                field.set(obj, method.invoke(apiProvider));
                find = true;
                break;
            }
        }
        if (!find) {
            throw new NullPointerException("找不到接口：" + type + "的实例在 " + apiProvider.getClass().getName());
        }
    }
}
