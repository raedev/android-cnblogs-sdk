package com.cnblogs.sdk.http.body;

import com.cnblogs.sdk.api.ApiConstant;
import com.cnblogs.sdk.model.CategoryInfo;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

/**
 * 首页二级分类请求参数
 * @author RAE
 * @date 2021/03/01
 */
public class SubCategoryRequestBody extends RequestBody {

    private final List<String> mCategoryList = new ArrayList<>();

    public SubCategoryRequestBody(List<CategoryInfo> categoryList) {
        for (CategoryInfo categoryInfo : categoryList) {
            mCategoryList.add(categoryInfo.getCategoryId());
        }
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return MediaType.parse(ApiConstant.CONTENT_TYPE_JSON);
    }

    @Override
    public void writeTo(@NotNull BufferedSink bufferedSink) throws IOException {
        StringBuilder sb = new StringBuilder("\"");
        for (int i = 0; i < mCategoryList.size(); i++) {
            String item = mCategoryList.get(i);
            if (i > 0) {
                sb.append(",");
            }
            sb.append(item);
        }
        sb.append("\"");
        bufferedSink.writeString(sb.toString(), StandardCharsets.UTF_8);
    }
}
