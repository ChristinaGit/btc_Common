package com.btc.common.control.manager.permission;

import android.support.annotation.NonNull;

import java.util.List;

public interface PermissionManager {
    @NonNull
    CheckPermissionsResult checkPermissions(@NonNull final List<String> permissions);

    int checkSelfPermission(@NonNull final String permission);

    @NonNull
    PermissionRequestBuilder createPermissionsRequest();

    boolean isPermissionGranted(@NonNull final String permission);
}
