package com.btc.common.utility;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.btc.common.contract.Contracts;

public final class NumberUtils {
    public static final long DEFAULT_LONG = 0L;

    public static final int DEFAULT_INT = 0;

    public static long getAlignedValue(final int value, final int border) {
        return value - (value % border);
    }

    public static long defaultIfNull(@Nullable final Integer value) {
        return defaultIfNull(value, DEFAULT_INT);
    }

    public static long defaultIfNull(@Nullable final Integer value, final int defaultValue) {
        return value == null ? defaultValue : value;
    }

    public static long getAlignedValue(final long value, final long border) {
        return value - (value % border);
    }

    public static long defaultIfNull(@Nullable final Long value) {
        return defaultIfNull(value, DEFAULT_LONG);
    }

    public static long defaultIfNull(@Nullable final Long value, final long defaultValue) {
        return value == null ? defaultValue : value;
    }

    @Nullable
    public static Integer tryParse(@Nullable final String stringValue) {
        Integer result = null;

        if (!TextUtils.isEmpty(stringValue)) {
            try {
                result = Integer.parseInt(stringValue);
            } catch (final NumberFormatException ignored) {
                result = null;
            }
        }

        return result;
    }

    private NumberUtils() {
        Contracts.unreachable();
    }
}
