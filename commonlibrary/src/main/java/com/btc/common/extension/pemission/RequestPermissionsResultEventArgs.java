package com.btc.common.extension.pemission;

import android.support.annotation.NonNull;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

import com.btc.common.contract.Contracts;
import com.btc.common.event.eventArgs.EventArgs;

import java.util.List;

@ToString(doNotUseGetters = true)
@Accessors(prefix = "_")
public class RequestPermissionsResultEventArgs extends EventArgs {
    public RequestPermissionsResultEventArgs(
        @NonNull final List<String> deniedPermissions,
        @NonNull final List<String> grantedPermissions,
        @NonNull final List<String> neverAskAgainPermissions,
        @NonNull final List<String> unrequestedPermissions) {
        Contracts.requireNonNull(deniedPermissions, "deniedPermissions == null");
        Contracts.requireNonNull(grantedPermissions, "grantedPermissions == null");
        Contracts.requireNonNull(neverAskAgainPermissions, "neverAskAgainPermissions == null");
        Contracts.requireNonNull(unrequestedPermissions, "unrequestedPermissions == null");

        _deniedPermissions = deniedPermissions;
        _grantedPermissions = grantedPermissions;
        _neverAskAgainPermissions = neverAskAgainPermissions;
        _unrequestedPermissions = unrequestedPermissions;
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

    @Getter
    @NonNull
    private final List<String> _unrequestedPermissions;
}
