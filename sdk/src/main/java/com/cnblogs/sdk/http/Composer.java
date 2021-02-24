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
 * AndroidComposer
 * @author RAE
 * @date 2021/02/24
 */
public final class Composer {

    public static <T> ObservableTransformer<T, T> mainThread() {
        return upstream -> upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> ObservableTransformer<T, T> uiThread(LifecycleOwner owner) {
        return new UiObservableTransformer<>(owner);
    }


    private static class UiObservableTransformer<T> implements ObservableTransformer<T, T> {

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
