package com.btc.common.utility;

import android.support.annotation.NonNull;

import com.btc.common.contract.Contracts;

public final class ExceptionUtils {
    @NonNull
    public static RuntimeException asRuntimeException(@NonNull final Exception exception) {
        Contracts.requireNonNull(exception, "exception == null");

        final RuntimeException runtimeException;
        if (exception instanceof RuntimeException) {
            runtimeException = (RuntimeException) exception;
        } else {
            runtimeException = new RuntimeException(exception);
        }

        return runtimeException;
    }

    private ExceptionUtils() {
        Contracts.unreachable();
    }
}
