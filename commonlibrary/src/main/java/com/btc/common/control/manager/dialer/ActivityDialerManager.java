package com.btc.common.control.manager.dialer;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.val;

import rx.Observable;
import rx.functions.Func1;

import com.btc.common.contract.Contracts;
import com.btc.common.control.manager.permission.CheckPermissionsResult;
import com.btc.common.control.manager.permission.PermissionManager;
import com.btc.common.control.manager.permission.RequestPermissionsResult;
import com.btc.common.extension.activity.ObservableActivity;
import com.btc.common.extension.exception.InsufficientPermissionException;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

@Accessors(prefix = "_")
public class ActivityDialerManager implements DialerManager {
    public ActivityDialerManager(
        @NonNull final PermissionManager permissionManager,
        @NonNull final ObservableActivity observableActivity) {
        Contracts.requireNonNull(permissionManager, "permissionManager == null");
        Contracts.requireNonNull(observableActivity, "observableActivity == null");

        _permissionManager = permissionManager;
        _observableActivity = observableActivity;
    }

    @NonNull
    @Override
    public CheckPermissionsResult checkPhoneCallPermissions() {
        return getPermissionManager().checkPermissions(getPhoneCallPermissions());
    }

    @NonNull
    @Override
    public final List<String> getPhoneCallPermissions() {
        return Collections.singletonList(Manifest.permission.CALL_PHONE);
    }

    @NonNull
    @Override
    public Observable<Boolean> performPhoneCall(@NonNull final Uri phoneUri) {
        Contracts.requireNonNull(phoneUri, "phoneUri == null");

        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call()
                throws Exception {
                final val phoneCallIntent = new Intent(Intent.ACTION_CALL, phoneUri);
                final boolean hasActivity =
                    phoneCallIntent.resolveActivity(getActivity().getPackageManager()) != null;

                if (hasActivity) {
                    getActivity().startActivity(phoneCallIntent);
                }

                return hasActivity;
            }
        });
    }

    @NonNull
    @Override
    public Observable<Boolean> performPhoneCallWithPermissions(
        @NonNull final Uri phoneUri, final boolean allowAskPermissions) {
        Contracts.requireNonNull(phoneUri, "phoneUri == null");

        if (allowAskPermissions) {
            return requestPhoneCallPermissions().flatMap(new Func1<RequestPermissionsResult,
                Observable<Boolean>>() {
                @Override
                public Observable<Boolean> call(final RequestPermissionsResult result) {
                    if (result.isAllGranted()) {
                        return performPhoneCall(phoneUri);
                    } else {
                        final val error =
                            new InsufficientPermissionException(result.getDeniedPermissions());
                        return Observable.error(error);
                    }
                }
            });
        } else {
            final val checkResult = checkPhoneCallPermissions();
            if (checkResult.isAllGranted()) {
                return performPhoneCall(phoneUri);
            } else {
                final val error =
                    new InsufficientPermissionException(checkResult.getDeniedPermissions());
                return Observable.error(error);
            }
        }
    }

    @NonNull
    protected final AppCompatActivity getActivity() {
        return getObservableActivity().asActivity();
    }

    @NonNull
    protected final Observable<RequestPermissionsResult> requestPhoneCallPermissions() {
        return getPermissionManager()
            .createPermissionsRequest()
            .addPermissions(getPhoneCallPermissions())
            .show();
    }

    @Getter
    @NonNull
    private final ObservableActivity _observableActivity;

    @Getter
    @NonNull
    private final PermissionManager _permissionManager;
}
