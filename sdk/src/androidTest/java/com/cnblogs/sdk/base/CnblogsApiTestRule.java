package com.cnblogs.sdk.base;

import com.cnblogs.sdk.BuildConfig;
import com.cnblogs.sdk.common.CnblogsUserManager;
import com.cnblogs.sdk.internal.utils.Logger;

import org.junit.internal.AssumptionViolatedException;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.MultipleFailureException;
import org.junit.runners.model.Statement;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * 输出方法名
 * @author RAE
 * @date 2022/02/16
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class CnblogsApiTestRule implements TestRule {

    private static final String TAG = "Test";

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                List<Throwable> errors = new ArrayList<Throwable>();
                startingQuietly(description, errors);
                Observable.create(emitter -> {
                    Object data = new ArrayList<>();
                    base.evaluate();
                    emitter.onNext(data);
                    emitter.onComplete();
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).blockingSubscribe(next -> {
                    succeededQuietly(description, errors);
                }, error -> {
                    errors.add(error);
                    if (error instanceof org.junit.internal.AssumptionViolatedException) {
                        skippedQuietly((AssumptionViolatedException) error, description, errors);
                    } else {
                        failedQuietly(error, description, errors);
                    }
                }, () -> {
                    finishedQuietly(description, errors);
                });

                MultipleFailureException.assertEmpty(errors);
            }


        };
    }

    private void startingQuietly(Description description, List<Throwable> errors) {
        try {
            starting(description);
            mockLogin();
        } catch (Throwable e) {
            errors.add(e);
        }
    }

    /**
     * 登录信息设置
     */
    private void mockLogin() {
        CnblogsUserManager um = CnblogsUserManager.getInstance();
        um.setLoginCookie(BuildConfig.TEST_COOKIE);
    }


    private void succeededQuietly(Description description, List<Throwable> errors) {
    }

    private void skippedQuietly(AssumptionViolatedException e, Description description, List<Throwable> errors) {
    }

    private void finishedQuietly(Description description, List<Throwable> errors) {
    }

    private void failedQuietly(Throwable e, Description description, List<Throwable> errors) {
    }

    protected void starting(Description d) {
        String methodName = d.getMethodName();
        String className = d.getClassName();
        String simpleName = d.getTestClass().getSimpleName();
        Logger.d(TAG, "============================================");
        Logger.d(TAG, "Running Test: " + simpleName + "." + methodName);
        Logger.d(TAG, "============================================");
    }

    private void findApi(String className) {

    }
}
