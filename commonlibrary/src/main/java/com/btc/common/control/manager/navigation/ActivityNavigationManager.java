package com.btc.common.control.manager.navigation;

import android.content.Intent;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.val;

import rx.Observable;
import rx.Subscriber;

import com.btc.common.contract.Contracts;
import com.btc.common.control.adviser.ResourceAdviser;
import com.btc.common.control.manager.ReleasableManager;
import com.btc.common.event.generic.EventHandler;
import com.btc.common.extension.activity.ObservableActivity;
import com.btc.common.extension.eventArgs.ActivityResultEventArgs;

@Accessors(prefix = "_")
public abstract class ActivityNavigationManager extends ReleasableManager {
    protected ActivityNavigationManager(
        @NonNull final ResourceAdviser resourceAdviser,
        @NonNull final ObservableActivity observableActivity) {
        super(Contracts.requireNonNull(resourceAdviser, "resourceAdviser == null"));
        Contracts.requireNonNull(observableActivity, "observableActivity == null");

        _observableActivity = observableActivity;

        _observableActivity.getActivityResultEvent().addHandler(_activityResultHandler);
    }

    @NonNull
    protected final AppCompatActivity getActivity() {
        return getObservableActivity().asActivity();
    }

    @NonNull
    protected final Observable<ActivityNavigationResult> navigateToActivityForResult(
        @NonNull final Intent intent, final int requestCode) {
        Contracts.requireNonNull(intent, "intent == null");

        return Observable.create(new Observable.OnSubscribe<ActivityNavigationResult>() {
            @Override
            public void call(final Subscriber<? super ActivityNavigationResult> subscriber) {
                final val navigationSubscribers = getNavigationSubscribers();

                final val oldSubscriber = navigationSubscribers.get(requestCode);
                if (oldSubscriber != null) {
                    oldSubscriber.onError(new IllegalStateException("Request is obsolete."));
                    navigationSubscribers.remove(requestCode);
                }

                navigationSubscribers.append(requestCode, subscriber);

                performStartActivityForResult(intent, requestCode);
            }
        });
    }

    @CallSuper
    @Override
    protected void onAcquireResources() {
        super.onAcquireResources();

        // TODO: Fix it.
        //        getObservableActivity().getActivityResultEvent().addHandler(_activityResultHandler);
    }

    @CallSuper
    @Override
    protected void onReleaseResources() {
        super.onReleaseResources();

        getObservableActivity().getActivityResultEvent().removeHandler(_activityResultHandler);
    }

    protected void performStartActivity(@NonNull final Intent intent) {
        Contracts.requireNonNull(intent, "intent == null");

        getActivity().startActivity(intent);
    }

    protected void performStartActivityForResult(
        @NonNull final Intent intent, final int requestCode) {
        Contracts.requireNonNull(intent, "intent == null");

        getActivity().startActivityForResult(intent, requestCode);
    }

    @Getter(value = AccessLevel.PRIVATE)
    @NonNull
    private final SparseArray<Subscriber<? super ActivityNavigationResult>> _navigationSubscribers =
        new SparseArray<>();

    @NonNull
    private final EventHandler<ActivityResultEventArgs> _activityResultHandler =
        new EventHandler<ActivityResultEventArgs>() {
            @Override
            public void onEvent(@NonNull final ActivityResultEventArgs eventArgs) {
                Contracts.requireNonNull(eventArgs, "eventArgs == null");

                final int requestCode = eventArgs.getRequestCode();
                final int resultCode = eventArgs.getResultCode();
                final val data = eventArgs.getData();

                final val navigationSubscribers = getNavigationSubscribers();
                final val oldSubscriber = navigationSubscribers.get(requestCode);
                if (oldSubscriber != null) {
                    final val navigationResult =
                        new ActivityNavigationResult(requestCode, resultCode, data);
                    oldSubscriber.onNext(navigationResult);
                    oldSubscriber.onCompleted();

                    navigationSubscribers.remove(requestCode);
                }
            }
        };

    @Getter(value = AccessLevel.PROTECTED)
    @NonNull
    private final ObservableActivity _observableActivity;
}
