package com.cnblogs.sdk.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密工具
 * @author RAE
 * @date 2021/07/22
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public final class CnblogsEncrypt {

    static {
        System.loadLibrary("cnblogs-lib");
    }

    /**
     * 加密算法
     * @param appKey AppKey
     * @param appSecret App Secret
     * @param map 请求参数
     * @return 加密后的Key
     */
    public static native String encrypt(String text);

    /**
     * byte数组转Hex
     * @param data 数组
     * @return Hex字符串
     */
    public static String toHex(byte[] data) {
        final char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[data.length * 2];
        for (int j = 0; j < data.length; j++) {
            int v = data[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    /**
     * MD5加密
     * @param value 加密文本
     * @return 加密后的值
     */
    public static String md5(String value) {
        if (value == null || value.length() <= 0) {
            return value;
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(value.getBytes(StandardCharsets.UTF_8));
            byte[] digest = md.digest();
            return toHex(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return value;
        }
    }
}
