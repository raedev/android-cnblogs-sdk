package com.cnblogs.sdk.model;

/**
 * App配置信息
 * @author RAE
 * @date 2022/03/25
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class AppConfig {

    /**
     * 华为应用市场审核状态
     */
    private boolean huaweiAudit;

    /**
     * 小米应用市场审核状态
     */
    private boolean xiaomiAudit;

    /**
     * 接口签名密钥
     */
    private String appKey;

    public boolean isHuaweiAudit() {
        return huaweiAudit;
    }

    public void setHuaweiAudit(boolean huaweiAudit) {
        this.huaweiAudit = huaweiAudit;
    }

    public boolean isXiaomiAudit() {
        return xiaomiAudit;
    }

    public void setXiaomiAudit(boolean xiaomiAudit) {
        this.xiaomiAudit = xiaomiAudit;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }
}
