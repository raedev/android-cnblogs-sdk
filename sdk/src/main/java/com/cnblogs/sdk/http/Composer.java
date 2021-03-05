package com.cnblogs.sdk.http;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.trello.lifecycle4.android.lifecycle.AndroidLifecycle;
import com.trello.rxlifecycle4.LifecycleTransformer;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * 用在{@link Observable#compose(ObservableTransformer)} 中来关联Android的生命周期。
 *
 * @author RAE
 * @date 2021/02/24
 */
public final class Composer {

    /**
     * 运行在IO线程，回调到主线程中
     */
    public static <T> ObservableTransformer<T, T> mainThread() {
        return upstream -> upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 关联生命周期，运行在IO线程，回调到主线程中
     *
     * @param owner 生命周期：Activity / Fragment
     */
    public static <T> UiObservableTransformer<T> uiThread(LifecycleOwner owner) {
        return new UiObservableTransformer<>(owner);
    }

    public static class UiObservableTransformer<T> implements ObservableTransformer<T, T> {

        private final LifecycleOwner mOwner;

        public UiObservableTransformer(LifecycleOwner owner) {
            mOwner = owner;
        }

        @Override
        public @NonNull ObservableSource<T> apply(@NonNull Observable<T> upstream) {
            LifecycleTransformer<T> transformer = AndroidLifecycle
                    .createLifecycleProvider(mOwner)
                    .bindUntilEvent(Lifecycle.Event.ON_DESTROY);
            return transformer.apply(upstream
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()));
        }
    }
}
