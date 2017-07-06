package com.btc.common.utility;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.btc.common.contract.Contracts;

public final class CursorUtils {
    @Nullable
    public static Double getNullableDouble(@NonNull final Cursor cursor, final int columnIndex) {
        Contracts.requireNonNull(cursor, "cursor == null");

        final Double result;

        if (cursor.isNull(columnIndex)) {
            result = null;
        } else {
            result = cursor.getDouble(columnIndex);
        }

        return result;
    }

    @Nullable
    public static Integer getNullableInt(@NonNull final Cursor cursor, final int columnIndex) {
        Contracts.requireNonNull(cursor, "cursor == null");

        final Integer result;

        if (cursor.isNull(columnIndex)) {
            result = null;
        } else {
            result = cursor.getInt(columnIndex);
        }

        return result;
    }

    @Nullable
    public static Short getNullableShort(@NonNull final Cursor cursor, final int columnIndex) {
        Contracts.requireNonNull(cursor, "cursor == null");

        final Short result;

        if (cursor.isNull(columnIndex)) {
            result = null;
        } else {
            result = cursor.getShort(columnIndex);
        }

        return result;
    }

    @Nullable
    public static Long getNullableLong(@NonNull final Cursor cursor, final int columnIndex) {
        Contracts.requireNonNull(cursor, "cursor == null");

        final Long result;

        if (cursor.isNull(columnIndex)) {
            result = null;
        } else {
            result = cursor.getLong(columnIndex);
        }

        return result;
    }

    @Nullable
    public static Float getNullableFloat(@NonNull final Cursor cursor, final int columnIndex) {
        Contracts.requireNonNull(cursor, "cursor == null");

        final Float result;

        if (cursor.isNull(columnIndex)) {
            result = null;
        } else {
            result = cursor.getFloat(columnIndex);
        }

        return result;
    }

    private CursorUtils() {
        Contracts.unreachable();
    }
}
