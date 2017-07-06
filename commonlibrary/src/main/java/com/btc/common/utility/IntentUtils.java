package com.btc.common.utility;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.btc.common.contract.Contracts;

public final class IntentUtils {
    @Nullable
    public static Double getNullableDoubleExtra(
        @NonNull final Intent intent, @NonNull final String name) {
        Contracts.requireNonNull(intent, "intent == null");
        Contracts.requireNonNull(name, "name == null");

        return intent.hasExtra(name) ? intent.getDoubleExtra(name, 0d) : null;
    }

    @Nullable
    public static Float getNullableFloatExtra(
        @NonNull final Intent intent, @NonNull final String name) {
        Contracts.requireNonNull(intent, "intent == null");
        Contracts.requireNonNull(name, "name == null");

        return intent.hasExtra(name) ? intent.getFloatExtra(name, 0f) : null;
    }

    @Nullable
    public static Integer getNullableIntExtra(
        @NonNull final Intent intent, @NonNull final String name) {
        Contracts.requireNonNull(intent, "intent == null");
        Contracts.requireNonNull(name, "name == null");

        return intent.hasExtra(name) ? intent.getIntExtra(name, (short) 0) : null;
    }

    @Nullable
    public static Short getNullableShortExtra(
        @NonNull final Intent intent, @NonNull final String name) {
        Contracts.requireNonNull(intent, "intent == null");
        Contracts.requireNonNull(name, "name == null");

        return intent.hasExtra(name) ? intent.getShortExtra(name, (short) 0) : null;
    }

    @Nullable
    public static Boolean getNullableBooleanExtra(
        @NonNull final Intent intent, @NonNull final String name) {
        Contracts.requireNonNull(intent, "intent == null");
        Contracts.requireNonNull(name, "name == null");

        return intent.hasExtra(name) ? intent.getBooleanExtra(name, false) : null;
    }

    @Nullable
    public static Long getNullableLongExtra(
        @NonNull final Intent intent, @NonNull final String name) {
        Contracts.requireNonNull(intent, "intent == null");
        Contracts.requireNonNull(name, "name == null");

        return intent.hasExtra(name) ? intent.getLongExtra(name, 0) : null;
    }

    public static void putNullableExtra(
        @NonNull final Intent intent, @NonNull final String name, @Nullable final Boolean value) {
        Contracts.requireNonNull(intent, "intent == null");
        Contracts.requireNonNull(name, "name == null");

        if (value != null) {
            intent.putExtra(name, value);
        } else {
            intent.removeExtra(name);
        }
    }

    public static void putNullableExtra(
        @NonNull final Intent intent, @NonNull final String name, @Nullable final Short value) {
        Contracts.requireNonNull(intent, "intent == null");
        Contracts.requireNonNull(name, "name == null");

        if (value != null) {
            intent.putExtra(name, value);
        } else {
            intent.removeExtra(name);
        }
    }

    public static void putNullableExtra(
        @NonNull final Intent intent, @NonNull final String name, @Nullable final Byte value) {
        Contracts.requireNonNull(intent, "intent == null");
        Contracts.requireNonNull(name, "name == null");

        if (value != null) {
            intent.putExtra(name, value);
        } else {
            intent.removeExtra(name);
        }
    }

    public static void putNullableExtra(
        @NonNull final Intent intent, @NonNull final String name, @Nullable final Float value) {
        Contracts.requireNonNull(intent, "intent == null");
        Contracts.requireNonNull(name, "name == null");

        if (value != null) {
            intent.putExtra(name, value);
        } else {
            intent.removeExtra(name);
        }
    }

    public static void putNullableExtra(
        @NonNull final Intent intent, @NonNull final String name, @Nullable final Double value) {
        Contracts.requireNonNull(intent, "intent == null");
        Contracts.requireNonNull(name, "name == null");

        if (value != null) {
            intent.putExtra(name, value);
        } else {
            intent.removeExtra(name);
        }
    }

    public static void putNullableExtra(
        @NonNull final Intent intent, @NonNull final String name, @Nullable final Long value) {
        Contracts.requireNonNull(intent, "intent == null");
        Contracts.requireNonNull(name, "name == null");

        if (value != null) {
            intent.putExtra(name, value);
        } else {
            intent.removeExtra(name);
        }
    }

    public static void putNullableExtra(
        @NonNull final Intent intent, @NonNull final String name, @Nullable final Integer value) {
        Contracts.requireNonNull(intent, "intent == null");
        Contracts.requireNonNull(name, "name == null");

        if (value != null) {
            intent.putExtra(name, value);
        } else {
            intent.removeExtra(name);
        }
    }

    private IntentUtils() {
        Contracts.unreachable();
    }
}
