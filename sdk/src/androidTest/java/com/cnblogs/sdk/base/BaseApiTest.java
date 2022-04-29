package com.cnblogs.sdk.base;

import com.cnblogs.sdk.internal.utils.Logger;

import org.junit.Assert;
import org.junit.Rule;

import io.reactivex.rxjava3.core.Completable;
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
    protected <T> T exec(Observable<T> observable) {
        try {
            return TestExecutor.exec(observable);
        } catch (Throwable throwable) {
            Logger.e("测试异常", throwable);
            Assert.fail("异常结束测试" + throwable.getMessage());
        }
        return null;
    }

    protected <T> void exec(Completable observable) {
        try {
            TestExecutor.exec(observable);
        } catch (Throwable throwable) {
            Logger.e("测试异常", throwable);
            Assert.fail("异常结束测试" + throwable.getMessage());
        }
    }
}
