package com.cnblogs.sdk.model;

import android.text.Html;
import android.text.SpannableString;

/**
 * HTML 字符串
 * @author RAE
 * @date 2022/03/04
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class TextHtml extends SpannableString {

    /**
     * @param source source html text
     */
    public TextHtml(String source) {
        super(Html.fromHtml(source));
    }


}
