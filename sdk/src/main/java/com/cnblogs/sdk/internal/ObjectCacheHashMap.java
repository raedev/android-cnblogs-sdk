package com.cnblogs.sdk.internal;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 对象缓存，控制缓存个数，当超过缓存数量时移除最先缓存对象。
 * @author RAE
 * @date 2021/02/25
 */
public final class ObjectCacheHashMap<K, V> {

    /**
     * 最大的缓存个数
     */
    private final int maxCount;

    private final Map<K, V> mCacheMap;

    public ObjectCacheHashMap(int maxCount) {
        this.maxCount = maxCount;
        mCacheMap = new ConcurrentHashMap<>(10);
    }

    public V get(K key) {
        return mCacheMap.get(key);
    }

    public void put(K key, V value) {
        if (mCacheMap.size() > this.maxCount) {
            K firstKey = null;
            for (Map.Entry<K, V> kvEntry : mCacheMap.entrySet()) {
                firstKey = kvEntry.getKey();
                break;
            }
            if (firstKey != null) {
                mCacheMap.remove(firstKey);
//                CnblogsLogger.d("rae", "移除对象：" + key + "=" + value);
            }
        }
        mCacheMap.put(key, value);
//        CnblogsLogger.d("rae", "缓存对象：" + key + "=" + value);
    }

    public int size() {
        return mCacheMap.size();
    }
}
