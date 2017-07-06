package com.btc.common.extension.asyncTask;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;

import com.btc.common.AsyncCallback;
import com.btc.common.AsyncResult;
import com.btc.common.contract.Contracts;

@Accessors(prefix = "_")
public abstract class AsyncTaskBase<TResult, TError>
    extends AsyncTask<Void, Void, AsyncResult<TResult, TError>> {
    protected AsyncTaskBase(@NonNull final AsyncCallback<TResult, TError> asyncCallback) {
        Contracts.requireNonNull(asyncCallback, "asyncCallback == null");

        _asyncCallback = asyncCallback;
    }

    @Override
    protected final AsyncResult<TResult, TError> doInBackground(final Void... params) {
        try {
            return AsyncResult.success(doInBackground());
        } catch (final Exception e) {
            return AsyncResult.error(convertExceptionToError(e));
        }
    }

    @Override
    protected void onPostExecute(final AsyncResult<TResult, TError> asyncResult) {
        super.onPostExecute(asyncResult);

        if (asyncResult != null) {
            if (asyncResult.isSuccess()) {
                getAsyncCallback().onSuccess(asyncResult.getResult());
            } else {
                getAsyncCallback().onError(asyncResult.getError());
            }
        }
    }

    @NonNull
    protected abstract TError convertExceptionToError(@NonNull Exception e);

    @Nullable
    protected abstract TResult doInBackground()
        throws Exception;

    @Getter(AccessLevel.PROTECTED)
    @NonNull
    private final AsyncCallback<TResult, TError> _asyncCallback;
}
