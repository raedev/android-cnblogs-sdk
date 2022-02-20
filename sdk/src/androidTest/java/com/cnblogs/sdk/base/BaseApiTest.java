package com.cnblogs.sdk.base;

import android.util.Log;

import com.cnblogs.sdk.internal.utils.Logger;

import org.junit.Assert;
import org.junit.Rule;

import io.reactivex.rxjava3.core.Observable;

/**
 * @author RAE
 * @date 2021/02/17
 */
public abstract class BaseApiTest {
    @Rule
    public CnblogsApiTestRule mCnblogsApiTestRule = new CnblogsApiTestRule();

    /**
     * 执行测试
     */
    protected <T> void exec(Observable<T> observable) {
        try {
            TestExecutor.exec(observable);
        } catch (Throwable throwable) {
            Logger.e("测试异常", throwable);
            Assert.fail("测试异常！" + Log.getStackTraceString(throwable));
        }
    }
}
