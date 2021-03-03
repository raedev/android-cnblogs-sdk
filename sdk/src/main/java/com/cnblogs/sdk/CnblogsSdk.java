package com.cnblogs.sdk;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Room;

import com.cnblogs.sdk.db.CnblogsDatabase;
import com.cnblogs.sdk.exception.CnblogsSdkException;
import com.cnblogs.sdk.internal.CnblogsLogger;
import com.cnblogs.sdk.internal.CnblogsSessionManager;
import com.cnblogs.sdk.provider.CnblogsConfigProvider;
import com.cnblogs.sdk.provider.CnblogsDataProvider;
import com.cnblogs.sdk.provider.CnblogsWebApiProvider;
import com.github.raedev.swift.AppSwift;

/**
 * 博客园SDK主入口，设计模式：工厂模式
 * <p>请在 {@link Application#onCreate()} 中调用{@link #config(Builder)}方法进行初始化</p>
 *
 * @author RAE
 * @date 2021/02/10
 */
@SuppressWarnings({"AlibabaLowerCamelCaseVariableNaming", "unused"})
public final class CnblogsSdk {

    /**
     * 是否开启调试，输出Log见：{@link com.cnblogs.sdk.internal.CnblogsLogger#TAG }
     */
    public static boolean S_DEBUG = BuildConfig.DEBUG;
    @Nullable
    private static CnblogsSdk sFactory;
    @Nullable
    private static CnblogsSessionManager sSessionManager;
    @NonNull
    private final CnblogsDataProvider mCnblogsDataProvider;
    @NonNull
    private final CnblogsWebApiProvider mCnblogsWebApiProvider;
    @NonNull
    private final Application mContext;
    @Nullable
    private CnblogsConfigProvider mConfigProvider;
    private final CnblogsDatabase mDataBase;

    private CnblogsSdk(@NonNull Application context) {
        mContext = context;
        mCnblogsWebApiProvider = new CnblogsWebApiProvider();
        mCnblogsDataProvider = new CnblogsDataProvider();
        mDataBase = Room.databaseBuilder(context, CnblogsDatabase.class, "cnblogs-v2").build();
    }

    /**
     * 初始化接口配置
     *
     * @param builder 构建者
     */
    public static void config(Builder builder) {
        S_DEBUG = builder.mDebug;
        CnblogsLogger.DEBUG = builder.mDebug;
        if (sFactory == null) {
            synchronized (CnblogsSdk.class) {
                Application application = builder.mApplication;
                if (sFactory == null) {
                    // 初始化 AppSwift
                    AppSwift.init(application);
                    // 初始化用户管理
                    sSessionManager = new CnblogsSessionManager(application);
                    // 初始化接口工厂
                    sFactory = new CnblogsSdk(application);
                }
            }
        }
    }

    /**
     * 获取工厂实例
     *
     * @return 实例
     */
    @NonNull
    public static CnblogsSdk getInstance() {
        if (sFactory == null) {
            throw new NullPointerException("please invoke Builder.build() method first to create sdk factory!");
        }
        return sFactory;
    }

    /**
     * 获取Session管理器
     */
    @NonNull
    public static CnblogsSessionManager getSessionManager() {
        if (sSessionManager == null) {
            throw new CnblogsSdkException("请先初始化CnblogsSessionManager后在使用");
        }
        return sSessionManager;
    }

    /**
     * 获取网页接口接口
     */
    @NonNull
    public CnblogsWebApiProvider getWebApiProvider() {
        return mCnblogsWebApiProvider;
    }

    /**
     * 博客园数据提供者
     */
    @NonNull
    public CnblogsDataProvider getDataProvider() {
        return mCnblogsDataProvider;
    }

    /**
     * 获取接口配置
     */
    @NonNull
    public CnblogsConfigProvider getConfigProvider() {
        if (mConfigProvider == null) {
            mConfigProvider = new CnblogsConfigProvider(mContext);
        }
        return mConfigProvider;
    }

    @NonNull
    public CnblogsDatabase getDatabase() {
        return mDataBase;
    }

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
}
