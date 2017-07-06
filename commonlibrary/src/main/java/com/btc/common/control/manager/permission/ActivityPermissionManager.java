package com.btc.common.control.manager.permission;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;

import com.btc.common.contract.Contracts;
import com.btc.common.extension.activity.ObservableActivity;
import com.btc.common.utility.PermissionUtils;

import java.util.List;

@Accessors(prefix = "_")
public class ActivityPermissionManager implements PermissionManager {
    public ActivityPermissionManager(
        @NonNull final ObservableActivity observableActivity,
        @Nullable final String requestPermissionsFragmentTag) {
        Contracts.requireNonNull(observableActivity, "observableActivity == null");

        _observableActivity = observableActivity;
        _requestPermissionsFragmentTag = requestPermissionsFragmentTag;
    }

    @Override
    @NonNull
    public CheckPermissionsResult checkPermissions(@NonNull final List<String> permissions) {
        Contracts.requireNonNull(permissions, "permissions == null");

        return PermissionUtils.checkPermissions(getActivity(), permissions);
    }

    @Override
    public int checkSelfPermission(@NonNull final String permission) {
        Contracts.requireNonNull(permission, "permission == null");

        return ContextCompat.checkSelfPermission(getActivity(), permission);
    }

    @Override
    @NonNull
    public PermissionRequestBuilder createPermissionsRequest() {
        return new FragmentPermissionRequestBuilder(getActivity(),
                                                    getRequestPermissionsFragmentTag());
    }

    @Override
    public boolean isPermissionGranted(@NonNull final String permission) {
        Contracts.requireNonNull(permission, "permission == null");

        return PermissionUtils.isPermissionGranted(getActivity(), permission);
    }

    @NonNull
    protected final AppCompatActivity getActivity() {
        return getObservableActivity().asActivity();
    }

    @Getter(AccessLevel.PROTECTED)
    @NonNull
    private final ObservableActivity _observableActivity;

    @Getter
    @Nullable
    private final String _requestPermissionsFragmentTag;
}
