package com.btc.common.contract;

import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.jetbrains.annotations.Contract;

public final class Contracts {
    @NonNull
    public static RuntimeException asRuntimeException(@Nullable final Throwable throwable) {
        final RuntimeException runtimeException;

        if (throwable instanceof RuntimeException) {
            runtimeException = (RuntimeException) throwable;
        } else {
            runtimeException = new ContractException(throwable);
        }

        return runtimeException;
    }

    @Contract("false, _ -> fail; true, _ -> true")
    public static boolean require(final boolean condition, @Nullable final Throwable throwable) {
        if (!condition) {
            throw asRuntimeException(throwable);
        }
        return true;
    }

    @Contract("false, _ -> fail; true, _ -> true")
    public static boolean require(final boolean condition, @Nullable final String message) {
        return require(condition, new ContractException(message));
    }

    @Contract("false -> fail; true -> true")
    public static boolean require(final boolean condition) {
        return require(condition, (String) null);
    }

    public static int requireInRange(
        final int value, final int min, final int max, @Nullable final Throwable throwable) {
        require(min <= value && value <= max, throwable);

        return value;
    }

    public static int requireInRange(
        final int value, final int min, final int max, @Nullable final String message) {
        return requireInRange(value, min, max, new ContractException(message));
    }

    public static int requireInRange(final int value, final int min, final int max) {
        return requireInRange(value, min, max, (String) null);
    }

    public static long requireInRange(
        final long value, final long min, final long max, @Nullable final Throwable throwable) {
        require(min <= value && value <= max, throwable);

        return value;
    }

    public static long requireInRange(
        final long value, final long min, final long max, @Nullable final String message) {
        return requireInRange(value, min, max, new ContractException(message));
    }

    public static long requireInRange(final long value, final long min, final long max) {
        return requireInRange(value, min, max, (String) null);
    }

    @Contract("null, _, _, _ -> fail; !null, _, _, _ -> !null")
    @NonNull
    public static <TBorder, TValue extends Comparable<TBorder>> TValue requireInRange(
        @Nullable final TValue value,
        @Nullable final TBorder min,
        @Nullable final TBorder max,
        @Nullable final Throwable throwable) {
        boolean condition = value != null;
        if (condition && min != null) {
            condition = value.compareTo(min) >= 0;
        }
        if (condition && max != null) {
            condition = value.compareTo(max) <= 0;
        }
        require(condition, throwable);

        return value;
    }

    @Contract("null, _, _, _ -> fail; !null, _, _, _ -> !null")
    @NonNull
    public static <TBorder, TValue extends Comparable<TBorder>> TValue requireInRange(
        @Nullable final TValue value,
        @Nullable final TBorder min,
        @Nullable final TBorder max,
        @Nullable final String message) {
        return requireInRange(value, min, max, new ContractException(message));
    }

    @Contract("null, _, _ -> fail; !null, _, _ -> !null")
    @NonNull
    public static <TBorder, TValue extends Comparable<TBorder>> TValue requireInRange(
        @Nullable final TValue value, @Nullable final TBorder min, @Nullable final TBorder max) {
        return requireInRange(value, min, max, (String) null);
    }

    public static void requireMainThread(@Nullable final Throwable throwable) {
        final long currentThreadId = Thread.currentThread().getId();
        final long mainThreadId = Looper.getMainLooper().getThread().getId();
        require(currentThreadId == mainThreadId, throwable);
    }

    public static void requireMainThread(@Nullable final String message) {
        requireMainThread(new ContractException(message));
    }

    public static void requireMainThread() {
        requireMainThread((String) null);
    }

    @Contract("null, _ -> fail; !null, _ -> !null")
    @NonNull
    public static <T> T requireNonNull(
        @Nullable final T object, @Nullable final Throwable throwable) {
        require(object != null, throwable);

        return object;
    }

    @Contract("null, _ -> fail; !null, _ -> !null")
    @NonNull
    public static <T> T requireNonNull(
        @Nullable final T object, @Nullable final String message) {
        return requireNonNull(object, new ContractException(message));
    }

    @Contract("null -> fail; !null-> !null")
    @NonNull
    public static <T> T requireNonNull(@Nullable final T object) {
        return requireNonNull(object, (String) null);
    }

    @Contract("!null, _ -> fail; null, _ -> null")
    @Nullable
    public static <T> T requireNull(
        @Nullable final T object, @Nullable final Throwable throwable) {
        require(object == null, throwable);

        return null;
    }

    @Contract("!null, _ -> fail; null, _ -> null")
    @Nullable
    public static <T> T requireNull(@Nullable final T object, @Nullable final String message) {
        return requireNull(object, new ContractException(message));
    }

    @Contract("!null -> fail; null -> null")
    @Nullable
    public static <T> T requireNull(@Nullable final T object) {
        return requireNull(object, (String) null);
    }

    public static void requireWorkerThread(@Nullable final Throwable throwable) {
        final long currentThreadId = Thread.currentThread().getId();
        final long mainThreadId = Looper.getMainLooper().getThread().getId();
        require(currentThreadId != mainThreadId, throwable);
    }

    public static void requireWorkerThread(@Nullable final String message) {
        requireWorkerThread(new ContractException(message));
    }

    public static void requireWorkerThread() {
        requireWorkerThread((String) null);
    }

    @Contract("_ -> fail")
    public static void unreachable(@Nullable final Throwable throwable) {
        require(false, throwable);
    }

    @Contract("_ -> fail")
    public static void unreachable(@Nullable final String message) {
        unreachable(new ContractException(message));
    }

    @Contract("-> fail")
    public static void unreachable() {
        unreachable((String) null);
    }

    private Contracts() {
        Contracts.unreachable();
    }
}
