package com.cnblogs.sdk.http;

import androidx.annotation.NonNull;

import com.cnblogs.sdk.exception.CnblogsSdkException;
import com.cnblogs.sdk.internal.CnblogsLogger;

import io.reactivex.rxjava3.observers.DisposableObserver;

/**
 * 观察者
 * @author RAE
 * @date 2021/02/24
 */
public abstract class CnblogsObserver<T> extends DisposableObserver<T> {

    @Override
    public void onNext(@NonNull T t) {
        onAccept(t);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        CnblogsLogger.e("发生异常：" + e.getMessage(), e);
        onError(new CnblogsSdkException(e));
    }

    @Override
    public void onComplete() {

    }

    /**
     * 正常回调
     * @param t 对象
     */
    public abstract void onAccept(@NonNull T t);

    /**
     * 错误回调
     * @param exception 一次信息
     */
    public void onError(@NonNull CnblogsSdkException exception) {
    }
}
