package com.cnblogs.sdk.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 *
 * 用户信息
 * @author RAE
 * @date 2022/06/21
 * @copyright Copyright (c) https://github.com/raedev All rights reserved.
 */
@Parcelize
data class UserInfo(
    /**
     * 账号
     */
    var spaceUserId: String?,

    /**
     * 昵称
     */
    var displayName: String?,

    /**
     * blogApp,非常重要的字段,唯一ID
     */
    var blogApp: String?,

    /**
     * 未读消息数量
     */
    var unreadMsg: Int = 0,

    /**
     * 个人中心主页
     */
    var homeLink: String?,

    /**
     * 博客主页
     */
    var blogLink: String?,

    /**
     * 头像小图
     */
    var iconName: String?,

    /**
     * 头像大图
     */
    var avatarName: String?
) : Parcelable
