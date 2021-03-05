package com.cnblogs.sdk.internal;

import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;
import com.cnblogs.sdk.model.ArticleInfo;
import com.github.raedev.swift.AppSwift;

import java.io.File;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * 文章缓存
 *
 * @author RAE
 * @date 2021/03/04
 */
public final class ArticleCache {

    public ArticleInfo mArticleInfo;

    public ArticleCache(ArticleInfo articleInfo) {
        mArticleInfo = articleInfo;
    }

    private File cacheFile() {
        File cacheDir = AppSwift.getApplication().getExternalCacheDir();
        File parent = new File(cacheDir, "article");
        FileUtils.createOrExistsDir(parent);
        return new File(parent, cacheKey());
    }

    private String cacheKey() {
        return String.format("%s_%s.tmp", mArticleInfo.getType(), mArticleInfo.getPostId());
    }

    /**
     * 【异步】缓存文章内容
     *
     * @param content 内容
     */
    public void cache(String content) {
        Observable.create(emitter -> {
            File file = cacheFile();
            if (file.exists()) {
                FileUtils.delete(file);
            }
            FileIOUtils.writeFileFromString(file, content);
        }).observeOn(Schedulers.io()).subscribe();
    }

    /**
     * 获取缓存的文章内容
     *
     * @return 文章内容
     */
    @Nullable
    public String getContent() {
        File file = cacheFile();
        if (!file.exists()) {
            return null;
        }
        String content = FileIOUtils.readFile2String(file);
        if (TextUtils.isEmpty(content)) {
            return null;
        }
        return content;
    }
}
