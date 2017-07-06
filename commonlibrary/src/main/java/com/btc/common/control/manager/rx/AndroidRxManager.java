package com.btc.common.control.manager.rx;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;

import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import com.btc.common.contract.Contracts;
import com.trello.rxlifecycle.LifecycleProvider;

@Accessors(prefix = "_")
public class AndroidRxManager<TLifecycleEvent> implements RxManager {
    public AndroidRxManager(
        @NonNull final LifecycleProvider<TLifecycleEvent> lifecycleProvider) {
        Contracts.requireNonNull(lifecycleProvider, "lifecycleProvider == null");

        _lifecycleProvider = lifecycleProvider;
    }

    @CheckResult
    @NonNull
    @Override
    public <T> Observable<T> autoManage(@NonNull final Observable<T> observable) {
        Contracts.requireNonNull(observable, "observable == null");

        return observable.compose(getLifecycleProvider().<T>bindToLifecycle());
    }

    @NonNull
    @Override
    public Scheduler getComputationScheduler() {
        return Schedulers.computation();
    }

    @NonNull
    @Override
    public Scheduler getIOScheduler() {
        return Schedulers.io();
    }

    @NonNull
    @Override
    public Scheduler getUIScheduler() {
        return AndroidSchedulers.mainThread();
    }

    @Getter(AccessLevel.PROTECTED)
    @NonNull
    private final LifecycleProvider<TLifecycleEvent> _lifecycleProvider;
}
