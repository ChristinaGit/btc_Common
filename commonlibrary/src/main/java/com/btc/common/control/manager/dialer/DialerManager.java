package com.btc.common.control.manager.dialer;

import android.net.Uri;
import android.support.annotation.NonNull;

import rx.Observable;

import com.btc.common.control.manager.permission.CheckPermissionsResult;

import java.util.List;

public interface DialerManager {
    @NonNull
    CheckPermissionsResult checkPhoneCallPermissions();

    @NonNull
    List<String> getPhoneCallPermissions();

    @NonNull
    Observable<Boolean> performPhoneCall(@NonNull final Uri phoneUri);

    @NonNull
    Observable<Boolean> performPhoneCallWithPermissions(
        @NonNull final Uri phoneUri, boolean allowAskPermissions);
}
