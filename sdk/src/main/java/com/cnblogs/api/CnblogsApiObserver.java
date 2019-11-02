package com.cnblogs.api;

import com.rae.session.SessionManager;

import java.net.ConnectException;
import java.net.UnknownHostException;

import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

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
        String message = e.getMessage();
        CLog.e("回调处理异常：" + message, e);

        // 未登录处理
        if (message.contains("登录过期")
                || message.contains("Authorization")
                || (e instanceof CnblogsApiException
                && ((CnblogsApiException) e).getCode() == CnblogsApiException.ERROR_LOGIN_EXPIRED)
        ) {
            clearLoginToken();
            onLoginExpired();
            return;
        }

        // 网络状态处理
        if (e instanceof ConnectException) {
            onError("无法连接到服务器");
            return;
        }
        if (e instanceof UnknownHostException) {
            onError("网络连接错误，请检查网络连接");
            return;
        }

        // HTTP 状态错误
        if (e instanceof HttpException) {
            int code = ((HttpException) e).code();
            if (code == 401) {
                clearLoginToken();
                onLoginExpired();
                return;
            }
            onError(String.format("请求错误！状态码[%s]", code));
            return;
        }
        onError(message);
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
