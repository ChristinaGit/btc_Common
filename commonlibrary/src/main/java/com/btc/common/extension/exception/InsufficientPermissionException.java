package com.btc.common.extension.exception;

import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(prefix = "_")
public class InsufficientPermissionException extends Exception {
    private static final long serialVersionUID = -6350227847346814928L;

    public InsufficientPermissionException() {
        _deniedPermissions = null;
    }

    public InsufficientPermissionException(@Nullable final List<String> deniedPermissions) {
        _deniedPermissions = deniedPermissions;
    }

    public InsufficientPermissionException(@Nullable final String message) {
        super(message);

        _deniedPermissions = null;
    }

    public InsufficientPermissionException(
        @Nullable final String message, @Nullable final List<String> deniedPermissions) {
        super(message);

        _deniedPermissions = deniedPermissions;
    }

    public InsufficientPermissionException(@Nullable final String message, final Throwable cause) {
        super(message, cause);

        _deniedPermissions = null;
    }

    public InsufficientPermissionException(
        @Nullable final String message,
        @Nullable final List<String> deniedPermissions,
        final Throwable cause) {
        super(message, cause);

        _deniedPermissions = deniedPermissions;
    }

    public InsufficientPermissionException(@Nullable final Throwable cause) {
        super(cause);

        _deniedPermissions = null;
    }

    public InsufficientPermissionException(
        @Nullable final Throwable cause, @Nullable final List<String> deniedPermissions) {
        super(cause);

        _deniedPermissions = deniedPermissions;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected InsufficientPermissionException(
        @Nullable final String message,
        @Nullable final Throwable cause,
        final boolean enableSuppression,
        final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);

        _deniedPermissions = null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected InsufficientPermissionException(
        @Nullable final String message,
        @Nullable final List<String> deniedPermissions,
        @Nullable final Throwable cause,
        final boolean enableSuppression,
        final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);

        _deniedPermissions = deniedPermissions;
    }

    @Getter
    @Nullable
    private final List<String> _deniedPermissions;
}
