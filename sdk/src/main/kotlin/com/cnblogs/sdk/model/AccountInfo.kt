package com.cnblogs.sdk.model

import com.google.gson.annotations.SerializedName

/**
 * 账号信息
 * @author RAE
 * @date 2022/06/21
 * @copyright Copyright (c) https://github.com/raedev All rights reserved.
 */
data class AccountInfo(
    /**
     * 登录账号
     */
    @SerializedName("loginName")
    var account: String?,

    /**
     * 昵称
     */
    @SerializedName("displayName")
    var nickname: String?,

    /**
     * 邮件
     */
    var email: String?,

    /**
     * 手机号
     */
    @SerializedName("phoneNum")
    var phone: String?,
)