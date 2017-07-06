package com.btc.common.utility;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.btc.common.UriScheme;
import com.btc.common.contract.Contracts;

public final class UriFactoryUtils {
    @NonNull
    public static Uri getTelephoneUri(@NonNull final String telephoneNumber) {
        return Uri.parse(
            UriScheme.TEL.getSchemeName() + UriUtils.SCHEMA_SEPARATOR + telephoneNumber);
    }

    private UriFactoryUtils() {
        Contracts.unreachable();
    }
}
