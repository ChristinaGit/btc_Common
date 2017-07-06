package com.btc.common.thread;

import android.os.Handler;
import android.support.annotation.NonNull;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;

import com.btc.common.contract.Contracts;
import com.btc.common.utility.HandlerUtils;

import java.util.concurrent.Executor;

@Accessors(prefix = "_")
public class MainThreadExecutor implements Executor {
    @Override
    public void execute(@NonNull final Runnable command) {
        Contracts.requireNonNull(command, "command == null");

        getMainThreadHandler().post(command);
    }

    @Getter(value = AccessLevel.PROTECTED, lazy = true)
    private final Handler _mainThreadHandler = HandlerUtils.getMainThreadHandler();
}
