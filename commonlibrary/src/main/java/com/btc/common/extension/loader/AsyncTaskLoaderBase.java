package com.btc.common.extension.loader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.btc.common.AsyncResult;
import com.btc.common.contract.Contracts;

public abstract class AsyncTaskLoaderBase<TResult, TError>
    extends AsyncTaskLoader<AsyncResult<TResult, TError>> {
    @Override
    public final AsyncResult<TResult, TError> loadInBackground() {
        try {
            return AsyncResult.success(load());
        } catch (final Exception e) {
            return AsyncResult.error(convertExceptionToError(e));
        }
    }

    protected AsyncTaskLoaderBase(
        @NonNull final Context context) {
        super(Contracts.requireNonNull(context, "context == null"));
    }

    @NonNull
    protected abstract TError convertExceptionToError(@NonNull Exception e);

    @Nullable
    protected abstract TResult load()
        throws Exception;
}
