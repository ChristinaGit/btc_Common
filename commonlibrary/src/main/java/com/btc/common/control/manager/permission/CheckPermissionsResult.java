package com.btc.common.control.manager.permission;

import android.support.annotation.NonNull;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

import com.btc.common.contract.Contracts;
import com.btc.common.event.eventArgs.EventArgs;

import java.util.Collections;
import java.util.List;

@ToString(doNotUseGetters = true)
@Accessors(prefix = "_")
public class CheckPermissionsResult extends EventArgs {
    @NonNull
    public static CheckPermissionsResult allGranted(@NonNull final List<String> permissions) {
        Contracts.requireNonNull(permissions, "permissions == null");

        return from(Collections.<String>emptyList(), permissions, Collections.<String>emptyList());
    }

    @NonNull
    public static CheckPermissionsResult allDenied(@NonNull final List<String> permissions) {
        Contracts.requireNonNull(permissions, "permissions == null");

        return from(permissions, Collections.<String>emptyList(), Collections.<String>emptyList());
    }

    @NonNull
    public static CheckPermissionsResult from(
        @NonNull final List<String> deniedPermissions,
        @NonNull final List<String> grantedPermissions,
        @NonNull final List<String> neverAskAgainPermissions) {
        Contracts.requireNonNull(deniedPermissions, "deniedPermissions == null");
        Contracts.requireNonNull(grantedPermissions, "grantedPermissions == null");
        Contracts.requireNonNull(neverAskAgainPermissions, "neverAskAgainPermissions == null");

        return new CheckPermissionsResult(deniedPermissions,
                                          grantedPermissions,
                                          neverAskAgainPermissions);
    }

    public boolean isAllGranted() {
        return !getGrantedPermissions().isEmpty() && getDeniedPermissions().isEmpty();
    }

    protected CheckPermissionsResult(
        @NonNull final List<String> deniedPermissions,
        @NonNull final List<String> grantedPermissions,
        @NonNull final List<String> neverAskAgainPermissions) {
        Contracts.requireNonNull(deniedPermissions, "deniedPermissions == null");
        Contracts.requireNonNull(grantedPermissions, "grantedPermissions == null");
        Contracts.requireNonNull(neverAskAgainPermissions, "neverAskAgainPermissions == null");

        _deniedPermissions = deniedPermissions;
        _grantedPermissions = grantedPermissions;
        _neverAskAgainPermissions = neverAskAgainPermissions;
    }

    @Getter
    @NonNull
    private final List<String> _deniedPermissions;

    @Getter
    @NonNull
    private final List<String> _grantedPermissions;

    @Getter
    @NonNull
    private final List<String> _neverAskAgainPermissions;
}
