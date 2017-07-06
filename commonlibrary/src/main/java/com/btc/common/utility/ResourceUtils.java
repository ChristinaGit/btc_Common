package com.btc.common.utility;

import android.support.annotation.Nullable;

import com.btc.common.contract.Contracts;

public final class ResourceUtils {
    public static boolean quietClose(@Nullable final AutoCloseable closeable) {
        boolean closed = false;

        if (closeable != null) {
            try {
                closeable.close();
                closed = true;
            } catch (final Exception ignored) {
                closed = false;
            }
        }

        return closed;
    }

    private ResourceUtils() {
        Contracts.unreachable();
    }
}
