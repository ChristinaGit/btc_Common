package com.btc.common.control.manager.task;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;

import com.btc.common.contract.Contracts;
import com.btc.common.thread.MainThreadExecutor;

import java.util.concurrent.Executor;

@Accessors(prefix = "_")
public class AndroidTaskManager implements TaskManager {
    @Override
    public void execute(@NonNull final Runnable task) {
        Contracts.requireNonNull(task, "task == null");

        getUIExecutor().execute(task);
    }

    @Override
    public void executeAsync(@NonNull final Runnable task) {
        Contracts.requireNonNull(task, "task == null");

        getIOExecutor().execute(task);
    }

    @NonNull
    @Override
    public Executor getIOExecutor() {
        return AsyncTask.THREAD_POOL_EXECUTOR;
    }

    @NonNull
    @Override
    public Executor getUIExecutor() {
        return getMainThreadExecutor();
    }

    @Getter(AccessLevel.PROTECTED)
    @NonNull
    private final MainThreadExecutor _mainThreadExecutor = new MainThreadExecutor();
}
