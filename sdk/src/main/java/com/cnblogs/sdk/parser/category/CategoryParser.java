package com.cnblogs.sdk.parser.category;

import android.text.TextUtils;

import com.cnblogs.sdk.model.CategoryInfo;
import com.cnblogs.sdk.parser.BaseHtmlParser;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * 分类解析
 * @author RAE
 * @date 2021/02/26
 */
public final class CategoryParser {

    /**
     * 首页解析
     */
    public static class HomeCategoryParser extends BaseHtmlParser<List<CategoryInfo>> {

        /**
         * 首页分类解析
         * @param doc 文档
         */
        @Override
        protected List<CategoryInfo> parseHtml(Document doc) {
            List<CategoryInfo> result = new ArrayList<>();
            Elements lis = doc.select("#cate_item li");
            for (Element li : lis) {
                CategoryInfo m = new CategoryInfo();
                String name = li.text().trim();
                m.setCategoryId(li.attr("id").replace("cate_item_", ""));
                m.setName(name.substring(0, name.indexOf("(")));
                m.setTypeName("TopSiteCategory");
                m.setParentId("0");
                if (!TextUtils.equals(m.getCategoryId(), "-1") && !TextUtils.equals(m.getCategoryId(), "0")) {
                    result.add(m);
                }
            }
            return result;
        }
    }

    /**
     * 首页子类解析
     */
    public static class SubCategoryParser extends BaseHtmlParser<List<CategoryInfo>> {

        private final OkHttpClient mHttpClient = new OkHttpClient();

        @Override
        protected List<CategoryInfo> parseHtml(Document doc) {
            List<CategoryInfo> result = new ArrayList<>();
            Elements lis = doc.select("li");
            for (Element li : lis) {
                String name = li.text().trim();
                name = name.substring(0, name.indexOf("("));
                // 需要发请求
                String url = "https://www.cnblogs.com/" + li.select("a").eq(0).attr("href");
                CategoryInfo categoryInfo = blockingCategoryRequest(name, url);
                if (categoryInfo != null) {
                    result.add(categoryInfo);
                }
            }
            return result;
        }

        private CategoryInfo blockingCategoryRequest(String name, String url) {
            try {
                Request request = new Request.Builder().url(url).build();
                Call call = mHttpClient.newCall(request);
                String html = call.execute().body().string();
                Document doc = Jsoup.parse(html);
                Elements elements = doc.select("script");
                for (Element element : elements) {
                    String text = element.html();
                    if (!text.contains("aggSiteModel")) {
                        continue;
                    }
                    int start = text.indexOf("{");
                    int end = text.lastIndexOf("}");
                    String json = text.substring(start, end + 1);
                    JSONObject obj = new JSONObject(json);
                    CategoryInfo m = new CategoryInfo();
                    m.setName(name);
                    m.setTypeName(obj.getString("CategoryType"));
                    m.setParentId(obj.getString("ParentCategoryId"));
                    m.setCategoryId(obj.getString("CategoryId"));
                    return m;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
