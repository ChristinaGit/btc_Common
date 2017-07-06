package com.btc.common.control.manager.task;

import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

public interface TaskManager {
    void execute(@NonNull Runnable task);

    void executeAsync(@NonNull Runnable task);

    @NonNull
    Executor getIOExecutor();

    @NonNull
    Executor getUIExecutor();
}
