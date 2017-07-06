package com.btc.common.control.manager.message;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.val;

import rx.Observable;
import rx.Subscriber;

import com.btc.common.contract.Contracts;
import com.btc.common.extension.activity.ObservableActivity;

@Accessors(prefix = "_")
public class ActivityMessageManager implements MessageManager {
    public ActivityMessageManager(
        @NonNull final ObservableActivity observableActivity,
        @IdRes final int contentContainerViewId) {
        Contracts.requireNonNull(observableActivity, "observableActivity == null");

        _observableActivity = observableActivity;
        _contentContainerViewId = contentContainerViewId;
    }

    @Override
    public void dismissProgressMessage() {
        if (_currentProgressMessageDialog != null) {
            _currentProgressMessageDialog.dismiss();

            _currentProgressMessageDialog = null;
        }
    }

    @NonNull
    @Override
    public String getMessage(@StringRes final int messageId) {
        return getActivity().getString(messageId);
    }

    @NonNull
    @Override
    public Observable<UserActionReaction> showActionMessage(
        @StringRes final int messageId, @StringRes final int actionNameId) {
        return showActionMessage(getMessage(messageId), actionNameId);
    }

    @NonNull
    @Override
    public Observable<UserActionReaction> showActionMessage(
        @NonNull final CharSequence message, @StringRes final int actionNameId) {
        Contracts.requireNonNull(message, "message == null");

        final Observable<UserActionReaction> result;

        final val contentView = getCoordinatorView();
        if (contentView != null) {
            result = Observable.create(new Observable.OnSubscribe<UserActionReaction>() {
                @Override
                public void call(final Subscriber<? super UserActionReaction> subscriber) {
                    Snackbar
                        .make(contentView, message, Snackbar.LENGTH_LONG)
                        .setAction(actionNameId, new View.OnClickListener() {
                            @Override
                            public void onClick(final View v) {
                                subscriber.onNext(UserActionReaction.PERFORM);
                                subscriber.onCompleted();
                            }
                        })
                        .addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                            @Override
                            public void onDismissed(
                                final Snackbar transientBottomBar, final int event) {
                                super.onDismissed(transientBottomBar, event);

                                if (event == DISMISS_EVENT_TIMEOUT ||
                                    event == DISMISS_EVENT_SWIPE) {
                                    subscriber.onNext(UserActionReaction.IGNORE);
                                    subscriber.onCompleted();
                                }
                            }
                        })
                        .show();
                }
            });
        } else {
            result = Observable.empty();
        }

        return result;
    }

    @Override
    public void showErrorMessage(@StringRes final int messageId) {
        showErrorMessage(getMessage(messageId));
    }

    @Override
    public void showErrorMessage(@NonNull final CharSequence message) {
        Contracts.requireNonNull(message, "message == null");

        final val contentView = getCoordinatorView();
        if (contentView != null) {
            Snackbar.make(contentView, message, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void showInfoMessage(@StringRes final int messageId) {
        showInfoMessage(getMessage(messageId));
    }

    @Override
    public void showInfoMessage(@NonNull final CharSequence message) {
        Contracts.requireNonNull(message, "message == null");

        final val contentView = getCoordinatorView();
        if (contentView != null) {
            Snackbar.make(contentView, message, Snackbar.LENGTH_SHORT).show();
        }
    }

    @NonNull
    @Override
    public Observable<UserActionReaction> showModalActionMessage(@StringRes final int messageId) {
        final val message = getMessage(messageId);
        return showModalActionMessage(message);
    }

    @NonNull
    @Override
    public Observable<UserActionReaction> showModalActionMessage(
        @NonNull final CharSequence message) {
        Contracts.requireNonNull(message, "message == null");

        return Observable.create(new Observable.OnSubscribe<UserActionReaction>() {
            @Override
            public void call(final Subscriber<? super UserActionReaction> subscriber) {
                Contracts.requireMainThread();

                new AlertDialog.Builder(getActivity())
                    .setMessage(message)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, final int which) {
                            subscriber.onNext(UserActionReaction.PERFORM);
                            subscriber.onCompleted();
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, final int which) {
                            subscriber.onNext(UserActionReaction.IGNORE);
                            subscriber.onCompleted();
                        }
                    })
                    .setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(final DialogInterface dialog) {
                            subscriber.onNext(UserActionReaction.IGNORE);
                            subscriber.onCompleted();
                        }
                    })
                    .show();
            }
        });
    }

    @Override
    public void showModalMessage(@StringRes final int messageId) {
        showModalMessage(getMessage(messageId));
    }

    @Override
    public void showModalMessage(@NonNull final CharSequence message) {
        Contracts.requireNonNull(message, "message == null");

        new AlertDialog.Builder(getActivity())
            .setPositiveButton(android.R.string.ok, null)
            .setMessage(message)
            .setCancelable(false)
            .show();
    }

    @Override
    public void showNotificationMessage(@StringRes final int messageId) {
        showNotificationMessage(getMessage(messageId));
    }

    @Override
    public void showNotificationMessage(@NonNull final CharSequence message) {
        Contracts.requireNonNull(message, "message == null");

        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressMessage(
        @Nullable final CharSequence title, @Nullable final CharSequence message) {

        dismissProgressMessage();

        final val context = getActivity();

        _currentProgressMessageDialog = ProgressDialog.show(context, title, message, true, false);
    }

    @Override
    public void showProgressMessage(@StringRes final int titleId, @StringRes final int messageId) {
        showProgressMessage(getMessage(titleId), getMessage(messageId));
    }

    @NonNull
    protected final AppCompatActivity getActivity() {
        return getObservableActivity().asActivity();
    }

    @Nullable
    protected final View getCoordinatorView() {
        if (_contentContainerView == null) {
            _contentContainerView = getActivity().findViewById(getContentContainerViewId());
        }

        return _contentContainerView;
    }

    @IdRes
    @Getter(AccessLevel.PROTECTED)
    private final int _contentContainerViewId;

    @Getter(AccessLevel.PROTECTED)
    @NonNull
    private final ObservableActivity _observableActivity;

    @Nullable
    private View _contentContainerView;

    @Nullable
    private Dialog _currentProgressMessageDialog;
}
