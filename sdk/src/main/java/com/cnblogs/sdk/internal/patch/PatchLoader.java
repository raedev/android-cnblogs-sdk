package com.cnblogs.sdk.internal.patch;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cnblogs.sdk.CnblogsFactory;
import com.cnblogs.sdk.internal.Logger;

import java.io.File;
import java.lang.reflect.Constructor;

/**
 * 博客园补丁包加载
 * @author RAE
 * @date 2022/02/03
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public final class PatchLoader {

    /**
     * 补丁包固定名
     */
    private final static String PATCH_NAME = "cnblogs.pkg";
    private static final String TAG = "PatchLoader";
    private final Context mContext;

    private ClassLoader mClassLoader;

    public PatchLoader(Context context) {
        mContext = context;
    }

    /**
     * 获取补丁包路径
     * @return 绝对路径
     */
    private String getPatchPath() {
        File codeCacheDir = mContext.getCodeCacheDir();
        File patchDir = new File(codeCacheDir, "cnblogs-patch");
        if (!patchDir.exists()) {
            patchDir.mkdir();
        }
        File patchFile = new File(patchDir, PATCH_NAME);
        return patchFile.getPath();
    }

    /**
     * 获取系统优化后的dex路径
     * @return 绝对路径
     */
    private String getOptimizedDir() {
        File codeCacheDir = mContext.getCodeCacheDir();
        File dir = new File(codeCacheDir, "cnblogs-optimized");
        if (!dir.exists()) {
            dir.mkdir();
        }
        return dir.getPath();
    }

    /**
     * 获取OK文件
     */
    private File getOkFile() {
        String patchPath = getPatchPath();
        // cnblogs.pkg.ok
        return new File(patchPath + ".ok");
    }

    /**
     * 获取ClassLoader
     */
    @NonNull
    private ClassLoader getClassLoader() {
        if (mClassLoader == null) {
            mClassLoader = newClassLoader();
        }
        return mClassLoader;
    }

    private ClassLoader newClassLoader() {
        ClassLoader loader = getClass().getClassLoader();
        String patchPath = getPatchPath();
        String optimizedDir = getOptimizedDir();
        // 检查补丁包完整性
        if (!getOkFile().exists() && loader != null) {
            // OK文件不存在不加载补丁包
            return loader;
        }
        loader = new CnblogsClassLoader(patchPath, optimizedDir, loader);
        return loader;
    }

    /**
     * 从补丁包中加载类，实例必须是无参构造函数
     * @param cls 类
     * @param context Context
     * @param <T> 类型
     * @return 实例
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public <T> T newInstance(Class<T> cls, Context context) {
        ClassLoader loader = getClassLoader();
        try {
            Class<?> iClass = loader.loadClass(cls.getName());
            Constructor<?> constructor = iClass.getDeclaredConstructor(Context.class);
            constructor.setAccessible(true);
            Object obj = constructor.newInstance(context);
            return (T) obj;
        } catch (Exception e) {
            Logger.e(TAG, "实例化补丁包类异常，类型为：" + cls.getName() + ", 异常信息：" + e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取补丁包下载路径
     */
    public File getDownloadPatchFile() {
        return new File(getPatchPath() + ".temp");
    }

    /**
     * 应用补丁包
     * 1、测试补丁包是否正常加载
     * 2、正常加载后生成.ok文件
     * 3、替换原来补丁包
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public boolean apply() {
        File downloadFile = getDownloadPatchFile();
        if (!downloadFile.exists() || !downloadFile.canWrite()) {
            return false;
        }
        try {
            // 尝试加载
            int oldVersion = CnblogsFactory.getInstance().getVersion();
            int oldPatchVersion = CnblogsFactory.getInstance().getPatchVersion();
            ClassLoader loader = new CnblogsClassLoader(downloadFile.getPath(), null, null);
            Class<?> iClass = loader.loadClass(CnblogsFactory.class.getName());
            Constructor<?> constructor = iClass.getConstructor(Context.class);
            constructor.setAccessible(true);
            CnblogsFactory factory = (CnblogsFactory) constructor.newInstance(mContext);
            int versionCode = factory.getVersion();
            int patchVersion = factory.getPatchVersion();
            if (versionCode < oldVersion) {
                Logger.e(TAG, "补丁包的主版本(" + versionCode + ")不能比当前的主版本低(" + oldVersion + ")");
                return false;
            }
            // 主版本号相同，补丁版本号不能小于当前版本
            if (versionCode == oldVersion && patchVersion <= oldPatchVersion) {
                Logger.e(TAG, "主版本号(" + versionCode + ")的补丁包的版本(" + patchVersion + ")不能比当前的版本低(" + oldPatchVersion + ")");
                return false;
            }
            Logger.i(TAG, "应用补丁包，主版本号(" + versionCode + ")，补丁包版本(" + patchVersion + ")");
            File patchFile = new File(getPatchPath());
            // 删除Ok文件
            getOkFile().delete();
            // 删除当前补丁包
            patchFile.renameTo(new File(patchFile.getPath() + ".del"));
            // 移动下载的文件
            downloadFile.renameTo(patchFile);
            // 生成OK文件
            getOkFile().createNewFile();
            Logger.i(TAG, "补丁包应用成功！主版本号(" + versionCode + ")，补丁包版本(" + patchVersion + ")");
            return getOkFile().exists();
        } catch (Exception e) {
            Logger.e(TAG, "应用补丁包失败：" + e.getMessage(), e);
            // TODO: 标记这次的补丁有问题，不再检查这次的补丁
        }
        return false;
    }
}
