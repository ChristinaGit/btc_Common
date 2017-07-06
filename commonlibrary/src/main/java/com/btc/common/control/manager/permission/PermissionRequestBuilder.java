package com.btc.common.control.manager.permission;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import rx.Observable;

import java.util.Collection;

public interface PermissionRequestBuilder {
    @NonNull
    PermissionRequestBuilder addPermission(@NonNull final String permission);

    @NonNull
    PermissionRequestBuilder addPermission(
        @NonNull final String permission, @Nullable final String explanation);

    @NonNull
    PermissionRequestBuilder addPermissions(@NonNull final Collection<String> permission);

    @NonNull
    Observable<RequestPermissionsResult> show();
}
