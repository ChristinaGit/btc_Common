package com.btc.common.control.manager.loader;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.val;

import com.btc.common.AsyncCallback;
import com.btc.common.AsyncResult;
import com.btc.common.contract.Contracts;

@Accessors(prefix = "_")
public class ActivityDataLoaderManager implements DataLoaderManager {
    public ActivityDataLoaderManager(@NonNull final LoaderManager loaderManager) {
        Contracts.requireNonNull(loaderManager, "loaderManager == null");

        _loaderManager = loaderManager;
    }

    @Override
    public <TResult, TError> void startLoader(
        final int loaderId,
        @NonNull final Loader<AsyncResult<TResult, TError>> loader,
        @NonNull final AsyncCallback<TResult, TError> callback) {
        Contracts.requireNonNull(loader, "loader == null");
        Contracts.requireNonNull(callback, "callback == null");

        final val loaderTaskCallbacks = new DataLoaderCallbacks<>(loader, callback);
        getLoaderManager().restartLoader(loaderId, null, loaderTaskCallbacks);
    }

    @Override
    public void stopLoader(final int loaderId) {
        getLoaderManager().destroyLoader(loaderId);
    }

    @Getter(AccessLevel.PROTECTED)
    @NonNull
    private final LoaderManager _loaderManager;

    @Accessors(prefix = "_")
    protected static class DataLoaderCallbacks<TResult, TError>
        implements LoaderManager.LoaderCallbacks<AsyncResult<TResult, TError>> {
        public DataLoaderCallbacks(
            @NonNull final Loader<AsyncResult<TResult, TError>> loader,
            @NonNull final AsyncCallback<TResult, TError> asyncCallback) {
            Contracts.requireNonNull(loader, "loader == null");
            Contracts.requireNonNull(asyncCallback, "asyncCallback == null");

            _loader = loader;
            _asyncCallback = asyncCallback;
        }

        @Override
        public Loader<AsyncResult<TResult, TError>> onCreateLoader(
            final int id, final Bundle args) {
            return getLoader();
        }

        @Override
        public void onLoadFinished(
            final Loader<AsyncResult<TResult, TError>> loader,
            final AsyncResult<TResult, TError> data) {
            if (data != null) {
                if (data.isSuccess()) {
                    getAsyncCallback().onSuccess(data.getResult());
                } else {
                    getAsyncCallback().onError(data.getError());
                }
            }
        }

        @Override
        public void onLoaderReset(final Loader<AsyncResult<TResult, TError>> loader) {
        }

        @Getter(AccessLevel.PROTECTED)
        @NonNull
        private final AsyncCallback<TResult, TError> _asyncCallback;

        @Getter(AccessLevel.PROTECTED)
        @NonNull
        private final Loader<AsyncResult<TResult, TError>> _loader;
    }
}
