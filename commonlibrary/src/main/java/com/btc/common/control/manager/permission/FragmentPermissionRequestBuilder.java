package com.btc.common.control.manager.permission;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.val;

import rx.Observable;
import rx.Subscriber;

import com.btc.common.contract.Contracts;
import com.btc.common.event.generic.EventHandler;
import com.btc.common.extension.pemission.RequestPermissionFragment;
import com.btc.common.extension.pemission.RequestPermissionsResultEventArgs;
import com.btc.common.utility.PermissionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Accessors(prefix = "_")
public final class FragmentPermissionRequestBuilder implements PermissionRequestBuilder {
    public FragmentPermissionRequestBuilder(
        @NonNull final AppCompatActivity activity, @Nullable final String fragmentTag) {
        Contracts.requireNonNull(activity, "activity == null");

        _activity = activity;
        _fragmentManager = activity.getSupportFragmentManager();
        _fragmentBuilder = RequestPermissionFragment.builder(activity);
        _fragmentTag = fragmentTag;
    }

    @CallSuper
    @NonNull
    @Override
    public PermissionRequestBuilder addPermission(@NonNull final String permission) {
        Contracts.requireNonNull(permission, "permission == null");

        getRequestedPermissions().add(permission);

        getFragmentBuilder().addPermission(permission);

        return this;
    }

    @CallSuper
    @NonNull
    @Override
    public PermissionRequestBuilder addPermission(
        @NonNull final String permission, @Nullable final String explanation) {
        Contracts.requireNonNull(permission, "permission == null");

        getRequestedPermissions().add(permission);

        getFragmentBuilder().addPermission(permission, explanation);

        return this;
    }

    @NonNull
    @Override
    public PermissionRequestBuilder addPermissions(@NonNull final Collection<String> permissions) {
        Contracts.requireNonNull(permissions, "permissions == null");

        getRequestedPermissions().addAll(permissions);

        getFragmentBuilder().addPermissions(permissions);

        return this;
    }

    @CallSuper
    @NonNull
    @Override
    public Observable<RequestPermissionsResult> show() {
        final Observable<RequestPermissionsResult> result;

        final val requestedPermissions = getRequestedPermissions();

        if (PermissionUtils.isAllPermissionGranted(getActivity(), requestedPermissions)) {
            result = Observable.just(RequestPermissionsResult.allGranted(requestedPermissions));
        } else {
            final val fragment = getFragmentBuilder().build();

            result = Observable.create(new Observable.OnSubscribe<RequestPermissionsResult>() {
                @Override
                public void call(final Subscriber<? super RequestPermissionsResult> subscriber) {
                    Contracts.requireMainThread();

                    final val permissionResultEvent = fragment.getPermissionResultEvent();
                    permissionResultEvent.addHandler(new EventHandler<RequestPermissionsResultEventArgs>() {
                        @Override
                        public void onEvent(
                            @NonNull final RequestPermissionsResultEventArgs eventArgs) {
                            Contracts.requireNonNull(eventArgs, "eventArgs == null");

                            permissionResultEvent.removeHandler(this);

                            subscriber.onNext(RequestPermissionsResult.from(
                                eventArgs.getDeniedPermissions(),
                                eventArgs.getGrantedPermissions(),
                                eventArgs.getNeverAskAgainPermissions(),
                                eventArgs.getUnrequestedPermissions()));
                            subscriber.onCompleted();
                        }
                    });

                    try {
                        fragment.startRequest(getFragmentManager(), getFragmentTag());
                    } catch (final Throwable t) {
                        subscriber.onError(t);
                    }
                }
            });
        }

        return result;
    }

    @Getter(AccessLevel.PROTECTED)
    @NonNull
    private final AppCompatActivity _activity;

    @Getter(AccessLevel.PROTECTED)
    @NonNull
    private final RequestPermissionFragment.Builder _fragmentBuilder;

    @Getter(AccessLevel.PROTECTED)
    @NonNull
    private final FragmentManager _fragmentManager;

    @Getter(AccessLevel.PROTECTED)
    @Nullable
    private final String _fragmentTag;

    @Getter(AccessLevel.PROTECTED)
    @NonNull
    private final List<String> _requestedPermissions = new ArrayList<>();
}
