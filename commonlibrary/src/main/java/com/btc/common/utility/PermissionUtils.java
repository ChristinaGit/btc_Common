package com.btc.common.utility;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import lombok.val;

import com.btc.common.contract.Contracts;
import com.btc.common.control.manager.permission.CheckPermissionsResult;

import java.util.ArrayList;
import java.util.List;

public final class PermissionUtils {
    @NonNull
    public static CheckPermissionsResult checkPermissions(
        @NonNull final Activity activity, @NonNull final List<String> permissions) {
        Contracts.requireNonNull(activity, "activity == null");
        Contracts.requireNonNull(permissions, "permissions == null");

        final val gratedPermissions = new ArrayList<String>();
        final val deniedPermissions = new ArrayList<String>();
        final val neverAskAgainPermissions = new ArrayList<String>();

        for (final val permission : permissions) {
            if (isPermissionGranted(activity, permission)) {
                gratedPermissions.add(permission);
            } else {
                deniedPermissions.add(permission);
                if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                    neverAskAgainPermissions.add(permission);
                }
            }
        }

        return CheckPermissionsResult.from(deniedPermissions,
                                           gratedPermissions,
                                           neverAskAgainPermissions);
    }

    public static boolean isPermissionGranted(
        @NonNull final Context context, @NonNull final String permission) {
        Contracts.requireNonNull(context, "context == null");
        Contracts.requireNonNull(permission, "permission == null");

        return ContextCompat.checkSelfPermission(context, permission) ==
               PackageManager.PERMISSION_GRANTED;
    }

    public static boolean isAllPermissionGranted(
        @NonNull final Context context, @NonNull final Iterable<String> permissions) {
        Contracts.requireNonNull(context, "context == null");
        Contracts.requireNonNull(permissions, "permissions == null");

        boolean allGranted = true;

        for (final val permission : permissions) {
            if (!isPermissionGranted(context, permission)) {
                allGranted = false;

                break;
            }
        }

        return allGranted;
    }

    private PermissionUtils() {
        Contracts.unreachable();
    }
}
