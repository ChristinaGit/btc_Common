package com.btc.common.extension.pemission;

import android.support.annotation.Nullable;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import org.parceler.Parcel;

import java.util.List;
import java.util.Map;

@Parcel
@Accessors(prefix = "_")
/*package-private*/ final class RequestPermissionsFragmentState {
    @Getter
    @Setter
    @Nullable
    /*package-private*/ List<String> _deniedPermissions;

    @Getter
    @Setter
    @Nullable
    /*package-private*/ List<String> _grantedPermissions;

    @Getter
    @Setter
    @Nullable
    /*package-private*/ List<String> _neverAskPermissions;

    @Getter
    @Setter
    @Nullable
    /*package-private*/ Map<String, String> _permissionExplanations;

    @Getter
    @Setter
    /*package-private*/ boolean _permissionsRequested;

    @Getter
    @Setter
    @Nullable
    /*package-private*/ List<String> _requestExplanationPermissions;

    @Getter
    @Setter
    @Nullable
    /*package-private*/ List<String> _unrequestedPermissions;
}
