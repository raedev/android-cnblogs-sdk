package com.cnblogs.sdk.internal.utils;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

/**
 * 弱引用对象
 * @author RAE
 * @date 2022/02/19
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class WeakObjectMap<K, V> extends WeakHashMap<K, V> {

    private final int mMaxLength;

    public WeakObjectMap(int initialCapacity) {
        super(initialCapacity);
        mMaxLength = initialCapacity;
    }

    @Nullable
    @Override
    public V put(K key, V value) {
        // 检查空间
        resize();
        return super.put(key, value);
    }


    /**
     * 调整缓存容量
     */
    private void resize() {
        if (this.size() < mMaxLength) {
            return;
        }
        // 删除空间
        List<K> deleteKeys = new ArrayList<>();
        int i = 0;
        for (K key : this.keySet()) {
            if (i > mMaxLength / 3) {
                // 腾出1/3空间
                break;
            }
            deleteKeys.add(key);
            i++;
        }
        for (K key : deleteKeys) {
            this.remove(key);
        }
        deleteKeys.clear();
    }
}
