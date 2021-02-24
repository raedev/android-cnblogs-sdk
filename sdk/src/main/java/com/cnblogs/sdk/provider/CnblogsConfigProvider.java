package com.cnblogs.sdk.provider;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 配置中心
 * @author RAE
 * @date 2021/02/23
 */
public final class CnblogsConfigProvider {

    private final SharedPreferences mSharedPreferences;

    public CnblogsConfigProvider(Context context) {
        mSharedPreferences = context.getSharedPreferences("CnblogsConfigProvider", Context.MODE_PRIVATE);
    }

    public void setValue(String key, String value) {
        mSharedPreferences.edit().putString(key, value).apply();
    }

    public String getValue(String key, String defValue) {
        return mSharedPreferences.getString(key, defValue);
    }

    public String getValue(String key) {
        return getValue(key, null);
    }
}
