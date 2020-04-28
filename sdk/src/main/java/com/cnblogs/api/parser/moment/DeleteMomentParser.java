package com.cnblogs.api.parser.moment;

import com.cnblogs.api.http.IHtmlParser;
import com.cnblogs.api.model.CnblogsJsonResult;

import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * 删除闪存
 * Created by ChenRui on 2017/9/25 0025 17:16.
 */
public class DeleteMomentParser implements IHtmlParser<CnblogsJsonResult> {


    @Override
    public CnblogsJsonResult parse(Document document) throws IOException {
        String text = document.text();
        CnblogsJsonResult result = new CnblogsJsonResult();
        if (text.contains("成功")) {
            result.setSuccess(true);
        } else {
            result.setMessage(text);
        }
        return result;
    }


}
