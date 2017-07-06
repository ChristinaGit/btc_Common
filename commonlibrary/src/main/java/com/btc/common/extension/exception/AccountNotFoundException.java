package com.btc.common.extension.exception;

import android.os.Build;
import android.support.annotation.RequiresApi;

public class AccountNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 6790165464099447047L;

    public AccountNotFoundException() {
    }

    public AccountNotFoundException(final String message) {
        super(message);
    }

    public AccountNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public AccountNotFoundException(final Throwable cause) {
        super(cause);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected AccountNotFoundException(
        final String message,
        final Throwable cause,
        final boolean enableSuppression,
        final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
