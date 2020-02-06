package com.cnblogs.api.param;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * 更新密码的参数
 * Created by rae on 2020-01-08.
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class ResetPasswordParam {

    @Expose(serialize = false, deserialize = false)
    private final String mPublicKey;


    @SerializedName("password")
    private String oldPassword;
    @SerializedName("newPassword")
    private String newPassword;
    @SerializedName("confirmNewPassword")
    private String confirmPassword;

    public ResetPasswordParam(String publicKey, String oldPassword, String newPassword) {
        mPublicKey = publicKey;
        this.oldPassword = entry(oldPassword);
        this.newPassword = entry(newPassword);
        this.confirmPassword = this.newPassword;
    }


    /**
     * 公钥加密
     *
     * @param value 加密文本
     * @return 加密后的文本
     */
    private String entry(String value) {
        // 公钥为空不加密
        if (TextUtils.isEmpty(this.mPublicKey)) return value;
        try {
            // 加载公钥
            X509EncodedKeySpec data = new X509EncodedKeySpec(Base64.decode(mPublicKey.getBytes(), Base64.DEFAULT));
            KeyFactory factory = KeyFactory.getInstance("RSA");
            PublicKey key = factory.generatePublic(data);

            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptData = cipher.doFinal(value.getBytes());
            return Base64.encodeToString(encryptData, Base64.DEFAULT);
        } catch (Exception e) {
            Log.e("rae", "公钥加密失败", e);
        }
        return value;
    }
}
