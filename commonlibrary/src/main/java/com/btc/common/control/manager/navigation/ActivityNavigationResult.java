package com.btc.common.control.manager.navigation;

import android.content.Intent;
import android.support.annotation.Nullable;

import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(prefix = "_")
public class ActivityNavigationResult {
    public ActivityNavigationResult(
        final int requestCode, final int resultCode, @Nullable final Intent data) {
        _data = data;
        _requestCode = requestCode;
        _resultCode = resultCode;
    }

    @Getter
    @Nullable
    private final Intent _data;

    @Getter
    private final int _requestCode;

    @Getter
    private final int _resultCode;
}
