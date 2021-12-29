package com.cnblogs.sdk;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Room;

import com.cnblogs.sdk.data.dao.CnblogsDatabase;
import com.cnblogs.sdk.internal.CnblogsLogger;
import com.cnblogs.sdk.internal.CnblogsSessionManager;
import com.cnblogs.sdk.provider.CnblogsDataProvider;
import com.cnblogs.sdk.provider.CnblogsGatewayApiProvider;
import com.cnblogs.sdk.provider.CnblogsWebApiProvider;
import com.cnblogs.sdk.provider.ICnblogsSdkConfig;
import com.github.raedev.swift.AppSwift;
import com.github.raedev.swift.config.ConfigurationManager;

/**
 * 博客园SDK主入口，设计模式：工厂模式
 * <p>请在 {@link Application#onCreate()} 中调用{@link #init(Builder)}方法进行初始化</p>
 * @author RAE
 * @date 2021/02/10
 */
@SuppressWarnings({"AlibabaLowerCamelCaseVariableNaming", "unused"})
public final class CnblogsSdk {

    public static class Builder {
        private final Application mApplication;
        private boolean mDebug;

        public Builder(@NonNull Application application) {
            mApplication = application;
        }

        public Builder debug(boolean enable) {
            this.mDebug = enable;
            return this;
        }
    }

    /**
     * 是否开启调试，输出Log见：{@link com.cnblogs.sdk.internal.CnblogsLogger#TAG }
     */
    public static boolean S_DEBUG = BuildConfig.DEBUG;
    @Nullable
    private static CnblogsSdk sFactory;
    @NonNull
    private final CnblogsDataProvider mCnblogsDataProvider;
    @NonNull
    private final CnblogsWebApiProvider mCnblogsWebApiProvider;
    private final CnblogsGatewayApiProvider mCnblogsGatewayApiProvider;
    private final CnblogsDatabase mDataBase;
    private final ICnblogsSdkConfig mSdkConfig;

    private CnblogsSdk(@NonNull Application context) {
        mSdkConfig = ConfigurationManager.create(ICnblogsSdkConfig.class);
        mCnblogsWebApiProvider = new CnblogsWebApiProvider(mSdkConfig);
        mCnblogsGatewayApiProvider = new CnblogsGatewayApiProvider(mSdkConfig);
        mDataBase = Room.databaseBuilder(context, CnblogsDatabase.class, "cnblogs.v2").build();
        mCnblogsDataProvider = new CnblogsDataProvider();
    }

    /**
     * 用来校验包的完整性
     * 每一个版本都会有固定的一个值
     */
    public static String md5() {
        return "2888cd106bd98b888fca74c785bd6cf5";
    }

    /**
     * 初始化接口配置
     * @param builder 构建者
     */
    public static void init(Builder builder) {
        S_DEBUG = builder.mDebug;
        CnblogsLogger.DEBUG = builder.mDebug;
        if (sFactory == null) {
            synchronized (CnblogsSdk.class) {
                Application application = builder.mApplication;
                if (sFactory == null) {
                    // TODO：通过DexClassLoader 加载本地dex版本库实现接口热修复功能
                    // 初始化 AppSwift
                    AppSwift.init(application);
                    // 初始化接口工厂
                    sFactory = new CnblogsSdk(application);
                }
            }
        }
    }

    /**
     * 获取工厂实例
     * @return 实例
     */
    @NonNull
    public static CnblogsSdk getInstance() {
        if (sFactory == null) {
            throw new NullPointerException("请先初始化CnblogsSdk接口!");
        }
        return sFactory;
    }

    /**
     * 获取Session管理器
     */
    @NonNull
    public static CnblogsSessionManager getSessionManager() {
        return CnblogsSessionManager.getDefault();
    }

    /**
     * 获取接口配置
     */
    public static ICnblogsSdkConfig getConfig() {
        if (sFactory == null) {
            return ConfigurationManager.create(ICnblogsSdkConfig.class);
        }
        return sFactory.mSdkConfig;
    }

    /**
     * 获取网页接口接口
     */
    @NonNull
    public CnblogsWebApiProvider getWebApiProvider() {
        return mCnblogsWebApiProvider;
    }

    /**
     * 网关接口
     */
    public CnblogsGatewayApiProvider getGatewayApiProvider() {
        return mCnblogsGatewayApiProvider;
    }

    /**
     * 博客园数据提供者
     */
    @NonNull
    public CnblogsDataProvider getDataProvider() {
        return mCnblogsDataProvider;
    }


    @NonNull
    public CnblogsDatabase getDatabase() {
        return mDataBase;
    }


}
