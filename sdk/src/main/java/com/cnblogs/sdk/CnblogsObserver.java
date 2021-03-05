package com.cnblogs.sdk;

import androidx.annotation.NonNull;

import com.cnblogs.sdk.exception.CnblogsSdkException;
import com.cnblogs.sdk.internal.CnblogsLogger;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.observers.DisposableObserver;

/**
 * SDK回调处理观察者
 * 建议用SDK里面调用的{@link Observable#subscribe(Observer)} 使用该回调。
 * 将会处理统一的错误信息
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
        CnblogsLogger.e("程序异常：" + e.getMessage(), e);
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
