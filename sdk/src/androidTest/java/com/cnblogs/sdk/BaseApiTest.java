package com.cnblogs.sdk;

import android.app.Application;

import androidx.test.platform.app.InstrumentationRegistry;

import com.cnblogs.sdk.internal.CnblogsLogger;
import com.github.raedev.swift.utils.JsonUtils;

import org.junit.Before;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observers.DefaultObserver;

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
        CnblogsSdk.config(new CnblogsSdk.Builder(application).build());
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
                    CnblogsLogger.d("运行测试：[" + traceElement.getClassName() + "] # " + traceElement.getMethodName());
                    break;
                }
            }
            observable
                    .doFinally(mCountDownLatch::countDown)
                    .subscribe(new DefaultObserver<T>() {
                        @Override
                        public void onNext(@NonNull T t) {
                            String json = JsonUtils.toJson(t);
                            CnblogsLogger.d("测试返回结果：\r\n" + json);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            CnblogsLogger.e("测试异常：" + e.getMessage(), e);
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
            mCountDownLatch.await(30, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
