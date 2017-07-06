package com.btc.common;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(prefix = "_")
public final class AsyncResult<TResult, TError> {
    @NonNull
    public static <TResult, TError> AsyncResult<TResult, TError> success() {
        return success(null);
    }

    @NonNull
    public static <TResult, TError> AsyncResult<TResult, TError> success(
        @Nullable final TResult result) {
        return new AsyncResult<>(true, result, null);
    }

    @NonNull
    public static <TResult, TError> AsyncResult<TResult, TError> error() {
        return error(null);
    }

    @NonNull
    public static <TResult, TError> AsyncResult<TResult, TError> error(
        @Nullable final TError error) {

        return new AsyncResult<>(false, null, error);
    }

    @Override
    public String toString() {
        final String result;

        if (isSuccess()) {
            result = "Success {" + _result + "}";
        } else {
            result = "Error {" + _error + "}";
        }

        return result;
    }

    @Getter
    @Nullable
    private final TError _error;

    @Getter
    @Nullable
    private final TResult _result;

    @Getter
    private final boolean _success;

    private AsyncResult(
        final boolean isSuccess, @Nullable final TResult result, @Nullable final TError error) {
        _success = isSuccess;
        _result = result;
        _error = error;
    }
}
