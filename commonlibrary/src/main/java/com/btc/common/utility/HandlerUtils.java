package com.btc.common.utility;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.btc.common.contract.Contracts;

public final class HandlerUtils {
    @NonNull
    public static Handler getMainThreadHandler() {
        return new Handler(Looper.getMainLooper());
    }

    private HandlerUtils() {
        Contracts.unreachable();
    }
}
