package com.btc.common.extension.eventArgs;

import android.content.Intent;
import android.support.annotation.Nullable;

import lombok.Getter;
import lombok.experimental.Accessors;

import com.btc.common.event.eventArgs.EventArgs;

@Accessors(prefix = "_")
public class ActivityResultEventArgs extends EventArgs {
    public ActivityResultEventArgs(
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
