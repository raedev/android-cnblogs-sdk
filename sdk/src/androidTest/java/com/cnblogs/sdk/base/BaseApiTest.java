package com.cnblogs.sdk.base;

import android.app.Application;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;

import com.cnblogs.sdk.CnblogsFactory;
import com.cnblogs.sdk.internal.Logger;
import com.github.raedev.swift.utils.JsonUtils;

import org.junit.Assert;
import org.junit.Before;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * @author RAE
 * @date 2021/02/17
 */
public abstract class BaseApiTest {

    private final CountDownLatch mCountDownLatch = new CountDownLatch(1);

    @Before
    public void setup() {
        Application application = (Application) InstrumentationRegistry.getInstrumentation().getContext().getApplicationContext();
        // 初始化接口
        CnblogsFactory.initFactory(application);
    }

    public <T> void runTest(Observable<T> observable) {
        try {
            StackTraceElement[] traceElements = Thread.currentThread().getStackTrace();
            boolean hasFind = false;
            for (StackTraceElement traceElement : traceElements) {
                if ("runTest".equals(traceElement.getMethodName())) {
                    hasFind = true;
                    continue;
                }
                if (hasFind) {
                    Logger.d("运行测试 at " + traceElement.getClassName() + "." + traceElement.getMethodName() + "(" + Class.forName(traceElement.getClassName()).getSimpleName() + ".java:" + traceElement.getLineNumber() + ")");
                    break;
                }
            }
            final AtomicReference<Throwable> exception = new AtomicReference<>();
            Disposable disposable = observable.doFinally(mCountDownLatch::countDown).subscribe(t -> {
                String json = JsonUtils.toJson(t);
                Logger.d("测试返回结果：\r\n" + json);
            }, e -> {
                Logger.e("测试异常：" + e.getMessage(), e);
                exception.set(e);
            });
            mCountDownLatch.await(30, TimeUnit.SECONDS);
            Throwable throwable = exception.get();
            if (throwable != null) {
                Assert.fail("测试失败：" + Log.getStackTraceString(throwable));
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(Log.getStackTraceString(e));
        }
    }
}
