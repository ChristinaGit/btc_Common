package com.btc.common.control.manager.message;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

import rx.Observable;

public interface MessageManager {
    void dismissProgressMessage();

    @NonNull
    String getMessage(@StringRes int messageId);

    @NonNull
    Observable<UserActionReaction> showActionMessage(
        @StringRes int messageId, @StringRes int actionNameId);

    @NonNull
    Observable<UserActionReaction> showActionMessage(
        @NonNull CharSequence message, @StringRes int actionNameId);

    void showErrorMessage(@StringRes int messageId);

    void showErrorMessage(@NonNull CharSequence message);

    void showInfoMessage(@StringRes int messageId);

    void showInfoMessage(@NonNull CharSequence message);

    @NonNull
    Observable<UserActionReaction> showModalActionMessage(@StringRes int messageId);

    @NonNull
    Observable<UserActionReaction> showModalActionMessage(@NonNull CharSequence message);

    void showModalMessage(@StringRes int messageId);

    void showModalMessage(@NonNull CharSequence message);

    void showNotificationMessage(@StringRes int messageId);

    void showNotificationMessage(@NonNull CharSequence message);

    void showProgressMessage(@Nullable CharSequence title, @Nullable CharSequence message);

    void showProgressMessage(@StringRes int titleId, @StringRes int messageId);
}
