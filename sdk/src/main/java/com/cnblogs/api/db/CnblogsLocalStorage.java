package com.cnblogs.api.db;

import android.content.Context;
import android.text.TextUtils;

import com.cnblogs.api.db.table.CnblogsDBHelper;
import com.cnblogs.api.db.table.DbCategory;
import com.cnblogs.api.model.UserInfoBean;
import com.rae.session.SessionManager;

/**
 * 本地数据存储
 * Created by rae on 2019-10-26.
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public final class CnblogsLocalStorage {

    private Context mContext;

    public static CnblogsLocalStorage getInstance(Context context) {
        return new CnblogsLocalStorage(context);
    }

    private CnblogsLocalStorage(Context context) {
        mContext = context;
    }

    public IDbCategory getCategory() {
        return new DbCategory(getHelper());
    }

    private CnblogsDBHelper getHelper() {
        UserInfoBean user = SessionManager.getDefault().getUser();
        String name = "cnblogs_v1.db";
        if (user != null && !TextUtils.isEmpty(user.getBlogApp())) {
            name = user.getBlogApp() + "_" + name;
        }
        return new CnblogsDBHelper(mContext, name);
    }
}
