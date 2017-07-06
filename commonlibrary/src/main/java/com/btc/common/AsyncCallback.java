package com.btc.common;

import android.support.annotation.Nullable;

public interface AsyncCallback<TResult, TError> {
    void onError(@Nullable TError error);

    void onSuccess(@Nullable TResult result);
}
