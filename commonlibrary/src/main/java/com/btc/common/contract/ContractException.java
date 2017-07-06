package com.btc.common.contract;

import android.support.annotation.Nullable;

public class ContractException extends RuntimeException {
    private static final long serialVersionUID = 8013008906294169410L;

    public ContractException() {
    }

    public ContractException(@Nullable final String message) {
        super(message);
    }

    public ContractException(@Nullable final String message, @Nullable final Throwable cause) {
        super(message, cause);
    }

    public ContractException(@Nullable final Throwable cause) {
        super(cause);
    }
}
