package com.cnblogs.sdk;

import androidx.annotation.NonNull;

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
@SuppressWarnings("AlibabaAbstractClassShouldStartWithAbstractNaming")
public abstract class CnblogsObserver<T> extends DisposableObserver<T> {

    /**
     * 正常回调
     * @param t 对象
     */
    public abstract void onAccept(@NonNull T t);

    @Override
    public void onNext(@NonNull T t) {
        onAccept(t);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        CnblogsLogger.e("程序异常：" + e.getMessage(), e);
        onError(e.getMessage());
    }

    @Override
    public void onComplete() {
    }

    /**
     * 错误回调
     * @param message 错误信息
     */
    protected void onError(String message) {
    }

}
