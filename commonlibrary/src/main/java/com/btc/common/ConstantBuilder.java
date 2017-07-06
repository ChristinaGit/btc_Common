package com.btc.common;

import android.support.annotation.NonNull;

import com.btc.common.contract.Contracts;

public final class ConstantBuilder {
    @NonNull
    public static String savedStateKey(@NonNull final Class<?> clazz, @NonNull final String key) {
        Contracts.requireNonNull(clazz, "clazz == null");
        Contracts.requireNonNull(key, "key == null");

        return clazz.getName() + ":" + key;
    }

    @NonNull
    public static String logTag(@NonNull final Class<?> clazz) {
        Contracts.requireNonNull(clazz, "clazz == null");

        return clazz.getSimpleName();
    }

    private ConstantBuilder() {
        Contracts.unreachable();
    }
}
