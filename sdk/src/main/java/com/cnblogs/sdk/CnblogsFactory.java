package com.cnblogs.sdk;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cnblogs.sdk.internal.loader.CnblogsClassLoader;
import com.cnblogs.sdk.internal.utils.Constant;
import com.cnblogs.sdk.internal.utils.Logger;
import com.cnblogs.sdk.provider.GatewayApiProvider;
import com.cnblogs.sdk.provider.OpenApiProvider;
import com.cnblogs.sdk.provider.WebApiProvider;

import java.util.Objects;

/**
 * 博客园全局入口类，提供全套的API接口实例。
 * <p>工厂提供两种接口, 一种官网开放接口{@link #getOpenApiProvider()}，另一种网页接口{@link #getWebApiProvider()}</p>
 * @author RAE
 * @date 2021/12/29
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public final class CnblogsFactory {

    @Nullable
    private static CnblogsFactory sFactory;
    private static Application sContext;
    private final OpenApiProvider mOpenApiProvider;
    private final WebApiProvider mWebApiProvider;
    private final GatewayApiProvider mGatewayApiProvider;

    CnblogsFactory(Application context) {
        // 初始化接口提供程序
        mOpenApiProvider = new OpenApiProvider(context);
        mWebApiProvider = new WebApiProvider(context);
        mGatewayApiProvider = new GatewayApiProvider(context);
    }

    /**
     * 获取初始化的上下文
     */
    public static Context getContext() {
        return Objects.requireNonNull(sContext, "博客园接口尚未初始化，请先调用initFactory方法");
    }

    /**
     * 获取接口实例
     */
    @NonNull
    public static CnblogsFactory getInstance() {
        return Objects.requireNonNull(sFactory, "博客园接口尚未初始化，请先调用initFactory方法");
    }

    /**
     * 初始化博客园接口，在Application中调用。
     */
    public static void initFactory(@NonNull Application application) {
        if (sFactory != null) {
            Logger.w("无需重复初始化接口");
            return;
        }
        sContext = application;
        // 初始化补丁包加载器
        CnblogsClassLoader loader = new CnblogsClassLoader(application);
        // 初始化工厂
        sFactory = Objects.requireNonNull(loader.newInstance(CnblogsFactory.class, application), "初始化博客园接口失败，请查看Logcat日志");
    }

    /**
     * 打开调试模式，将在Logcat中输出更多信息，配置过滤TAG：Cnblogs
     */
    public void openDebug() {
        Constant.DEBUG = true;
    }

    /**
     * 获取官网开放接口
     * @return 接口提供者
     * @see OpenApiProvider
     */
    public OpenApiProvider getOpenApiProvider() {
        return mOpenApiProvider;
    }

    /**
     * 获取网页接口
     * @return 接口提供者
     * @see WebApiProvider
     */
    public WebApiProvider getWebApiProvider() {
        return mWebApiProvider;
    }

    /**
     * 获取网关接口
     * @return 接口提供者
     * @see GatewayApiProvider
     */
    public GatewayApiProvider getGatewayApiProvider() {
        return mGatewayApiProvider;
    }


    /**
     * 获取当前接口版本号
     * @return 版本号
     */
    public int getVersion() {
        return 1;
    }

    /**
     * 获取补丁包版本号
     * @return 补丁版本
     */
    public int getPatchVersion() {
        return 0;
    }
}
