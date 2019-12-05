package com.cnblogs.api;

import com.rae.session.SessionManager;

import io.reactivex.observers.DisposableObserver;

/**
 * 接口回调
 * Created by ChenRui on 2017/5/5 0005 16:48.
 */
@SuppressWarnings("WeakerAccess")
public abstract class CnblogsApiObserver<T> extends DisposableObserver<T> {

    private void clearLoginToken() {
        SessionManager.getDefault().clear(); // 退出登录
    }

    /**
     * 数据回调
     */
    public abstract void onAccept(T t);

    @Override
    public void onComplete() {
        // 子类来实现
    }

    protected void onEmpty() {
        onError("暂无数据");
    }

    /**
     * 发生错误
     *
     * @param message 错误信息
     */
    public abstract void onError(String message);

    @Override
    public void onError(Throwable e) {
        CnblogsApiException exception = CnblogsApiException.valueOf(e);
        CLog.e("回调处理异常：" + exception, e);

        if (exception.getCode() == CnblogsApiException.ERROR_LOGIN_EXPIRED) {
            clearLoginToken();
            onLoginExpired();
            return;
        }

        onError(exception.getMessage());
    }

    /**
     * 登录过期
     */
    protected void onLoginExpired() {
        onError("登录失效，请重新登录");
    }

    @Override
    public void onNext(T t) {
        if (t == null) {
            onEmpty();
        } else {
            onAccept(t);
        }
    }
}
