package com.cnblogs.sdk.internal.patch;

import com.cnblogs.sdk.internal.Logger;

import dalvik.system.DexClassLoader;

/**
 * 接口类加载器
 * <p>主要用于加载本地SDK补丁包</p>
 * @author RAE
 * @date 2022/02/03
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class CnblogsClassLoader extends DexClassLoader {

    public CnblogsClassLoader(String dexPath, String optimizedDirectory, ClassLoader parent) {
        super(dexPath, optimizedDirectory, null, parent);
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        try {
            Class<?> cls = this.findClass(name);
            if (cls != null) {
                return cls;
            }
        } catch (Exception ex) {
            Logger.e("CnblogsClassLoader", "类加载异常：" + ex.getMessage(), ex);
        }

        // 没找到类，遵守双亲委派机制
        return super.loadClass(name);
    }
}
